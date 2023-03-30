package com.example.weather_crypto_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.data.ManagerCryptoApi
import com.example.weather_crypto_app.models.CryptoAddModel
import com.example.weather_crypto_app.models.MainMenuModel
import com.example.weather_crypto_app.models.MainMenuModules
import com.example.weather_crypto_app.presentation.ui.adapters.CryptoAddAdapter


class Crypto_Add : Fragment() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_crypto__add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val managerCryptoApi: ManagerCryptoApi = ManagerCryptoApi()

        //val adapter = CryptoAddAdapter(managerCryptoApi.GetDataCrypto())

        val adapter = CryptoAddAdapter(testAddCrypto())

        recyclerView = view.findViewById(R.id.rv_crypto_add)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun testAddCrypto(): List<CryptoAddModel> {
        val items = mutableListOf<CryptoAddModel>()
        items.add(CryptoAddModel("", "Bitcoin", false))
        return items
    }
}