package com.example.weather_crypto_app.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.data.db.dbCrypto.DbCrypto
import com.example.weather_crypto_app.presentation.ui.viewholders.CryptoMenuViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.crypto_main_item_layout.view.*

class CryptoMenuAdapter(private val cryptoList: List<DbCrypto>): RecyclerView.Adapter<CryptoMenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoMenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_main_item_layout, parent, false)
        return CryptoMenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoMenuViewHolder, position: Int) {
        val item = cryptoList[position]
        holder.itemView.name_coin.text = cryptoList[position].nameCoin
        Picasso.get()
            .load(cryptoList[position].image)
            .placeholder(R.drawable.usd_coin_usdc_1)
            .fit()
            .into(holder.itemView.image_of_coin)
        holder.itemView.image_of_coin
        holder.itemView.total_cost.text = cryptoList[position].costCoin.toString() + " $"
        holder.itemView.up_cost.visibility = View.INVISIBLE
        holder.itemView.down_cost.visibility = View.INVISIBLE
        if(cryptoList[position].price_change > 0) {
            holder.itemView.changes_cost.text = "+" + cryptoList[position].price_change.toString()
            holder.itemView.up_cost.visibility = View.VISIBLE
            holder.itemView.changes_cost.setTextColor(Color.parseColor("#36DD0D"))
        }
        else {
            holder.itemView.changes_cost.text = cryptoList[position].price_change.toString()
            holder.itemView.down_cost.visibility = View.VISIBLE
            holder.itemView.changes_cost.setTextColor(Color.parseColor("#FF2727"))
        }
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}