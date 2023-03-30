package com.example.weather_crypto_app.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.models.MainMenuModel
import com.example.weather_crypto_app.models.MainMenuModules
import com.example.weather_crypto_app.presentation.ui.viewholders.MainMenuViewHolder
import kotlinx.android.synthetic.main.main_menu_item_layout.view.*

class MainMenuAdapter(private val mainMenuList: List<MainMenuModel>): RecyclerView.Adapter<MainMenuViewHolder>() {

    var clickCallback: ((type: MainMenuModules) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_menu_item_layout, parent, false)
        return MainMenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainMenuViewHolder, position: Int) {
        holder.itemView.name_menu.text = mainMenuList[position].nameMenu
        val item = mainMenuList[position]
        holder.clickCallback = clickCallback
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return mainMenuList.size
    }

    interface Listener {
        fun onCLick(mainMenuModel: MainMenuModel)
    }

}