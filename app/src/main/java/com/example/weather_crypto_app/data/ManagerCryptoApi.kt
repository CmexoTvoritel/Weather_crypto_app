package com.example.weather_crypto_app.data

import com.example.weather_crypto_app.models.CryptoAddModel
import com.example.weather_crypto_app.models.crypto.cryptoRep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ManagerCryptoApi {

    fun GetDataApi(): List<CryptoAddModel>{
        val data = arrayListOf<CryptoAddModel>()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val cryptoApi = retrofit.create(CryptoApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val infoCrypto = cryptoApi.getCrypto()
            infoCrypto.forEach {
                data.add(CryptoAddModel(0, it.image, it.name, false))
            }
        }
        return data
    }
}