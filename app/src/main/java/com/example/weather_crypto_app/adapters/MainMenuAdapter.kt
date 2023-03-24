package com.example.weather_crypto_app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.models.MainMenuModel
import kotlinx.android.synthetic.main.main_menu_item_layout.view.*

class MainMenuAdapter(private val mainMenuList: List<MainMenuModel>): RecyclerView.Adapter<MainMenuAdapter.MainMenuViewHolder>() {

    class MainMenuViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name_menu: TextView = itemView.findViewById(R.id.name_menu)
        val name_button: TextView = itemView.findViewById(R.id.name_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_menu_item_layout, parent, false)
        return MainMenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainMenuViewHolder, position: Int) {
        holder.itemView.name_menu.text = mainMenuList[position].name_menu
        holder.itemView.name_button.text = mainMenuList[position].name_menu

    }

    override fun getItemCount(): Int {
        return mainMenuList.size
    }

}