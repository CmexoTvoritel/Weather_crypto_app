package com.example.weather_crypto_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.weather_crypto_app.data.CordsWeatherApi
import com.example.weather_crypto_app.data.HelpGenerateApi
import com.example.weather_crypto_app.data.WeatherApi
import com.example.weather_crypto_app.data.db.CryptoDataBase
import com.example.weather_crypto_app.data.db.DbCrypto
import com.example.weather_crypto_app.presentation.ui.adapters.MainMenuAdapter
import com.example.weather_crypto_app.models.MainMenuModel
import com.example.weather_crypto_app.models.MainMenuModules
import com.example.weather_crypto_app.models.weather.info.weatherInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainMenu : Fragment() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textMap = arguments?.getString("CityMap")
        var textWeather = arguments?.getString("CityWeather")
        if(!textWeather.isNullOrBlank()) {
            val infoToast = Toast.makeText(context, "${textWeather}", Toast.LENGTH_SHORT)
            infoToast.show()
            //val helpGenerateWeatherApi: HelpGenerateWeatherApi = HelpGenerateWeatherApi()
            //helpGenerateWeatherApi.generateApiWeather(textWeather)
            //val data = helpGenerateWeatherApi.getDataWeatherApi(55.7504461, 37.6174943)
            val retrofitCords = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val infoWeatherCoords = retrofitCords.create(CordsWeatherApi::class.java)

            val retrofitInfo = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val infoWeatherApi = retrofitInfo.create(WeatherApi::class.java)
            lifecycleScope.launch {
                val coordsWeather = infoWeatherCoords.getCordsWeather(textWeather)
                val infoWeather = infoWeatherApi.getWeather(coordsWeather[0].lat.toString(), coordsWeather[0].lon.toString(), "22c2b837bf6f65a956144d42d02343bb")
                withContext(Dispatchers.Main) {
                    textWeather = infoWeather.name
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
            }

        }
        else {
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

        //if(!textWeather.isNullOrBlank()) helpGenerateApi.helpGenerateApi(textWeather.toString())
        //TODO
        /*
        val dbCrypto = context?.let {
            Room.databaseBuilder(
                it, CryptoDataBase::class.java, "crypto-data-base"
            ).build()
        }

        val dbDao = dbCrypto?.dbDao()
        val coins: List<DbCrypto>? = dbDao?.getAll()
        coins?.forEach {
            val infoToast = Toast.makeText(context, "${it.uid} ${it.nameCoin} ${it.costCoin}", Toast.LENGTH_SHORT)
            infoToast.show()
        } */
        //TODO
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

private class HelpGenerateWeatherApi() {
    fun generateApiWeather(city: String?) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val cordsApi = retrofit.create(CordsWeatherApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val cords = cordsApi.getCordsWeather(city)
            val lat = cords[0].lat
            val lon = cords[0].lon
        }
    }

    fun getDataWeatherApi(lat: Double, lon: Double) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val infoWeatherApi = retrofit.create(WeatherApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val infoWeather = infoWeatherApi.getWeather(lat.toString(), lon.toString(), "22c2b837bf6f65a956144d42d02343bb")
        }
    }
}