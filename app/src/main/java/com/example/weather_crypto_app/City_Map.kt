package com.example.weather_crypto_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.data.names.city.CityNamesMap
import com.example.weather_crypto_app.models.CityMapModel
import com.example.weather_crypto_app.presentation.ui.adapters.CityMapAdapter

class City_Map : Fragment() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city__map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()

        val adapter = CityMapAdapter(addMapItems())

        adapter.clickCallback = { type->
            bundle.putString("CityMap", type.nameApiCity)
            findNavController().navigate(R.id.mainMenu, bundle) }


        recyclerView = view.findViewById(R.id.rv_city_map)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun addMapItems(): List<CityMapModel> {
        val items = mutableListOf<CityMapModel>()
        val cityNamesMap: CityNamesMap = CityNamesMap()
        cityNamesMap.cityNames.forEach {
            items.add(it)
        }
        return items
    }
}