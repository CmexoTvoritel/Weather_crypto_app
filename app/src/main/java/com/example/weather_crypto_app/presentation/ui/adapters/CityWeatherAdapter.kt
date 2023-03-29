package com.example.weather_crypto_app.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.models.CityWeatherModel
import com.example.weather_crypto_app.presentation.ui.viewholders.CityWeatherViewHolder
import com.example.weather_crypto_app.presentation.ui.viewholders.MainMenuViewHolder
import kotlinx.android.synthetic.main.city_weather_item_layout.view.*

class CityWeatherAdapter(private val cityList: List<CityWeatherModel>): RecyclerView.Adapter<CityWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_weather_item_layout, parent, false)
        return CityWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityWeatherViewHolder, position: Int) {
        holder.itemView.city_short_name.text = cityList[position].shortName
        holder.itemView.city_full_name.text = cityList[position].fullNameCity
    }

    override fun getItemCount(): Int {
        return cityList.size
    }
}