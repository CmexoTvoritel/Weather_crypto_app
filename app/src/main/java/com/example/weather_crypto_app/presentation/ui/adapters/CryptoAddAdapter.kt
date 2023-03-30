package com.example.weather_crypto_app.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.models.CryptoAddModel
import com.example.weather_crypto_app.presentation.ui.viewholders.CryptoAddViewHolder
import kotlinx.android.synthetic.main.crypto_add_item_layout.view.*

class CryptoAddAdapter(private val cryptoList: List<CryptoAddModel>): RecyclerView.Adapter<CryptoAddViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoAddViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_add_item_layout, parent, false)
        return CryptoAddViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoAddViewHolder, position: Int) {
        val item = cryptoList[position]
        holder.itemView.name_Coin.text = cryptoList[position].nameCoin
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}