package com.example.weather_crypto_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.models.CityWeatherModel
import com.example.weather_crypto_app.presentation.ui.adapters.CityWeatherAdapter

class City_Weather : Fragment() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city__weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CityWeatherAdapter(addWeatherItems())
        recyclerView = view.findViewById(R.id.rv_city_weather)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun addWeatherItems(): List<CityWeatherModel> {
        val items = mutableListOf<CityWeatherModel>()
        items.add(CityWeatherModel("RU", "Москва", "Moscow"))
        items.add(CityWeatherModel("RU", "Ростов на Дону", "Rostov"))
        items.add(CityWeatherModel("RU", "Таганрог", "Taganrog"))
        return items
    }
}