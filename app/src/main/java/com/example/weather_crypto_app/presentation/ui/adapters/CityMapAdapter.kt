package com.example.weather_crypto_app.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.R
import com.example.weather_crypto_app.models.CityMapModel
import com.example.weather_crypto_app.presentation.ui.viewholders.CityMapViewHolder
import kotlinx.android.synthetic.main.city_map_item_layout.view.*

class CityMapAdapter(private val cityList: List<CityMapModel>): RecyclerView.Adapter<CityMapViewHolder>(), Filterable {

    var clickCallback: ((type: CityMapModel) -> Unit)? = null
    private val dataFiltered = ArrayList<CityMapModel>()
    private val searchFilter = SearchFilter()

    init {
        dataFiltered.addAll(cityList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityMapViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_map_item_layout, parent, false)
        return CityMapViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityMapViewHolder, position: Int) {
        val item = cityList[position]
        holder.itemView.city_short_name.text = cityList[position].shortName
        holder.itemView.city_full_name.text = cityList[position].fullNameCity
        holder.clickCallback = clickCallback
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    private inner class SearchFilter: Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredCity = ArrayList<CityMapModel>()
            if(constraint == null || constraint.isEmpty()) filteredCity.addAll(cityList)
            else {
                val filterPattern = constraint.toString().trim()
                for(item in cityList) {
                    if(item.fullNameCity.contains(filterPattern)) filteredCity.add(item)
                }
            }
            val results = FilterResults()
            results.values = filteredCity
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            dataFiltered.clear()
            dataFiltered.addAll(results?.values as ArrayList<CityMapModel>)
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

}