package com.example.weather_crypto_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.adapters.MainMenuAdapter
import com.example.weather_crypto_app.models.MainMenuModel



class MainMenu : Fragment(), MainMenuAdapter.Listener {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MainMenuAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)
        recyclerView = view.findViewById(R.id.rv_main_menu)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MainMenuAdapter(add_menu_items())
        return view
    }

    private fun add_menu_items(): List<MainMenuModel> {
        val items = mutableListOf<MainMenuModel>()
        items.add(MainMenuModel("Карта", "Выбрать"))
        items.add(MainMenuModel("Погода", "Выбрать"))
        items.add(MainMenuModel("Курс криптовалют", "Выбрать"))
        return items
    }

    companion object {
        fun newInstance() = MainMenu()
    }

    override fun onCLick(mainMenuModel: MainMenuModel) {

    }

}