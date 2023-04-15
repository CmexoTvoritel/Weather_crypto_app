package com.example.weather_crypto_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.data.CryptoApi
import com.example.weather_crypto_app.data.db.dbCrypto.CryptoViewModel
import com.example.weather_crypto_app.data.db.dbCrypto.DbCrypto
import com.example.weather_crypto_app.models.CryptoAddModel
import com.example.weather_crypto_app.presentation.ui.adapters.CryptoAddAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Crypto_Add : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var cryptoViewModel: CryptoViewModel
    private lateinit var adapter: CryptoAddAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_crypto__add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        searchView = (activity as AppCompatActivity).toolbar.menu.findItem(R.id.search_info).actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        val dbCoinsList = arrayListOf<DbCrypto>()
        cryptoViewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
        cryptoViewModel.readAllData.observe(viewLifecycleOwner, Observer { coin ->
            coin.forEach { dbCoinsList.add(it) }
        })
        showRV(dbCoinsList, view)
    }

    private fun deleteDataOfCoinDatabase(data: CryptoAddModel) {
        val name = data.nameCoin
        val image = data.image
        val cost = 0.00
        val coinData = DbCrypto(data.uid, name, image, cost)
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
        var check: Boolean
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val cryptoApi = retrofit.create(CryptoApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val infoCrypto = cryptoApi.getCrypto()
            withContext(Dispatchers.Main) {
                dbCoins.forEach { data.add(CryptoAddModel(it.uid, it.image, it.nameCoin, true))
                viewDataCrypto.add(it.nameCoin)}
                for (coin in infoCrypto) {
                    check = true
                    for(dbCoin in dbCoins) {
                        if(coin.name == dbCoin.nameCoin) check = false
                    }
                    if(check) data.add(CryptoAddModel(0, coin.image, coin.name, false))
                }
                adapter = CryptoAddAdapter(data)
                recyclerView = view.findViewById(R.id.rv_crypto_add)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
                adapter.clickCallback = {type ->
                    if(type.enableCoin) {
                        type.uid = viewDataCrypto.size + 1
                        viewDataCrypto.add(type.nameCoin)
                        insertDataToCoinDatabase(type)
                        type.enableCoin = true
                        Toast.makeText(context, "${type.enableCoin}", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        viewDataCrypto.remove(type.nameCoin)
                        deleteDataOfCoinDatabase(type)
                        type.enableCoin = false
                        Toast.makeText(context, "${type.enableCoin}", Toast.LENGTH_SHORT).show()
                    }
                    if(viewDataCrypto.size >= 3) {
                        val warningText = Toast.makeText(context, "Вы можете выбрать не более 3 криптовалют", Toast.LENGTH_LONG)
                        warningText.show()
                    }
                }
            }
        }
    }
}
