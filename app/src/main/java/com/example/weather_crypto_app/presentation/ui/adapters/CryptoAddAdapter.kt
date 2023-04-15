package com.example.weather_crypto_app.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.models.CryptoAddModel
import com.example.weather_crypto_app.presentation.ui.viewholders.CryptoAddViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.crypto_add_item_layout.view.*
import java.util.*

class CryptoAddAdapter(private val cryptoList: List<CryptoAddModel>): RecyclerView.Adapter<CryptoAddViewHolder>(), Filterable {

    var clickCallback: ((type: CryptoAddModel) -> Unit)?= null
    private var dataFiltered: List<CryptoAddModel> = cryptoList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoAddViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_add_item_layout, parent, false)
        return CryptoAddViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoAddViewHolder, position: Int) {
        val item = dataFiltered[position]
        holder.itemView.name_Coin.text = dataFiltered[position].nameCoin
        Picasso.get()
            .load(dataFiltered[position].image)
            .placeholder(R.drawable.usd_coin_usdc_1)
            .fit()
            .into(holder.itemView.image_Coin)
        holder.itemView.check_Enable_Coin.isChecked = dataFiltered[position].enableCoin
        holder.clickCallback = clickCallback
        holder.bind(item)
    }

    override fun onViewRecycled(holder: CryptoAddViewHolder) {
        holder.unbind()
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int {
        return dataFiltered.size
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint.toString().lowercase(Locale.getDefault())
                dataFiltered = if(query.isEmpty()) {
                    cryptoList
                }else {
                    cryptoList.filter { it.nameCoin.lowercase(Locale.getDefault()).contains(query) }
                }
                val filterResults = FilterResults()
                filterResults.values = dataFiltered
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFiltered = results?.values as List<CryptoAddModel>
                notifyDataSetChanged()
            }

        }
    }
}