package com.example.weather_crypto_app.presentation.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.databinding.CityMapItemLayoutBinding
import com.example.weather_crypto_app.presentation.model.CityMapModel

class CityMapViewHolder(val binding: CityMapItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {

    var clickCallback: ((type: CityMapModel) -> Unit)? = null

    fun bind(item: CityMapModel) {
        binding.cityCard.setOnClickListener {
            clickCallback?.invoke(item)
        }
    }
}