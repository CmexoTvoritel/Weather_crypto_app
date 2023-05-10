package com.example.weather_crypto_app.presentation.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.databinding.MainMenuItemLayoutBinding
import com.example.weather_crypto_app.presentation.model.MainMenuModel
import com.example.weather_crypto_app.presentation.model.MainMenuModules
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.mapview.MapView

class MainMenuViewHolder(val binding: MainMenuItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {

    private val mapView: MapView = binding.mapView

    init {
        mapView.map.mapType = MapType.MAP
    }

    var clickCallback: ((type: MainMenuModules) -> Unit)? = null

    fun bind(item: MainMenuModel) {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        binding.nameButton.setOnClickListener {
            clickCallback?.invoke(item.type)
        }
        binding.settingsButt.setOnClickListener {
            clickCallback?.invoke(item.type)
        }

        binding.reloadButton.setOnClickListener {
            clickCallback?.invoke(item.type)
        }
    }
    fun unbind() {
        mapView.onStop()
        mapView.visibility = View.INVISIBLE
        MapKitFactory.getInstance().onStop()
    }
}