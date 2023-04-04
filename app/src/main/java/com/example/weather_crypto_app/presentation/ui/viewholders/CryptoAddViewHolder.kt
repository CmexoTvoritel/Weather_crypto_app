package com.example.weather_crypto_app.presentation.ui.viewholders

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.models.CryptoAddModel
import kotlinx.android.synthetic.main.crypto_add_item_layout.view.*

class CryptoAddViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var clickCallback: ((type: CryptoAddModel) -> Unit)?= null

    fun bind(item: CryptoAddModel) {
        itemView.check_Enable_Coin.setOnCheckedChangeListener { buttonView, isChecked ->
            item.enableCoin = isChecked
            clickCallback?.invoke(item)
        }
    }
}