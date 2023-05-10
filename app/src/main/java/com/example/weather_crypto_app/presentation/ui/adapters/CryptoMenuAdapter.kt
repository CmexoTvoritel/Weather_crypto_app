package com.example.weather_crypto_app.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.data.model.DbCrypto
import com.example.weather_crypto_app.databinding.CryptoMainItemLayoutBinding
import com.example.weather_crypto_app.presentation.ui.viewholders.CryptoMenuViewHolder
import com.squareup.picasso.Picasso

class CryptoMenuAdapter(private val cryptoList: List<DbCrypto>): RecyclerView.Adapter<CryptoMenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoMenuViewHolder {
        val binding = CryptoMainItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoMenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoMenuViewHolder, position: Int) {
        with(holder) {
            with(cryptoList[position]) {
                 val price = this.costCoin.toString() + " $"
                 binding.nameCoin.text = this.nameCoin
                 if(this.image != "") {
                     Picasso.get()
                         .load(this.image)
                         .placeholder(R.drawable.usd_coin_usdc_1)
                         .fit()
                         .into(binding.imageOfCoin)
                 }
                binding.totalCost.text = price
                if(this.price_change >= 0) {
                    val positivePrice = "+" + this.price_change.toString()
                    binding.changesCost.text = positivePrice
                    binding.upCost.visibility = View.VISIBLE
                    binding.downCost.visibility = View.INVISIBLE
                    binding.changesCost.setTextColor(Color.parseColor("#36DD0D"))
                }
                else {
                    val negativePrice = "-" + this.price_change.toString()
                    binding.changesCost.text = negativePrice
                    binding.upCost.visibility = View.INVISIBLE
                    binding.downCost.visibility = View.VISIBLE
                    binding.changesCost.setTextColor(Color.parseColor("#FF2727"))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}