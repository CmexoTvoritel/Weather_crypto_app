package com.example.weather_crypto_app.presentation.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.VectorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.databinding.MainMenuItemLayoutBinding
import com.example.weather_crypto_app.models.MainMenuModel
import com.example.weather_crypto_app.models.MainMenuModules
import com.example.weather_crypto_app.presentation.ui.viewholders.MainMenuViewHolder
import com.squareup.picasso.Picasso
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.runtime.image.ImageProvider
import kotlin.math.floor

class MainMenuAdapter(private val context: Context, private val mainMenuList: List<MainMenuModel>): RecyclerView.Adapter<MainMenuViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    private val menuMap = "Карта"
    private val menuWeather = "Погода"
    private val menuCoins = "Курс криптовалют"

    var clickCallback: ((type: MainMenuModules) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenuViewHolder {
        val binding = MainMenuItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainMenuViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MainMenuViewHolder, position: Int) {
        with(holder) {
            with(mainMenuList[position]) {
                binding.loadCard.visibility = View.VISIBLE
                val anim = binding.loadCard.drawable as AnimatedVectorDrawable
                anim.start()
                binding.loadErrorMessage.visibility = View.INVISIBLE
                binding.reloadButton.visibility = View.INVISIBLE
                binding.rvCoinInfo.visibility = View.INVISIBLE
                binding.cardWeather.visibility = View.INVISIBLE
                binding.mapCard.visibility = View.INVISIBLE
                binding.noMap.visibility = View.INVISIBLE
                binding.noWeather.visibility = View.INVISIBLE
                binding.noCoin1.visibility = View.INVISIBLE
                binding.noCoin2.visibility = View.INVISIBLE
                binding.noCoin3.visibility = View.INVISIBLE
                binding.nameButton.visibility = View.INVISIBLE
                binding.settingsButt.visibility = View.INVISIBLE

                binding.nameMenu.text = mainMenuList[position].nameMenu

                when(this.status) {
                    true -> {
                        when(this.nameMenu) {
                            menuCoins -> {
                                binding.settingsButt.visibility = View.VISIBLE
                                binding.rvCoinInfo.visibility = View.VISIBLE
                                val adapterCrypto = CryptoMenuAdapter(this.cryptoList)
                                recyclerView = binding.rvCoinInfo
                                recyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                                recyclerView.adapter = adapterCrypto
                                adapterCrypto.notifyDataSetChanged()
                            }
                            menuMap -> {
                                binding.settingsButt.visibility = View.VISIBLE
                                binding.mapCard.visibility = View.VISIBLE
                                binding.mapView.map.move(
                                    CameraPosition(Point(this.needPoint.lan, this.needPoint.lon), 8.0f, 0.0f, 0.0f),
                                    Animation(Animation.Type.SMOOTH, 3f), null
                                )
                                val vectorDrawable = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_baseline_location_on_24, null) as VectorDrawable
                                val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
                                val canvas = Canvas(bitmap)
                                vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
                                vectorDrawable.draw(canvas)
                                val imagePoint = ImageProvider.fromBitmap(bitmap)

                                val mapObjectCollection = binding.mapView.map.mapObjects.addCollection()
                                mapObjectCollection.addPlacemark(
                                    Point(
                                        this.needPoint.lan,
                                        this.needPoint.lon
                                    ),imagePoint
                                )
                                binding.mapView.map.isScrollGesturesEnabled = false
                                binding.mapView.map.isZoomGesturesEnabled = false
                            }
                            menuWeather -> {
                                if(this.weatherList.name_city != "NULL") {
                                    binding.settingsButt.visibility = View.VISIBLE
                                    binding.cardWeather.visibility = View.VISIBLE
                                    binding.nameCityWeather.text = this.weatherList.name_city
                                    binding.idWeather.text = this.weatherList.name_weather
                                    binding.currentTemp.text = this.weatherList.temp.toString()
                                    Picasso.get()
                                        .load("https://openweathermap.org/img/wn/${this.weatherList.icon}@2x.png")
                                        .placeholder(R.drawable.usd_coin_usdc_1)
                                        .fit()
                                        .into(binding.weatherImage)
                                    binding.tempFeel.text = this.weatherList.feel_temp.toString()
                                    binding.windInfo.text = this.weatherList.wind.toString()
                                    binding.pressureInfo.text =
                                        (floor((this.weatherList.pressure * 0.750063755419211) * 100) / 100).toString()
                                    binding.wetnessInfo.text = this.weatherList.wetness.toString()
                                    binding.cloudsInfo.text = this.weatherList.cloud.toString()
                                    binding.lowTemp.text = this.weatherList.min_temp.toString()
                                    binding.highTemp.text = this.weatherList.max_temp.toString()
                                }
                                else {
                                    binding.settingsButt.visibility = View.VISIBLE
                                    binding.reloadButton.visibility = View.VISIBLE
                                    binding.loadErrorMessage.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                    false -> {
                        when(this.nameMenu) {
                            menuCoins -> {
                                binding.nameButton.visibility = View.VISIBLE
                                binding.noCoin1.visibility = View.VISIBLE
                                binding.noCoin2.visibility = View.VISIBLE
                                binding.noCoin3.visibility = View.VISIBLE
                            }
                            menuMap -> {
                                binding.nameButton.visibility = View.VISIBLE
                                binding.noMap.visibility = View.VISIBLE
                            }
                            menuWeather -> {
                                binding.nameButton.visibility = View.VISIBLE
                                binding.noWeather.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                binding.loadCard.visibility = View.GONE
            }
        }
        holder.clickCallback = clickCallback
        holder.bind(mainMenuList[position])
    }

    override fun onViewRecycled(holder: MainMenuViewHolder) {
        holder.unbind()
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int {
        return mainMenuList.size
    }

}