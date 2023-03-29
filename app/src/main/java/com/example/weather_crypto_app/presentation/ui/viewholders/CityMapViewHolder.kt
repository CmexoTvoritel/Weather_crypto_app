package com.example.weather_crypto_app.presentation.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.models.CityMapModel
import kotlinx.android.synthetic.main.city_map_item_layout.view.*

class CityMapViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var clickCallback: ((type: CityMapModel) -> Unit)? = null

    fun bind(item: CityMapModel) {
        itemView.city_card.setOnClickListener {
            clickCallback?.invoke(item)
        }
    }
}