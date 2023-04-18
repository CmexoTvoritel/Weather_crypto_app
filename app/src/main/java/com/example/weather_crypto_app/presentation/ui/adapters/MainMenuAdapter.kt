package com.example.weather_crypto_app.presentation.ui.adapters

import android.content.ContentProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.data.db.dbCrypto.CryptoViewModel
import com.example.weather_crypto_app.data.db.dbCrypto.DbCrypto
import com.example.weather_crypto_app.models.MainMenuModel
import com.example.weather_crypto_app.models.MainMenuModules
import com.example.weather_crypto_app.presentation.ui.viewholders.CryptoMenuViewHolder
import com.example.weather_crypto_app.presentation.ui.viewholders.MainMenuViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_menu_item_layout.view.*
import kotlin.math.floor

class MainMenuAdapter(private val mainMenuList: List<MainMenuModel>): RecyclerView.Adapter<MainMenuViewHolder>() {

    private lateinit var recyclerView: RecyclerView

    var clickCallback: ((type: MainMenuModules) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_menu_item_layout, parent, false)
        return MainMenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainMenuViewHolder, position: Int) {
        holder.itemView.name_menu.text = mainMenuList[position].nameMenu
        val item = mainMenuList[position]
        holder.itemView.rv_coin_info.visibility = View.INVISIBLE
        holder.itemView.card_weather.visibility = View.INVISIBLE
        if(mainMenuList[position].nameMenu == "Курс криптовалют" && mainMenuList[position].status) {
            holder.itemView.name_button.visibility = View.INVISIBLE
            holder.itemView.settings_butt.visibility = View.VISIBLE
            holder.itemView.rv_coin_info.visibility = View.VISIBLE
            val adapter = CryptoMenuAdapter(mainMenuList[position].cryptoList)
            recyclerView = holder.itemView.rv_coin_info
            recyclerView.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter

            //TODO отображение RV horizontal
        }
        else if(mainMenuList[position].nameMenu == "Карта" && mainMenuList[position].status) {
            holder.itemView.name_button.visibility = View.INVISIBLE
            holder.itemView.settings_butt.visibility = View.VISIBLE
        }
        else if(mainMenuList[position].nameMenu == "Погода" && mainMenuList[position].status) {
            holder.itemView.name_button.visibility = View.INVISIBLE
            holder.itemView.settings_butt.visibility = View.VISIBLE
            holder.itemView.card_weather.visibility = View.VISIBLE
            holder.itemView.name_city_weather.text = mainMenuList[position].weatherList.name_city
            holder.itemView.id_weather.text = mainMenuList[position].weatherList.name_weather
            holder.itemView.current_temp.text = mainMenuList[position].weatherList.temp.toString() + "°C"
            Picasso.get()
                .load("https://openweathermap.org/img/wn/${mainMenuList[position].weatherList.icon}@2x.png")
                .placeholder(R.drawable.usd_coin_usdc_1)
                .fit()
                .into(holder.itemView.weather_image)
            holder.itemView.temp_feel.text = mainMenuList[position].weatherList.feel_temp.toString() + "°C"
            holder.itemView.wind_info.text = mainMenuList[position].weatherList.wind.toString() + " м/с"
            holder.itemView.pressure_weather.text = (floor((mainMenuList[position].weatherList.pressure * 0.750063755419211) * 100)/100).toString() + "мм"
            holder.itemView.wetness_weather.text = mainMenuList[position].weatherList.wetness.toString() + " %"
            holder.itemView.cloud_weather.text = mainMenuList[position].weatherList.cloud.toString() + " %"
            holder.itemView.visibility_weather.text = (floor((mainMenuList[position].weatherList.visibility_weather / 1000) * 100) / 100).toString() + " км"
            holder.itemView.low_temp.text = mainMenuList[position].weatherList.min_temp.toString() + "°"
            holder.itemView.high_temp.text = mainMenuList[position].weatherList.max_temp.toString() + "°"
        }
        else {
            holder.itemView.name_button.visibility = View.VISIBLE
            holder.itemView.settings_butt.visibility = View.INVISIBLE
        }
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