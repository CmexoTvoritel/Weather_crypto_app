package com.example.weather_crypto_app

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
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
import com.example.weather_crypto_app.models.crypto.CryptoRepItem
import com.example.weather_crypto_app.models.weather.WeatherMenuModel
import com.example.weather_crypto_app.models.weather.info.WeatherInfo
import com.example.weather_crypto_app.presentation.ui.adapters.MainMenuAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.floor


class MainMenu : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cryptoViewModel: CryptoViewModel
    private lateinit var mapViewModel: MapViewModel
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var adapter: MainMenuAdapter
    private var menuWeather: String = ""
    private var menuMap: String = ""
    private var menuCoins: String = ""
    private var textButton: String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuWeather = getString(R.string.menu_name_weather)
        menuMap = getString(R.string.menu_name_map)
        menuCoins = getString(R.string.menu_name_coins)
        textButton = getString(R.string.text_choose_button)
        var textMap = menuMap
        var textWeather = menuWeather
        var needPoint = PointCity(0.0, 0.0)
        val coinsInfo = arrayListOf<DbCrypto>()
        val menuInfo = arrayListOf<DbMenu>()
        val loadingMenu = view.findViewById<ImageView>(R.id.load_menu)
        loadingMenu.visibility = View.VISIBLE
        val animDrawable = loadingMenu.drawable as AnimatedVectorDrawable
        animDrawable.start()

        openDataBases()

        mapViewModel.readAllData.observe(viewLifecycleOwner) { map ->
            if (map.isNotEmpty()) {
                textMap = map[0].CityName
                needPoint = PointCity(map[0].lan, map[0].lon)
            }
            weatherViewModel.readAllData.observe(viewLifecycleOwner) { weather ->
                if (weather.isNotEmpty()) textWeather = weather[0].CityName
                cryptoViewModel.readAllData.observe(viewLifecycleOwner) { coins ->
                    coinsInfo.clear()
                    coins.forEach { coinsInfo.add(it) }
                    menuViewModel.readAllData.observe(viewLifecycleOwner) { menu ->
                        menuInfo.clear()
                        if (menu.isNotEmpty()) menu.forEach { menuInfo.add(it) }
                        else {
                            menuViewModel.addMenu(DbMenu(0, menuMap))
                            menuViewModel.addMenu(DbMenu(0, menuWeather))
                            menuViewModel.addMenu(DbMenu(0, menuCoins))
                        }
                        creatingRV(textMap, textWeather, coinsInfo, menuInfo, needPoint, view)
                    }
                }
            }
        }
    }

    private fun addMenuItems(textMap: String?, textWeather: String?, coinsInfo: List<DbCrypto>, menuList: List<DbMenu>, needPoint: PointCity, weatherInfo: WeatherMenuModel): List<MainMenuModel> {
        val items = mutableListOf<MainMenuModel>()
        menuList.forEach { menu ->
            when (menu.MenuName) {
                menuMap -> {
                    if(textMap != menuMap) items.add(MainMenuModel(menu.MenuName, textButton, true, type = MainMenuModules.MAP, coinsInfo, weatherInfo, needPoint))
                    else items.add(MainMenuModel(menu.MenuName, textButton, false, type = MainMenuModules.MAP, coinsInfo, weatherInfo, needPoint))
                }
                menuWeather -> {
                    if(textWeather != menuWeather) items.add(MainMenuModel(menu.MenuName, textButton, true, type = MainMenuModules.WEATHER, coinsInfo, weatherInfo, needPoint))
                    else items.add(MainMenuModel(menu.MenuName, textButton, false, type = MainMenuModules.WEATHER, coinsInfo, weatherInfo, needPoint))
                }
                menuCoins -> {
                    if(coinsInfo.isNotEmpty()) items.add(MainMenuModel(menu.MenuName, textButton, true, type = MainMenuModules.COINS, coinsInfo, weatherInfo, needPoint))
                    else items.add(MainMenuModel(menu.MenuName, textButton, false, type = MainMenuModules.COINS, coinsInfo, weatherInfo, needPoint))
                }
            }
        }
        return items
    }

    private fun creatingRV(Map: String, Weather: String, coinsInfo: List<DbCrypto>, menuList: List<DbMenu>, needPoint: PointCity, view: View) {
        val loadingMenu = view.findViewById<ImageView>(R.id.load_menu)
        var adapterItems: List<MainMenuModel>
        var textWeather = Weather
        var weatherInfo = WeatherMenuModel("",0.00, 0.00,
            "", "", 0.00, 0.00, 0, 0, 0.00, 0.00, 0.00)
        if(textWeather != menuWeather) {
            val infoWeatherCoords = generateRequestToApiCords()
            val infoWeatherApi = generateRequestToApiWeather()
            CoroutineScope(Dispatchers.IO).launch {
                val coordsWeather = infoWeatherCoords.getCordsWeather(textWeather)
                val infoWeather = infoWeatherApi.getWeather(coordsWeather[0].lat.toString(), coordsWeather[0].lon.toString(),
                    "22c2b837bf6f65a956144d42d02343bb", "ru", "metric")
                withContext(Dispatchers.Main) {
                    textWeather = infoWeather.name
                    weatherInfo = setWeatherInfo(infoWeather)
                    adapterItems =
                        addMenuItems(Map, textWeather, coinsInfo, menuList, needPoint, weatherInfo)
                    adapter = MainMenuAdapter(requireContext(), adapterItems)
                    showRv(view)
                    loadingMenu.visibility = View.INVISIBLE
                    if(coinsInfo.isNotEmpty()) {
                        val handler = Handler(Looper.getMainLooper())
                        val runnable = object : Runnable {
                            override fun run() {
                                val cryptoApi = generateRequestToApiCoins()
                                CoroutineScope(Dispatchers.IO).launch {
                                    val infoCrypto = cryptoApi.getCrypto()
                                    withContext(Dispatchers.Main) {
                                        updateCoinsInfo(infoCrypto, coinsInfo, Map, textWeather,
                                            menuList, needPoint, weatherInfo)
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
            adapter = MainMenuAdapter(requireContext(),
                addMenuItems(Map, textWeather, coinsInfo, menuList, needPoint, weatherInfo))
            showRv(view)
            loadingMenu.visibility = View.INVISIBLE
        }
    }

    private fun updateCoinsInfo(infoCrypto: List<CryptoRepItem>, coinsInfo: List<DbCrypto>, textMap: String?,
                        textWeather: String?, menuList: List<DbMenu>, needPoint: PointCity,
                        weatherInfo: WeatherMenuModel) {
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
        val adapterItems: List<MainMenuModel> =
            addMenuItems(textMap, textWeather, coinsInfo, menuList, needPoint, weatherInfo)
        adapter = MainMenuAdapter(
            requireContext(),
            adapterItems
        )
        adapter = recyclerView.adapter as MainMenuAdapter
        val position = menuList.indexOfFirst{ it.MenuName == menuCoins }
        adapter.notifyItemChanged(position)
    }

    private fun setWeatherInfo(infoWeather: WeatherInfo): WeatherMenuModel {
        val weatherInfo = WeatherMenuModel()
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
        return weatherInfo
    }

    private fun openDataBases() {
        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        cryptoViewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
        menuViewModel = ViewModelProvider(this)[MenuViewModel::class.java]
    }

    private fun showRv(view: View) {
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

    private fun generateRequestToApiCords(): CordsWeatherApi {
        val retrofitCords = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitCords.create(CordsWeatherApi::class.java)
    }

    private fun generateRequestToApiWeather(): WeatherApi {
        val retrofitInfo = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitInfo.create(WeatherApi::class.java)
    }

    private fun generateRequestToApiCoins(): CryptoApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CryptoApi::class.java)
    }

}