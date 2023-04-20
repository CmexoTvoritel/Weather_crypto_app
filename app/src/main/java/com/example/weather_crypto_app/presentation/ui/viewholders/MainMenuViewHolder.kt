package com.example.weather_crypto_app.presentation.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.models.MainMenuModel
import com.example.weather_crypto_app.models.MainMenuModules
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.mapview.MapView
import kotlinx.android.synthetic.main.main_menu_item_layout.view.*

class MainMenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val mapView: MapView = itemView.findViewById(R.id.map_view)

    init {
        mapView.map.mapType = MapType.MAP
    }

    var clickCallback: ((type: MainMenuModules) -> Unit)? = null

    fun bind(item: MainMenuModel) {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        itemView.name_button.setOnClickListener {
            clickCallback?.invoke(item.type)
        }
        itemView.settings_butt.setOnClickListener {
            clickCallback?.invoke(item.type)
        }
    }
    fun unbind() {
        mapView.onStop()
        mapView.visibility = View.INVISIBLE
        MapKitFactory.getInstance().onStop()
    }
}