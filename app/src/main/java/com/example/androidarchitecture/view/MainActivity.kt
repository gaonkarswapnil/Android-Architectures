package com.example.androidarchitecture.view

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.androidarchitecture.R
import com.example.androidarchitecture.databinding.ActivityMainBinding
import com.example.androidarchitecture.model.WeatherRepository
import com.example.androidarchitecture.model.WeatherRequest
import com.example.androidarchitecture.viewmodel.WeatherViewModel
import com.example.androidarchitecture.viewmodel.WeatherViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val weatherViewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(application, WeatherRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val request = WeatherRequest(
            "55ae7587d2194b30bf364918242111",
            "Kalyan",
            1,
            "no",
            "no"
        )


        weatherViewModel.getWeatherData(request).observe(this, Observer { response ->
            // Update the UI with weather data
            binding.tvCityName.text = response.location.name
            binding.tvTemperature.text = response.current.temp_c.toString()
            binding.tvCondition.text = response.current.condition.text.toString()

            val iconUrl = "https:${response.current.condition.icon}"
            val imageView: ImageView = findViewById(R.id.imgWeatherIcon)
            Glide.with(this)
                .load(iconUrl)
                .into(imageView)

        })

//        weatherViewModel.getWeatherData(request)

    }
}