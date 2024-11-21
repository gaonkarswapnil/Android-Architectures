package com.example.androidarchitecture

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.provider.CalendarContract.CalendarAlerts
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.androidarchitecture.databinding.ActivityMainBinding
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

import kotlinx.coroutines.launch


data class WeatherRequest(
    val key: String,
    val location: String,
    val days: Int,
    val aqi: String,
    val alerts: String
)

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)

data class Current(
    val cloud: Int,
    val condition: Condition,
    val dewpoint_c: Double,
    val dewpoint_f: Double,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val gust_kph: Double,
    val gust_mph: Double,
    val heatindex_c: Double,
    val heatindex_f: Double,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Int,
    val precip_in: Double,
    val precip_mm: Double,
    val pressure_in: Double,
    val pressure_mb: Double,
    val temp_c: Double,
    val temp_f: Double,
    val uv: Double,
    val vis_km: Double,
    val vis_miles: Double,
    val wind_degree: Int,
    val wind_dir: String,
    val wind_kph: Double,
    val wind_mph: Double,
    val windchill_c: Double,
    val windchill_f: Double
)

data class Forecast(
    val forecastday: List<Forecastday>
)

data class Forecastday(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>
)

data class Astro(
    val is_moon_up: Int,
    val is_sun_up: Int,
    val moon_illumination: Int,
    val moon_phase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)

data class Day(
    val avghumidity: Int,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val avgvis_km: Double,
    val avgvis_miles: Double,
    val condition: Condition,
    val daily_chance_of_rain: Int,
    val daily_chance_of_snow: Int,
    val daily_will_it_rain: Int,
    val daily_will_it_snow: Int,
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val maxwind_kph: Double,
    val maxwind_mph: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val totalprecip_in: Double,
    val totalprecip_mm: Double,
    val totalsnow_cm: Double,
    val uv: Double
)

data class Hour(
    val chance_of_rain: Int,
    val chance_of_snow: Int,
    val cloud: Int,
    val condition: Condition,
    val dewpoint_c: Double,
    val dewpoint_f: Double,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val gust_kph: Double,
    val gust_mph: Double,
    val heatindex_c: Double,
    val heatindex_f: Double,
    val humidity: Int,
    val is_day: Int,
    val precip_in: Double,
    val precip_mm: Double,
    val pressure_in: Double,
    val pressure_mb: Double,
    val snow_cm: Double,
    val temp_c: Double,
    val temp_f: Double,
    val time: String,
    val time_epoch: Int,
    val uv: Double,
    val vis_km: Double,
    val vis_miles: Double,
    val will_it_rain: Int,
    val will_it_snow: Int,
    val wind_degree: Int,
    val wind_dir: String,
    val wind_kph: Double,
    val wind_mph: Double,
    val windchill_c: Double,
    val windchill_f: Double
)

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    val localtime_epoch: Int,
    val lon: Double,
    val name: String,
    val region: String,
    val tz_id: String
)

interface ApiServices {

    @GET("/v1/forecast.json")
    suspend fun getWeatherData(
        @Query("key") key: String,
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ): Response<WeatherResponse>
}

object RetrofitHelper {
    val BASE_URL = "http://api.weatherapi.com"

    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor().apply {
            val level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }
}



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val apiServices = RetrofitHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val imageView: ImageView = findViewById(R.id.imgWeatherIcon)

        lifecycleScope.launch {
            val getWeatherData = apiServices.getWeatherData(
                "55ae7587d2194b30bf364918242111",
                "Kalyan",
                1,
                "no",
                "no"
            )

            binding.tvCityName.text = getWeatherData.body()?.location?.name.toString()
            binding.tvTemperature.text = getWeatherData.body()?.current?.temp_c.toString()
            binding.tvCondition.text = getWeatherData.body()?.current?.condition?.text.toString()

            Glide.with(this@MainActivity)
                .load(getWeatherData.body()?.current?.condition?.icon.toString())
                .into(imageView)

        }

    }
}