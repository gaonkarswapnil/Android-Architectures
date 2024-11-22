package com.example.androidarchitecture.view

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidarchitecture.R
import com.example.androidarchitecture.model.Forecastday
import com.example.androidarchitecture.model.Hour
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class WeatherAdapter(var forecast: List<Hour>): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return forecast.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.itemView.also {
            val date = getHourFromDate(forecast[position].time.toString())
            if(date.toInt()>= 12){
                it.findViewById<TextView>(R.id.tvHours).text = "${date} PM"
            }else{
                it.findViewById<TextView>(R.id.tvHours).text = "${date} AM"
            }

            it.findViewById<TextView>(R.id.tvForecast).text = "${forecast[position].temp_c.toString()} °C"


            val iconUrl = "https:${forecast[position].condition.icon}"
            val imageView: ImageView = it.findViewById(R.id.ivWeatherImage)

            Glide.with(holder.itemView.context)
                .load(iconUrl)
                .into(imageView)
        }
    }

    fun getHourFromDate(dateString: String): String {
        // Define the input format pattern
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        // Parse the input date string into a Date object
        val date = inputFormat.parse(dateString)

        // Define the output format pattern to extract only the hour part
        val outputFormat = SimpleDateFormat("HH", Locale.getDefault())

        // Return the hour as a string
        return outputFormat.format(date)
    }
}