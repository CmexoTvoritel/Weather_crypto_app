package com.example.weather_crypto_app.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.models.CityWeatherModel
import com.example.weather_crypto_app.presentation.ui.viewholders.CityWeatherViewHolder
import kotlinx.android.synthetic.main.city_weather_item_layout.view.*
import kotlinx.coroutines.NonDisposableHandle.parent
import java.util.*

class CityWeatherAdapter(private val cityList: List<CityWeatherModel>): RecyclerView.Adapter<CityWeatherViewHolder>(), Filterable {
    var clickCallback: ((type: CityWeatherModel) -> Unit)? = null
    private var dataFiltered: List<CityWeatherModel> = cityList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_weather_item_layout, parent, false)
        return CityWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityWeatherViewHolder, position: Int) {
        val item = dataFiltered[position]
        holder.itemView.city_short_name.text = dataFiltered[position].shortName
        holder.itemView.city_full_name.text = dataFiltered[position].fullNameCity
        holder.clickCallback = clickCallback
        holder.bind(item)
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
                }else {
                    cityList.filter { it.fullNameCity.lowercase(Locale.getDefault()).contains(query) }
                }
                val filterResults = FilterResults()
                filterResults.values = dataFiltered
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFiltered = results?.values as List<CityWeatherModel>
                notifyDataSetChanged()
            }

        }
    }
}