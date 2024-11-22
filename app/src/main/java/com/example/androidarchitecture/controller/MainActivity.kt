package com.example.androidarchitecture.controller

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.androidarchitecture.R
import com.example.androidarchitecture.databinding.ActivityMainBinding
import com.example.androidarchitecture.model.RetrofitHelper
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val apiServices = RetrofitHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val imageView: ImageView = findViewById(R.id.imgWeatherIcon)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        lifecycleScope.launch {
            val getWeatherData = apiServices.getWeatherData(
                "55ae7587d2194b30bf364918242111",
                "Kalyan",
                1,
                "no",
                "no"
            )

            Log.d("Weather Request API",getWeatherData.body().toString())
//            binding.txtTemp.text = getWeatherData.body().toString()

            binding.tvCityName.text = getWeatherData.body()?.location?.name.toString()
            binding.tvTemperature.text = getWeatherData.body()?.current?.temp_c.toString()
            binding.tvCondition.text = getWeatherData.body()?.current?.condition?.text.toString()

            Glide.with(this@MainActivity)
                .load(getWeatherData.body()?.current?.condition?.icon.toString())
                .into(imageView)
        }


    }
}