package com.example.weather_crypto_app.presentation.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.models.MainMenuModel
import com.example.weather_crypto_app.models.MainMenuModules
import kotlinx.android.synthetic.main.main_menu_item_layout.view.name_button

class MainMenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var clickCallback: ((type: MainMenuModules) -> Unit)? = null

    fun bind(item: MainMenuModel) {
        itemView.name_button.setOnClickListener {
            clickCallback?.invoke(item.type)
        }
    }
}