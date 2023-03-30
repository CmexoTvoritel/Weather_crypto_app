package com.example.weather_crypto_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.presentation.ui.adapters.MainMenuAdapter
import com.example.weather_crypto_app.models.MainMenuModel
import com.example.weather_crypto_app.models.MainMenuModules


class MainMenu : Fragment() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textMap = arguments?.getString("CityMap")
        val textWeather = arguments?.getString("CityWeather")
        val adapter = MainMenuAdapter(addMenuItems(textMap, textWeather))
        adapter.clickCallback = { type ->
            when (type) {
                MainMenuModules.MAP -> findNavController().navigate(R.id.city_Map)
                MainMenuModules.WEATHER -> findNavController().navigate(R.id.city_Weather)
                MainMenuModules.COINS -> findNavController().navigate(R.id.crypto_Add)
            }
        }
        recyclerView = view.findViewById(R.id.rv_main_menu)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun addMenuItems(textMap: String?, textWeather: String?): List<MainMenuModel> {
        val items = mutableListOf<MainMenuModel>()
        if(!textMap.isNullOrBlank()) items.add(MainMenuModel(textMap, "Выбрать", type = MainMenuModules.MAP))
        else items.add(MainMenuModel("Карта", "Выбрать", type = MainMenuModules.MAP))
        if(!textWeather.isNullOrBlank()) items.add(MainMenuModel(textWeather, "Выбрать", type = MainMenuModules.WEATHER))
        else items.add(MainMenuModel("Погода", "Выбрать", type = MainMenuModules.WEATHER))
        items.add(MainMenuModel("Курс криптовалют", "Выбрать", type = MainMenuModules.COINS))
        return items
    }

    companion object {
        fun newInstance() = MainMenu()
    }

}