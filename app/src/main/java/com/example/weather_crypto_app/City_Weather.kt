package com.example.weather_crypto_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.data.db.dbWeather.DbWeather
import com.example.weather_crypto_app.data.db.dbWeather.WeatherViewModel
import com.example.weather_crypto_app.data.names.city.CityNamesWeather
import com.example.weather_crypto_app.models.CityWeatherModel
import com.example.weather_crypto_app.presentation.ui.adapters.CityWeatherAdapter
import kotlinx.android.synthetic.main.activity_main.*

class City_Weather : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var adapter: CityWeatherAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city__weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        recyclerView = view.findViewById(R.id.rv_city_weather)
        toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        searchView = (activity as AppCompatActivity).toolbar.menu.findItem(R.id.search_info).actionView as SearchView

        val textChosen = view.findViewById<TextView>(R.id.chosen_city)
        weatherViewModel.readAllData.observe(viewLifecycleOwner, Observer { it ->
            if(it.isNotEmpty()) textChosen.text = "Выбран город: ${it[0].ruCityName}"
            else textChosen.text = "Не выбрано"
        })

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })

        adapter = CityWeatherAdapter(addWeatherItems())
        adapter.clickCallback = {type ->
            weatherViewModel.readAllData.observe(viewLifecycleOwner, Observer { it ->
                if(it.isNotEmpty()) weatherViewModel.updateCity(DbWeather(1, type.nameApiCity, type.fullNameCity))
                else weatherViewModel.addCity(DbWeather(0, type.nameApiCity, type.fullNameCity))
                bundle.putString("CityWeather", type.nameApiCity)
                findNavController().navigate(R.id.mainMenu, bundle)
            })
        }


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun addWeatherItems(): List<CityWeatherModel> {
        val items = mutableListOf<CityWeatherModel>()
        val cityName: CityNamesWeather = CityNamesWeather()
        cityName.cityNames.forEach {
            items.add(it)
        }
        return items
    }
}