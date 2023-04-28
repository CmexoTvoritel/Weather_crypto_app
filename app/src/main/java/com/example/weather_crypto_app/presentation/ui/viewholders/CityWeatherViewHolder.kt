package com.example.weather_crypto_app.presentation.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.databinding.CityWeatherItemLayoutBinding
import com.example.weather_crypto_app.models.CityWeatherModel
import kotlinx.android.synthetic.main.city_weather_item_layout.view.*

class CityWeatherViewHolder(val binding: CityWeatherItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    var clickCallback: ((type: CityWeatherModel) -> Unit)? = null

    fun bind(item: CityWeatherModel) {
        itemView.weather_card.setOnClickListener {
            clickCallback?.invoke(item)
        }
    }
}