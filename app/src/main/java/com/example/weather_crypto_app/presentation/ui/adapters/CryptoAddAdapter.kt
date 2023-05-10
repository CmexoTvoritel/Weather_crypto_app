package com.example.weather_crypto_app.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.databinding.CryptoAddItemLayoutBinding
import com.example.weather_crypto_app.presentation.model.CryptoAddModel
import com.example.weather_crypto_app.presentation.ui.viewholders.CryptoAddViewHolder
import com.squareup.picasso.Picasso
import java.util.*

@Suppress("UNCHECKED_CAST")
class CryptoAddAdapter(private val cryptoList: List<CryptoAddModel>): RecyclerView.Adapter<CryptoAddViewHolder>(), Filterable {

    var clickCallback: ((type: CryptoAddModel) -> Unit)?= null
    private var dataFiltered: List<CryptoAddModel> = cryptoList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoAddViewHolder {
        val binding = CryptoAddItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoAddViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoAddViewHolder, position: Int) {

        with(holder) {
            with(dataFiltered[position]) {
                binding.nameCoin.text = this.nameCoin
                Picasso.get()
                    .load(this.image)
                    .placeholder(R.drawable.usd_coin_usdc_1)
                    .fit()
                    .into(binding.imageCoin)
                binding.checkEnableCoin.isChecked = this.enableCoin
            }
        }
        holder.clickCallback = clickCallback
        holder.bind(dataFiltered[position])
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