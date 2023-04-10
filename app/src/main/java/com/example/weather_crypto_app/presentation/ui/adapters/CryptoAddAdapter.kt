package com.example.weather_crypto_app.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.models.CryptoAddModel
import com.example.weather_crypto_app.presentation.ui.viewholders.CryptoAddViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.crypto_add_item_layout.view.*

class CryptoAddAdapter(private val cryptoList: List<CryptoAddModel>): RecyclerView.Adapter<CryptoAddViewHolder>() {

    var clickCallback: ((type: CryptoAddModel) -> Unit)?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoAddViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_add_item_layout, parent, false)
        return CryptoAddViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoAddViewHolder, position: Int) {
        val item = cryptoList[position]
        holder.itemView.name_Coin.text = cryptoList[position].nameCoin
        Picasso.get()
            .load(cryptoList[position].image)
            .placeholder(R.drawable.usd_coin_usdc_1)
            .fit()
            .into(holder.itemView.image_Coin)
        holder.itemView.check_Enable_Coin.isChecked = cryptoList[position].enableCoin
        holder.clickCallback = clickCallback
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}