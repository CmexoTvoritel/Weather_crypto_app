package com.example.weather_crypto_app.presentation.ui.fragments

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
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.utils.RequestsToApiUtils
import com.example.weather_crypto_app.network.services.weather.CordsWeatherService
import com.example.weather_crypto_app.network.services.CryptoService
import com.example.weather_crypto_app.network.services.weather.WeatherService
import com.example.weather_crypto_app.presentation.viewmodel.CryptoViewModel
import com.example.weather_crypto_app.data.model.DbCrypto
import com.example.weather_crypto_app.presentation.viewmodel.MapViewModel
import com.example.weather_crypto_app.data.model.DbMenu
import com.example.weather_crypto_app.presentation.viewmodel.MenuViewModel
import com.example.weather_crypto_app.presentation.viewmodel.WeatherViewModel
import com.example.weather_crypto_app.data.model.city.PointCity
import com.example.weather_crypto_app.presentation.model.MainMenuModel
import com.example.weather_crypto_app.presentation.model.MainMenuModules
import com.example.weather_crypto_app.network.model.crypto.CryptoRepItem
import com.example.weather_crypto_app.network.model.crypto.Roi
import com.example.weather_crypto_app.presentation.model.WeatherMenuModel
import com.example.weather_crypto_app.network.model.weather.info.Clouds
import com.example.weather_crypto_app.network.model.weather.info.Coord
import com.example.weather_crypto_app.network.model.weather.info.Main
import com.example.weather_crypto_app.network.model.weather.info.Sys
import com.example.weather_crypto_app.network.model.weather.info.Weather
import com.example.weather_crypto_app.network.model.weather.info.WeatherInfo
import com.example.weather_crypto_app.network.model.weather.info.Wind
import com.example.weather_crypto_app.presentation.ui.adapters.MainMenuAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.floor


class MainMenuFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cryptoViewModel: CryptoViewModel
    private lateinit var mapViewModel: MapViewModel
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var adapter: MainMenuAdapter
    private lateinit var requestsToApiUtils: RequestsToApiUtils
    private var menuWeather: String = ""
    private var menuMap: String = ""
    private var menuCoins: String = ""
    private var textButton: String = ""
    private lateinit var adapterItems: MutableList<MainMenuModel>
    private lateinit var weatherInfo: WeatherMenuModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestsToApiUtils = RequestsToApiUtils()
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

    private fun addMenuItems(textMap: String?, textWeather: String?, coinsInfo: List<DbCrypto>, menuList: List<DbMenu>, needPoint: PointCity, weatherInfo: WeatherMenuModel): MutableList<MainMenuModel> {
        val items = mutableListOf<MainMenuModel>()
        menuList.forEach { menu ->
            when (menu.MenuName) {
                menuMap -> {
                    if(textMap != menuMap) items.add(MainMenuModel(menu.MenuName, textButton, true, type = MainMenuModules.MAP, coinsInfo, weatherInfo, needPoint))
                    else items.add(MainMenuModel(menu.MenuName, textButton, false, type = MainMenuModules.MAP, coinsInfo, weatherInfo, needPoint))
                }
                menuWeather -> {
                    if(weatherInfo.name_city != "NULL") {
                        if (textWeather != menuWeather) items.add(
                            MainMenuModel(menu.MenuName, textButton, true,
                            type = MainMenuModules.WEATHER, coinsInfo, weatherInfo, needPoint)
                        )
                        else items.add(
                            MainMenuModel(menu.MenuName, textButton, false,
                                type = MainMenuModules.WEATHER, coinsInfo, weatherInfo, needPoint)
                        )
                    }
                    else {
                        items.add(
                            MainMenuModel(menu.MenuName, textButton, true,
                            type = MainMenuModules.ERROR1, coinsInfo, weatherInfo, needPoint)
                        )
                    }
                }
                menuCoins -> {
                    if(coinsInfo.isNotEmpty()) {
                        if(coinsInfo[0].nameCoin != "")
                            items.add(MainMenuModel(menu.MenuName, textButton, true, type = MainMenuModules.COINS, coinsInfo, weatherInfo, needPoint))
                        else
                            items.add(MainMenuModel(menu.MenuName, textButton, true, type = MainMenuModules.ERROR2, coinsInfo, weatherInfo, needPoint))
                    }
                    else items.add(MainMenuModel(menu.MenuName, textButton, false, type = MainMenuModules.COINS, coinsInfo, weatherInfo, needPoint))
                }
            }
        }
        return items
    }

    private fun creatingRV(Map: String, Weather: String, coinsInfo: List<DbCrypto>, menuList: List<DbMenu>, needPoint: PointCity, view: View) {
        var coinInfo = coinsInfo
        val loadingMenu = view.findViewById<ImageView>(R.id.load_menu)
        //var adapterItems: List<MainMenuModel>
        var textWeather = Weather
        weatherInfo = WeatherMenuModel("",0.00, 0.00,
            "", "", 0.00, 0.00, 0, 0, 0.00, 0.00, 0.00)
        if(textWeather != menuWeather) {
            val infoWeatherCords = requestsToApiUtils.publicGenerateRequest("Cords") as CordsWeatherService
            val infoWeatherService = requestsToApiUtils.publicGenerateRequest("Weather") as WeatherService
            val infoCoinsService = requestsToApiUtils.publicGenerateRequest("Coin") as CryptoService
            CoroutineScope(Dispatchers.IO).launch {
                var infoWeather: WeatherInfo
                try {
                    val cordsWeather = infoWeatherCords.getCordsWeather(textWeather)
                    infoWeather = infoWeatherService.getWeather(cordsWeather[0].lat.toString(), cordsWeather[0].lon.toString(),
                        "22c2b837bf6f65a956144d42d02343bb", "ru", "metric")
                }catch (_: Exception) {
                    infoWeather = WeatherInfo("", Clouds(0), 0, Coord(0.0, 0.0), 0, 0,
                        Main(0.0, 0, 0, 0, 0, 0.0, 0.0, 0.0), "NULL", Sys("", 0, 0, 0, 0),
                        0, 0, listOf(Weather("", "", 0, "")), Wind(0, 0.0, 0.0)
                    )
                }
                if(coinsInfo.isNotEmpty()) {
                    var infoCrypto: List<CryptoRepItem>
                    try {
                        infoCrypto = infoCoinsService.getCrypto()
                    }catch (_ : Exception) {
                        infoCrypto = listOf(CryptoRepItem(0.0, 0.0, "", 0.0, 0.0, "",
                            0.0, 0.0, 0, 0.0, "", "", "",
                            0.0, 0, 0.0, 0.0, 0, 0.0, "",
                            0.0, 0.0, Roi("", 0.0, 0.0), "",
                            0.0, 0.0))
                    }
                    withContext(Dispatchers.Main) {
                        if(infoCrypto[0].name == "") {
                            coinInfo = listOf(DbCrypto(0, "", "", 0.0, 0.0))
                        }
                        else {
                            for (coin in infoCrypto) {
                                for (dbCoin in coinInfo) {
                                    if (dbCoin.nameCoin == coin.name) {
                                        dbCoin.costCoin =
                                            floor(coin.current_price * 100) / 100
                                        dbCoin.price_change =
                                            floor(coin.price_change_24h * 100) / 100
                                    }
                                }
                            }
                        }
                    }
                }

                withContext(Dispatchers.Main) {
                    textWeather = infoWeather.name
                    weatherInfo = setWeatherInfo(infoWeather)
                    adapterItems = addMenuItems(Map, textWeather, coinInfo, menuList, needPoint, weatherInfo)
                    adapter = MainMenuAdapter(requireContext(), adapterItems)
                    showRv(Map, Weather, coinsInfo, menuList, needPoint ,view)
                    loadingMenu.visibility = View.INVISIBLE
                    if(coinsInfo.isNotEmpty()) {
                        startLoopLoadCoins(menuList, coinInfo, textWeather, needPoint, Map, view)
                    }
                }
            }

        }
        else {
            adapter = MainMenuAdapter(requireContext(),
                addMenuItems(Map, textWeather, coinsInfo, menuList, needPoint, weatherInfo))
            showRv(Map, Weather, coinsInfo, menuList, needPoint, view)
            loadingMenu.visibility = View.INVISIBLE
        }
    }

    private fun updateCoinsInfo(infoCrypto: List<CryptoRepItem>, coinsInfo: List<DbCrypto>, textMap: String?,
                                textWeather: String?, menuList: List<DbMenu>, needPoint: PointCity,
                                weatherInfo: WeatherMenuModel, view: View) {
        if(infoCrypto[0].name == "") {
            val errorCoin = listOf(DbCrypto(0, "", "", 0.0, 0.0))
            adapterItems = addMenuItems(textMap, textWeather, errorCoin, menuList, needPoint, weatherInfo)
        }
        else {
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
            adapterItems = addMenuItems(textMap, textWeather, coinsInfo, menuList, needPoint, weatherInfo)
        }
        val position = menuList.indexOfFirst{ it.MenuName == menuCoins }
        adapter.updateItem(position, adapterItems[position])
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

    private fun showRv(Map: String, Weather: String, coinsInfo: List<DbCrypto>, menuList: List<DbMenu>, needPoint: PointCity, view: View) {
        addClickCallbackToRV(Map, Weather, coinsInfo, menuList, needPoint, view)
        recyclerView = view.findViewById(R.id.rv_main_menu)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun startLoopLoadCoins(menuList: List<DbMenu>, coinInfo: List<DbCrypto>, textWeather: String?, needPoint: PointCity, Map: String, view: View) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if(adapterItems[menuList.indexOfFirst{ it.MenuName == menuCoins }].type != MainMenuModules.ERROR2) {
                    val cryptoService = requestsToApiUtils.publicGenerateRequest("Coin") as CryptoService
                    CoroutineScope(Dispatchers.IO).launch {
                        var infoCrypto = emptyList<CryptoRepItem>()
                        try {
                            infoCrypto = cryptoService.getCrypto()
                        } catch (_: Exception) {
                            infoCrypto = listOf(CryptoRepItem(0.0, 0.0, "", 0.0, 0.0, "",
                                0.0, 0.0, 0, 0.0, "", "", "",
                                0.0, 0, 0.0, 0.0, 0, 0.0, "",
                                0.0, 0.0, Roi("", 0.0, 0.0), "",
                                0.0, 0.0))
                        }
                        withContext(Dispatchers.Main) {
                            updateCoinsInfo(
                                infoCrypto, coinInfo, Map, textWeather,
                                menuList, needPoint, weatherInfo, view
                            )
                        }
                    }
                    handler.postDelayed(this, 10000)
                }
            }
        }
        handler.postDelayed(runnable, 10000)
    }

    private fun addClickCallbackToRV(Map: String, Weather: String, coinsInfo: List<DbCrypto>, menuList: List<DbMenu>, needPoint: PointCity, view: View) {
        adapter.clickCallback = { type ->
            when (type) {
                MainMenuModules.MAP -> findNavController().navigate(R.id.city_Map)
                MainMenuModules.WEATHER -> findNavController().navigate(R.id.city_Weather)
                MainMenuModules.COINS -> findNavController().navigate(R.id.crypto_Add)
                MainMenuModules.ERROR1 -> {
                    val infoWeatherCords = requestsToApiUtils.publicGenerateRequest("Cords") as CordsWeatherService
                    val infoWeatherService = requestsToApiUtils.publicGenerateRequest("Weather") as WeatherService
                    CoroutineScope(Dispatchers.IO).launch {
                        var infoWeather: WeatherInfo
                        try {
                            val cordsWeather = infoWeatherCords.getCordsWeather(Weather)
                            infoWeather = infoWeatherService.getWeather(cordsWeather[0].lat.toString(), cordsWeather[0].lon.toString(),
                                "22c2b837bf6f65a956144d42d02343bb", "ru", "metric")
                        } catch (_: Exception) {
                            infoWeather = WeatherInfo("", Clouds(0), 0, Coord(0.0, 0.0), 0, 0,
                                Main(0.0, 0, 0, 0, 0, 0.0, 0.0, 0.0),
                                "NULL", Sys("", 0, 0, 0, 0),
                                0, 0, listOf(Weather("", "", 0, "")), Wind(0, 0.0, 0.0)
                            )
                        }
                        withContext(Dispatchers.Main) {
                            val textWeather = infoWeather.name
                            weatherInfo = setWeatherInfo(infoWeather)
                            adapterItems = addMenuItems(Map, textWeather, coinsInfo, menuList, needPoint, weatherInfo)
                            val position = menuList.indexOfFirst { it.MenuName == menuWeather }
                            adapter.updateItem(position, adapterItems[position])
                        }
                    }
                }
                MainMenuModules.ERROR2 -> {
                    val cryptoService =
                        requestsToApiUtils.publicGenerateRequest("Coin") as CryptoService
                    CoroutineScope(Dispatchers.IO).launch {
                        var infoCrypto = emptyList<CryptoRepItem>()
                        try {
                            infoCrypto = cryptoService.getCrypto()
                        } catch (_: Exception) {
                            infoCrypto = listOf(CryptoRepItem(0.0, 0.0, "", 0.0, 0.0, "",
                                0.0, 0.0, 0, 0.0, "", "", "",
                                0.0, 0, 0.0, 0.0, 0, 0.0, "",
                                0.0, 0.0, Roi("", 0.0, 0.0), "",
                                0.0, 0.0))
                        }
                        withContext(Dispatchers.Main) {
                            updateCoinsInfo(
                                infoCrypto, coinsInfo, Map, Weather,
                                menuList, needPoint, weatherInfo, view
                            )
                            if(adapterItems[menuList.indexOfFirst{ it.MenuName == menuCoins }].type != MainMenuModules.ERROR2) {
                                startLoopLoadCoins(menuList, coinsInfo, Weather, needPoint, Map, view)
                            }
                        }
                    }
                }
            }
        }
    }

}