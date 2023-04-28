package com.example.weather_crypto_app.presentation.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.databinding.CryptoAddItemLayoutBinding
import com.example.weather_crypto_app.models.CryptoAddModel
import kotlinx.android.synthetic.main.crypto_add_item_layout.view.*

class CryptoAddViewHolder(val binding: CryptoAddItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    var clickCallback: ((type: CryptoAddModel) -> Unit)?= null

    fun bind(item: CryptoAddModel) {
        itemView.check_Enable_Coin.setOnCheckedChangeListener { buttonView, isChecked ->
            item.enableCoin = isChecked
            clickCallback?.invoke(item)
        }
    }

    fun unbind() {
        itemView.check_Enable_Coin.setOnCheckedChangeListener(null)
    }
}