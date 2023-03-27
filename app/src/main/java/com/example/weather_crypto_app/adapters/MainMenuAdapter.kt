package com.example.weather_crypto_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.models.MainMenuModel
import kotlinx.android.synthetic.main.main_menu_item_layout.view.*

class MainMenuAdapter(private val mainMenuList: List<MainMenuModel>): RecyclerView.Adapter<MainMenuAdapter.MainMenuViewHolder>() {


    class MainMenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name_menu: TextView = itemView.findViewById(R.id.name_menu)
        val name_button: Button = itemView.findViewById(R.id.name_button)

        fun bind(item: MainMenuModel) {
            itemView.name_button.setOnClickListener {
                if(item.name_menu == "Карта") Navigation.findNavController(itemView).navigate(R.id.city_Map)
                if(item.name_menu == "Погода") Navigation.findNavController(itemView).navigate(R.id.city_Weather)
                if(item.name_menu == "Курс криптовалют") Navigation.findNavController(itemView).navigate(R.id.crypto_Add)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_menu_item_layout, parent, false)

        return MainMenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainMenuViewHolder, position: Int) {
        holder.itemView.name_menu.text = mainMenuList[position].name_menu
        val item = mainMenuList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return mainMenuList.size
    }

    interface Listener {
        fun onCLick(mainMenuModel: MainMenuModel)
    }

}