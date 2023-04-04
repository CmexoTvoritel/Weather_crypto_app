package com.example.weather_crypto_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.data.CordsWeatherApi
import com.example.weather_crypto_app.data.WeatherApi
import com.example.weather_crypto_app.data.names.city.CityNamesWeather
import com.example.weather_crypto_app.models.CityWeatherModel
import com.example.weather_crypto_app.presentation.ui.adapters.CityWeatherAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class City_Weather : Fragment() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city__weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()

        val adapter = CityWeatherAdapter(addWeatherItems())

        adapter.clickCallback = {type ->
            bundle.putString("CityWeather", type.nameApiCity)
            findNavController().navigate(R.id.mainMenu, bundle)}

        recyclerView = view.findViewById(R.id.rv_city_weather)
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