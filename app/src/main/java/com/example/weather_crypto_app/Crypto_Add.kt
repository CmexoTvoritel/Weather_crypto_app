package com.example.weather_crypto_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.data.CryptoApi
import com.example.weather_crypto_app.data.ManagerCryptoApi
import com.example.weather_crypto_app.models.CryptoAddModel
import com.example.weather_crypto_app.models.MainMenuModel
import com.example.weather_crypto_app.models.MainMenuModules
import com.example.weather_crypto_app.presentation.ui.adapters.CryptoAddAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Crypto_Add : Fragment() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_crypto__add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val managerCryptoApi: ManagerCryptoApi = ManagerCryptoApi()

        //val adapter = CryptoAddAdapter(managerCryptoApi.GetDataCrypto())

        val data = arrayListOf<CryptoAddModel>()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val cryptoApi = retrofit.create(CryptoApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            data.add(CryptoAddModel("", "Bitcoin", false))
            val infoCrypto = cryptoApi.getCrypto()
            data.add(CryptoAddModel("", "Bitcoin", false))
            for (coin in infoCrypto) {
                data.add(CryptoAddModel("", coin.name, false))
            }
        }
        data.add(CryptoAddModel("", "Bitcoin", false))
        data.add(CryptoAddModel("", "Bitcoin", false))


        val adapter = CryptoAddAdapter(data)

        recyclerView = view.findViewById(R.id.rv_crypto_add)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun testAddCrypto(): List<CryptoAddModel> {
        val items = mutableListOf<CryptoAddModel>()
        items.add(CryptoAddModel("", "Bitcoin", false))
        return items
    }
/*
    private fun getDataCrypto(): List<CryptoAddModel>{
        val data = arrayListOf<CryptoAddModel>()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val cryptoApi = retrofit.create(CryptoApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            data.add(CryptoAddModel("", "Bitcoin", false))
            val infoCrypto = cryptoApi.getCrypto()
            data.add(CryptoAddModel("", "Bitcoin", false))
            infoCrypto.forEach {
                data.add(CryptoAddModel("", it.name, false))
                data.add(CryptoAddModel("", "Bitcoin", false))
            }
        }
        data.add(CryptoAddModel("", "Bitcoin", false))
        data.add(CryptoAddModel("", "Bitcoin", false))
        return data
    } */
}
