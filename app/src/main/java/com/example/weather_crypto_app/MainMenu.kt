package com.example.weather_crypto_app

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.data.CordsWeatherApi
import com.example.weather_crypto_app.data.CryptoApi
import com.example.weather_crypto_app.data.WeatherApi
import com.example.weather_crypto_app.data.db.dbCrypto.CryptoViewModel
import com.example.weather_crypto_app.data.db.dbCrypto.DbCrypto
import com.example.weather_crypto_app.data.db.dbMap.MapViewModel
import com.example.weather_crypto_app.data.db.dbMenu.DbMenu
import com.example.weather_crypto_app.data.db.dbMenu.MenuViewModel
import com.example.weather_crypto_app.data.db.dbWeather.WeatherViewModel
import com.example.weather_crypto_app.data.names.city.PointCity
import com.example.weather_crypto_app.models.MainMenuModel
import com.example.weather_crypto_app.models.MainMenuModules
import com.example.weather_crypto_app.models.weather.WeatherMenuModel
import com.example.weather_crypto_app.presentation.ui.adapters.MainMenuAdapter
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.floor


class MainMenu : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var cryptoViewModel: CryptoViewModel
    private lateinit var mapViewModel: MapViewModel
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var adapter: MainMenuAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var textMap = "Карта"
        var textWeather = "Погода"
        var needPoint: PointCity = PointCity(0.0, 0.0)
        val coinsInfo = arrayListOf<DbCrypto>()
        val menuInfo = arrayListOf<DbMenu>()


        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        cryptoViewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
        menuViewModel = ViewModelProvider(this)[MenuViewModel::class.java]


        mapViewModel.readAllData.observe(viewLifecycleOwner, Observer { map ->
            if(map.isNotEmpty()) {
                textMap = map[0].CityName
                needPoint = PointCity(map[0].lan, map[0].lon)
            }
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
                        creatingRV(textMap, textWeather, coinsInfo, menuInfo, needPoint, view)
                    })
                })
            })
        })
    }

    private fun addMenuItems(textMap: String?, textWeather: String?, coinsInfo: List<DbCrypto>, menuList: List<DbMenu>, needPoint: PointCity, weatherInfo: WeatherMenuModel): List<MainMenuModel> {
        val items = mutableListOf<MainMenuModel>()
        menuList.forEach { menu ->
            when (menu.MenuName) {
                "Карта" -> {
                    if(textMap != "Карта") items.add(MainMenuModel(menu.MenuName, "Выбрать", true, type = MainMenuModules.MAP, coinsInfo, weatherInfo, needPoint))
                    else items.add(MainMenuModel(menu.MenuName, "Выбрать", false, type = MainMenuModules.MAP, coinsInfo, weatherInfo, needPoint))
                }
                "Погода" -> {
                    if(textWeather != "Погода") items.add(MainMenuModel(menu.MenuName, "Выбрать", true, type = MainMenuModules.WEATHER, coinsInfo, weatherInfo, needPoint))
                    else items.add(MainMenuModel(menu.MenuName, "Выбрать", false, type = MainMenuModules.WEATHER, coinsInfo, weatherInfo, needPoint))
                }
                "Курс криптовалют" -> {
                    if(!coinsInfo.isEmpty()) items.add(MainMenuModel(menu.MenuName, "Выбрать", true, type = MainMenuModules.COINS, coinsInfo, weatherInfo, needPoint))
                    else items.add(MainMenuModel(menu.MenuName, "Выбрать", false, type = MainMenuModules.COINS, coinsInfo, weatherInfo, needPoint))
                }
            }
        }
        return items
    }

    private fun creatingRV(Map: String, Weather: String, coinsInfo: List<DbCrypto>, menuList: List<DbMenu>, needPoint: PointCity, view: View) {
        var textWeather = Weather
        var textMap = Map
        var weatherInfo: WeatherMenuModel = WeatherMenuModel("",0.00, 0.00,
            "", "", 0.00, 0.00, 0, 0, 0.00, 0.00, 0.00)
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
                val infoWeather = infoWeatherApi.getWeather(coordsWeather[0].lat.toString(), coordsWeather[0].lon.toString(),
                    "22c2b837bf6f65a956144d42d02343bb", "ru", "metric")
                withContext(Dispatchers.Main) {
                    textWeather = infoWeather.name

                    weatherInfo.name_city = infoWeather.name
                    weatherInfo.temp = infoWeather.main.temp
                    weatherInfo.feel_temp = infoWeather.main.feels_like
                    weatherInfo.name_weather = infoWeather.weather[0].description
                    weatherInfo.icon = infoWeather.weather[0].icon
                    weatherInfo.wind = infoWeather.wind.speed
                    weatherInfo.pressure = infoWeather.main.pressure.toDouble()
                    weatherInfo.wetness = infoWeather.main.humidity
                    weatherInfo.cloud = infoWeather.clouds.all
                    weatherInfo.visibility_weather = infoWeather.visibility.toDouble()
                    weatherInfo.min_temp = infoWeather.main.temp_min
                    weatherInfo.max_temp = infoWeather.main.temp_max

                    adapter = MainMenuAdapter(requireContext(), addMenuItems(textMap, textWeather, coinsInfo, menuList, needPoint, weatherInfo))
                    adapter.clickCallback = { type ->
                        when (type) {
                            MainMenuModules.MAP -> findNavController().navigate(R.id.city_Map)
                            MainMenuModules.WEATHER -> findNavController().navigate(R.id.city_Weather)
                            MainMenuModules.COINS -> findNavController().navigate(R.id.crypto_Add)
                        }
                    }
                    recyclerView = view.findViewById(R.id.rv_main_menu)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = adapter
                    if(coinsInfo.isNotEmpty()) {
                        val handler = Handler(Looper.getMainLooper())
                        val runnable = object : Runnable {
                            override fun run() {
                                val retrofit = Retrofit.Builder()
                                    .baseUrl("https://api.coingecko.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                                val cryptoApi = retrofit.create(CryptoApi::class.java)
                                CoroutineScope(Dispatchers.IO).launch {
                                    val infoCrypto = cryptoApi.getCrypto()
                                    withContext(Dispatchers.Main) {
                                        for (coin in infoCrypto) {
                                            for (dbCoin in coinsInfo) {
                                                if (dbCoin.nameCoin == coin.name) {
                                                    dbCoin.costCoin =
                                                        floor(coin.current_price * 100) / 100
                                                    dbCoin.price_change =
                                                        floor(coin.price_change_24h * 100) / 100
                                                }
                                            }
                                        }
                                        adapter = MainMenuAdapter(
                                            requireContext(),
                                            addMenuItems(
                                                textMap,
                                                textWeather,
                                                coinsInfo,
                                                menuList,
                                                needPoint,
                                                weatherInfo
                                            )
                                        )
                                        adapter = recyclerView.adapter as MainMenuAdapter
                                        val position = menuList.indexOfFirst{ it.MenuName == "Курс криптовалют" }
                                        adapter.notifyItemChanged(position)
                                    }
                                }
                                handler.postDelayed(this, 20000)
                            }
                        }
                        handler.postDelayed(runnable, 20000)
                    }
                }
            }

        }
        else {
            val adapter = MainMenuAdapter(requireContext(), addMenuItems(textMap, textWeather, coinsInfo, menuList, needPoint, weatherInfo))
            adapter.clickCallback = { type ->
                when (type) {
                    MainMenuModules.MAP -> findNavController().navigate(R.id.city_Map)
                    MainMenuModules.WEATHER -> findNavController().navigate(R.id.city_Weather)
                    MainMenuModules.COINS -> findNavController().navigate(R.id.crypto_Add)
                }
            }
            recyclerView = view.findViewById(R.id.rv_main_menu)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }
    }

    companion object {
        fun newInstance() = MainMenu()
    }

}