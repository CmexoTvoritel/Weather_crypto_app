package com.example.weather_crypto_app.presentation.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.models.CityWeatherModel
import kotlinx.android.synthetic.main.city_weather_item_layout.view.*

class CityWeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var clickCallback: ((type: CityWeatherModel) -> Unit)? = null

    fun bind(item: CityWeatherModel) {
        itemView.weather_card.setOnClickListener {
            clickCallback?.invoke(item)
        }
    }
}