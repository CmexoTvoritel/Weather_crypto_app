package com.example.weather_crypto_app.presentation.ui.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.utils.CryptoDbUtils
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.utils.RequestsToApiUtils
import com.example.weather_crypto_app.network.services.CryptoService
import com.example.weather_crypto_app.presentation.viewmodel.CryptoViewModel
import com.example.weather_crypto_app.data.model.DbCrypto
import com.example.weather_crypto_app.presentation.model.CryptoAddModel
import com.example.weather_crypto_app.network.model.crypto.CryptoRepItem
import com.example.weather_crypto_app.presentation.ui.adapters.CryptoAddAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.floor


class CryptoAddFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cryptoViewModel: CryptoViewModel
    private lateinit var adapter: CryptoAddAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView
    private lateinit var cryptoDbUtils: CryptoDbUtils
    private lateinit var requestsToApiUtils: RequestsToApiUtils
    private lateinit var errorMessageTextView: TextView
    private lateinit var errorButton: Button
    private lateinit var loadingCoins: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_crypto__add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingCoins = view.findViewById(R.id.load_coins)
        loadingCoins.visibility = View.VISIBLE
        val anim = loadingCoins.drawable as AnimatedVectorDrawable
        anim.start()

        errorMessageTextView = view.findViewById(R.id.error_message)
        errorButton = view.findViewById(R.id.reload_button)
        errorButton.visibility = View.INVISIBLE
        errorMessageTextView.visibility = View.INVISIBLE


        requestsToApiUtils = RequestsToApiUtils()
        toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        searchView = toolbar.menu.findItem(R.id.search_info).actionView as SearchView
        addSearchQuery()

        val dbCoinsList = arrayListOf<DbCrypto>()
        cryptoViewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
        cryptoDbUtils = CryptoDbUtils(cryptoViewModel)
        cryptoViewModel.readAllData.observe(viewLifecycleOwner) { coin ->
            coin.forEach { dbCoinsList.add(it) }
        }
        showRV(dbCoinsList, view)
    }

    private fun addSearchQuery() {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun createRV(data: MutableList<CryptoAddModel>, viewDataCrypto: ArrayList<CryptoAddModel>, infoCrypto: List<CryptoRepItem>, view: View) {
        var check: Boolean
        adapter = CryptoAddAdapter(data)
        recyclerView = view.findViewById(R.id.rv_crypto_add)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.clickCallback = {type ->
            if(type.enableCoin) {
                type.uid = viewDataCrypto.size + 1
                type.enableCoin = true
                viewDataCrypto.add(type)
                cryptoDbUtils.publicInsertData(type)
                if(viewDataCrypto.size > 3) {
                    cryptoDbUtils.publicDeleteData(viewDataCrypto[0])
                    viewDataCrypto.removeAt(0)
                }
                data.clear()
                viewDataCrypto.forEach { data.add(it) }
                for(coin in infoCrypto) {
                    check = true
                    for(dbCoin in viewDataCrypto) {
                        if(coin.name == dbCoin.nameCoin) check = false
                    }
                    if(check) data.add(CryptoAddModel(0, coin.image, coin.name, floor(coin.current_price * 100)/100, floor(coin.price_change_24h * 100)/100, false))
                }
            }
            else {
                viewDataCrypto.remove(type)
                cryptoDbUtils.publicDeleteData(type)
                type.enableCoin = false
                data.clear()
                viewDataCrypto.forEach { data.add(it) }
                for(coin in infoCrypto) {
                    check = true
                    for(dbCoin in viewDataCrypto) {
                        if(coin.name == dbCoin.nameCoin) check = false
                    }
                    if(check) data.add(CryptoAddModel(0, coin.image, coin.name, floor(coin.current_price * 100)/100, floor(coin.price_change_24h * 100)/100, false))
                }
            }
            if(viewDataCrypto.size >= 3)
                Toast.makeText(context, getString(R.string.warning_message_coins), Toast.LENGTH_LONG).show()
            adapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showRV(dbCoins: List<DbCrypto>, view: View) {
        val data = mutableListOf<CryptoAddModel>()
        val viewDataCrypto = arrayListOf<CryptoAddModel>()
        var check: Boolean
        val cryptoService: CryptoService = requestsToApiUtils.publicGenerateRequest("Coin") as CryptoService
        CoroutineScope(Dispatchers.IO).launch {
            var infoCrypto: List<CryptoRepItem>
            try {
                infoCrypto = cryptoService.getCrypto()
                check = true
            }catch (_: Exception) {
                infoCrypto = emptyList()
                check = false
                withContext(Dispatchers.Main) {
                    loadingCoins.visibility = View.GONE
                    errorMessageTextView.visibility = View.VISIBLE
                    errorButton.visibility = View.VISIBLE
                    errorButton.setOnClickListener {
                        errorMessageTextView.visibility = View.INVISIBLE
                        errorButton.visibility = View.INVISIBLE
                        loadingCoins.visibility = View.VISIBLE
                        showRV(dbCoins, view)
                    }
                }

            }
            if(check) {
                withContext(Dispatchers.Main) {
                    dbCoins.forEach {
                        data.add(
                            CryptoAddModel(
                                it.uid,
                                it.image,
                                it.nameCoin,
                                it.costCoin,
                                it.price_change,
                                true
                            )
                        )
                        viewDataCrypto.add(
                            CryptoAddModel(
                                it.uid,
                                it.image,
                                it.nameCoin,
                                it.costCoin,
                                it.price_change,
                                true
                            )
                        )
                    }
                    for (coin in infoCrypto) {
                        check = true
                        for (dbCoin in dbCoins) {
                            if (coin.name == dbCoin.nameCoin) check = false
                        }
                        if (check) data.add(
                            CryptoAddModel(
                                0,
                                coin.image,
                                coin.name,
                                floor(coin.current_price * 100) / 100,
                                floor(coin.price_change_24h * 100) / 100,
                                false
                            )
                        )
                    }
                    createRV(data, viewDataCrypto, infoCrypto, view)
                    loadingCoins.visibility = View.GONE
                }
            }
        }
    }
}
