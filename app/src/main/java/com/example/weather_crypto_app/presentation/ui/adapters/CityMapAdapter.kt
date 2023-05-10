package com.example.weather_crypto_app.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.databinding.CityMapItemLayoutBinding
import com.example.weather_crypto_app.presentation.model.CityMapModel
import com.example.weather_crypto_app.presentation.ui.viewholders.CityMapViewHolder
import java.util.*

@Suppress("UNCHECKED_CAST")
class CityMapAdapter(private val cityList: List<CityMapModel>): RecyclerView.Adapter<CityMapViewHolder>(), Filterable {

    var clickCallback: ((type: CityMapModel) -> Unit)? = null
    private var dataFiltered: List<CityMapModel> = cityList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityMapViewHolder {
        val binding = CityMapItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityMapViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityMapViewHolder, position: Int) {

        with(holder) {
            with(dataFiltered[position]) {
                binding.cityShortName.text = this.shortName
                binding.cityFullName.text = this.fullNameCity
            }
        }
        holder.clickCallback = clickCallback
        holder.bind(dataFiltered[position])
    }

    override fun getItemCount(): Int {
        return dataFiltered.size
    }


    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint.toString().lowercase(Locale.getDefault())
                dataFiltered = if(query.isEmpty()) {
                    cityList
                } else {
                    cityList.filter { it.fullNameCity.lowercase(Locale.getDefault()).contains(query)}
                }
                val filterResults = FilterResults()
                filterResults.values = dataFiltered
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFiltered = results?.values as List<CityMapModel>
                notifyDataSetChanged()
            }
        }
    }

}