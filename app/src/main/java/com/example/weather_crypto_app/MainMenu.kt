package com.example.weather_crypto_app

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.data.CordsWeatherApi
import com.example.weather_crypto_app.data.WeatherApi
import com.example.weather_crypto_app.data.db.dbCrypto.CryptoViewModel
import com.example.weather_crypto_app.data.db.dbCrypto.DbCrypto
import com.example.weather_crypto_app.data.db.dbMap.DbMap
import com.example.weather_crypto_app.data.db.dbMap.DbMapDao
import com.example.weather_crypto_app.data.db.dbMap.MapViewModel
import com.example.weather_crypto_app.data.db.dbMenu.DbMenu
import com.example.weather_crypto_app.data.db.dbMenu.MenuViewModel
import com.example.weather_crypto_app.data.db.dbWeather.DbWeather
import com.example.weather_crypto_app.data.db.dbWeather.WeatherViewModel
import com.example.weather_crypto_app.presentation.ui.adapters.MainMenuAdapter
import com.example.weather_crypto_app.models.MainMenuModel
import com.example.weather_crypto_app.models.MainMenuModules
import com.example.weather_crypto_app.presentation.ui.adapters.CryptoMenuAdapter
import com.example.weather_crypto_app.presentation.ui.viewholders.MainMenuViewHolder
import kotlinx.android.synthetic.main.main_menu_item_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainMenu : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var cryptoViewModel: CryptoViewModel
    private lateinit var mapViewModel: MapViewModel
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var menuViewModel: MenuViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var textMap = "Карта"
        var textWeather = "Погода"
        val coinsInfo = arrayListOf<DbCrypto>()
        val menuInfo = arrayListOf<DbMenu>()

        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        cryptoViewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
        menuViewModel = ViewModelProvider(this)[MenuViewModel::class.java]


        mapViewModel.readAllData.observe(viewLifecycleOwner, Observer { map ->
            if(map.isNotEmpty()) textMap = map[0].CityName
            weatherViewModel.readAllData.observe(viewLifecycleOwner, Observer { weather ->
                if(weather.isNotEmpty()) textWeather = weather[0].CityName
                cryptoViewModel.readAllData.observe(viewLifecycleOwner, Observer { coins ->
                    coinsInfo.clear()
                    coins.forEach { coinsInfo.add(it) }
                    menuViewModel.readAllData.observe(viewLifecycleOwner, Observer { menu ->
                        menuInfo.clear()
                        if(menu.isNotEmpty()) menu.forEach { menuInfo.add(it) }
                        else {
                            menuViewModel.addMenu(DbMenu(0, "Карта"))
                            menuViewModel.addMenu(DbMenu(0, "Погода"))
                            menuViewModel.addMenu(DbMenu(0, "Курс криптовалют"))
                        }
                        creatingRV(textMap.toString(), textWeather.toString(), coinsInfo, menuInfo, view)
                    })
                })
            })
        })
    }

    private fun addMenuItems(textMap: String?, textWeather: String?, coinsInfo: List<DbCrypto>, menuList: List<DbMenu>): List<MainMenuModel> {
        val items = mutableListOf<MainMenuModel>()
        menuList.forEach { menu ->
            when (menu.MenuName) {
                "Карта" -> {
                    if(textMap != "Карта") items.add(MainMenuModel(menu.MenuName, "Выбрать", true, type = MainMenuModules.MAP, coinsInfo))
                    else items.add(MainMenuModel(menu.MenuName, "Выбрать", false, type = MainMenuModules.MAP, coinsInfo))
                }
                "Погода" -> {
                    if(textWeather != "Погода") items.add(MainMenuModel(menu.MenuName, "Выбрать", true, type = MainMenuModules.WEATHER, coinsInfo))
                    else items.add(MainMenuModel(menu.MenuName, "Выбрать", false, type = MainMenuModules.WEATHER, coinsInfo))
                }
                "Курс криптовалют" -> {
                    if(!coinsInfo.isEmpty()) items.add(MainMenuModel(menu.MenuName, "Выбрать", true, type = MainMenuModules.COINS, coinsInfo))
                    else items.add(MainMenuModel(menu.MenuName, "Выбрать", false, type = MainMenuModules.COINS, coinsInfo))
                }
            }
        }
        return items
    }

    private fun creatingRV(Map: String, Weather: String, coinsInfo: List<DbCrypto>, menuList: List<DbMenu>, view: View) {
        var textWeather = Weather
        var textMap = Map
        if(textWeather != "Погода") {
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
            CoroutineScope(Dispatchers.IO).launch {
                val coordsWeather = infoWeatherCoords.getCordsWeather(textWeather)
                val infoWeather = infoWeatherApi.getWeather(coordsWeather[0].lat.toString(), coordsWeather[0].lon.toString())
                withContext(Dispatchers.Main) {
                    textWeather = infoWeather.name
                    val adapter = MainMenuAdapter(addMenuItems(textMap, textWeather, coinsInfo, menuList))
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
                    Toast.makeText(context, "HEEE", Toast.LENGTH_SHORT).show()
                    //Toast.makeText(context, "${coinsInfo[2].nameCoin}", Toast.LENGTH_SHORT).show()
                }
            }

        }
        else {
            val adapter = MainMenuAdapter(addMenuItems(textMap, textWeather, coinsInfo, menuList))
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

    companion object {
        fun newInstance() = MainMenu()
    }

}