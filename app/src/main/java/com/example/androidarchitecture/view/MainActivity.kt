package com.example.androidarchitecture.view

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.androidarchitecture.R
import com.example.androidarchitecture.databinding.ActivityMainBinding
import com.example.androidarchitecture.model.RetrofitHelper
import com.example.androidarchitecture.model.WeatherRepository
import com.example.androidarchitecture.presenter.WeatherPresenter

class MainActivity : AppCompatActivity(), WeatherView{

    private lateinit var binding : ActivityMainBinding
    private lateinit var presenter: WeatherPresenter

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

        val repository = WeatherRepository(RetrofitHelper.getInstance())
        presenter = WeatherPresenter(this, repository)

        // Fetch weather data
        presenter.fetchWeatherData(
            key = "55ae7587d2194b30bf364918242111",
            location = "Kalyan",
            days = 1,
            aqi = "no",
            alerts = "no"
        )
    }

    override fun showWeatherData(location: String, temperature: String, condition: String, iconUrl: String) {
        binding.tvCityName.text = location
        binding.tvTemperature.text = temperature
        binding.tvCondition.text = condition

        val imageView: ImageView = findViewById(R.id.imgWeatherIcon)
        Glide.with(this)
            .load(iconUrl)
            .into(imageView)
    }

}