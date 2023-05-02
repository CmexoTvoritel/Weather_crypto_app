package com.example.weather_crypto_app.presentation.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.databinding.CityWeatherItemLayoutBinding
import com.example.weather_crypto_app.models.CityWeatherModel

class CityWeatherViewHolder(val binding: CityWeatherItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    var clickCallback: ((type: CityWeatherModel) -> Unit)? = null

    fun bind(item: CityWeatherModel) {
        binding.weatherCard.setOnClickListener {
            clickCallback?.invoke(item)
        }
    }
}