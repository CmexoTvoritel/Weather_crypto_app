package com.example.weather_crypto_app.presentation.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.databinding.CryptoAddItemLayoutBinding
import com.example.weather_crypto_app.presentation.model.CryptoAddModel

class CryptoAddViewHolder(val binding: CryptoAddItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    var clickCallback: ((type: CryptoAddModel) -> Unit)?= null

    fun bind(item: CryptoAddModel) {
        binding.checkEnableCoin.setOnCheckedChangeListener { _, isChecked ->
            item.enableCoin = isChecked
            clickCallback?.invoke(item)
        }
    }

    fun unbind() {
        binding.checkEnableCoin.setOnCheckedChangeListener(null)
    }
}