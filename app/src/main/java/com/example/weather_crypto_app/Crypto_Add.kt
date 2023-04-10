package com.example.weather_crypto_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.data.CryptoApi
import com.example.weather_crypto_app.data.db.dbCrypto.CryptoViewModel
import com.example.weather_crypto_app.data.db.dbCrypto.DbCrypto
import com.example.weather_crypto_app.models.CryptoAddModel
import com.example.weather_crypto_app.presentation.ui.adapters.CryptoAddAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Crypto_Add : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var cryptoViewModel: CryptoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_crypto__add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dbCoinsList = arrayListOf<DbCrypto>()
        cryptoViewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
        cryptoViewModel.readAllData.observe(viewLifecycleOwner, Observer { coin ->
            coin.forEach { dbCoinsList.add(it) }
            showRV(dbCoinsList, view)
        })
    }

    private fun deleteDataOfCoinDatabase(data: CryptoAddModel) {
        val name = data.nameCoin
        val image = data.image
        val cost = 0.00
        val coinData = DbCrypto(0, name, image, cost)
        cryptoViewModel.deleteCoins(coinData)
        Toast.makeText(requireContext(), "Success delete", Toast.LENGTH_SHORT).show()
    }

    private fun insertDataToCoinDatabase(data: CryptoAddModel) {
        val name = data.nameCoin
        val cost = 0.00
        val image = data.image
        val coinData = DbCrypto(0, name, image, cost)
        cryptoViewModel.addCoins(coinData)
        Toast.makeText(requireContext(), "Success add", Toast.LENGTH_SHORT).show()
    }

    private fun showRV(dbCoins: List<DbCrypto>, view: View) {
        val data = arrayListOf<CryptoAddModel>()
        val viewDataCrypto = arrayListOf<String>()
        var check = true
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val cryptoApi = retrofit.create(CryptoApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val infoCrypto = cryptoApi.getCrypto()
            withContext(Dispatchers.Main) {
                dbCoins.forEach { data.add(CryptoAddModel(it.image, it.nameCoin, true)) }
                for (coin in infoCrypto) {
                    for(dbCoin in dbCoins) {
                        if(coin.name == dbCoin.nameCoin) check = false
                    }
                    if(check) data.add(CryptoAddModel(coin.image, coin.name, false))
                    check = true
                }
                val adapter = CryptoAddAdapter(data)
                recyclerView = view.findViewById(R.id.rv_crypto_add)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
                adapter.clickCallback = {type ->
                    if(type.enableCoin) {
                        viewDataCrypto.add(type.nameCoin)
                        insertDataToCoinDatabase(type)
                        val toastEnable = Toast.makeText(context, "${type.nameCoin} is enabled", Toast.LENGTH_SHORT)
                        toastEnable.show()
                    }
                    else {
                        viewDataCrypto.remove(type.nameCoin)
                        deleteDataOfCoinDatabase(type)
                        val toastDisable = Toast.makeText(context, "${type.nameCoin} is disabled", Toast.LENGTH_SHORT)
                        toastDisable.show()
                    }
                    if(viewDataCrypto.size >= 3) {
                        val warningText = Toast.makeText(context, "Вы можете выбрать не более 3 криптовалют", Toast.LENGTH_LONG)
                        warningText.show()
                    }
                }
            }
        }
    }

    private fun testAddCrypto(): List<CryptoAddModel> {
        val items = mutableListOf<CryptoAddModel>()
        items.add(CryptoAddModel("", "Bitcoin", false))
        return items
    }
}
