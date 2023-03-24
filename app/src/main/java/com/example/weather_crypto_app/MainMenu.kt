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



class MainMenu : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MainMenuAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.rv_main_menu)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MainMenuAdapter(add_menu_items())
    }

    /*override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)
        recyclerView = view.findViewById(R.id.rv_main_menu)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MainMenuAdapter(add_menu_items())
        return view
    } */

    private fun add_menu_items(): List<MainMenuModel> {
        val items = mutableListOf<MainMenuModel>()
        items.add(MainMenuModel("Maps", "Выбрать"))
        items.add(MainMenuModel("Town", "Выбрать"))
        items.add(MainMenuModel("Crypto", "Выбрать"))
        return items
    }

}