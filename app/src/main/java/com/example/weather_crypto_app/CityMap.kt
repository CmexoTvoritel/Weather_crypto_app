package com.example.weather_crypto_app

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.data.db.dbMap.DbMap
import com.example.weather_crypto_app.data.db.dbMap.MapViewModel
import com.example.weather_crypto_app.data.names.city.CityNamesMap
import com.example.weather_crypto_app.databinding.FragmentCityMapBinding
import com.example.weather_crypto_app.models.CityMapModel
import com.example.weather_crypto_app.presentation.ui.adapters.CityMapAdapter

class CityMap : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mapViewModel: MapViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView
    private lateinit var adapter: CityMapAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city__map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)

        recyclerView = view.findViewById(R.id.rv_city_map)

        searchView = toolbar.menu.findItem(R.id.search_info).actionView as SearchView
        addSearchQuery()
        createRV()
    }

    private fun addSearchQuery() {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })
    }

    private fun addMapItems(): List<CityMapModel> {
        val items = mutableListOf<CityMapModel>()
        val cityNamesMap = CityNamesMap()
        cityNamesMap.cityNames.forEach {
            items.add(it)
        }
        return items
    }

    private fun createRV() {
        adapter = CityMapAdapter(addMapItems())
        adapter.clickCallback = { type->
            mapViewModel.readAllData.observe(viewLifecycleOwner, Observer { it  ->
                if(it.isNotEmpty()) mapViewModel.updateMap(DbMap(1, type.nameApiCity, type.fullNameCity, type.pointCity.lan, type.pointCity.lon))
                else mapViewModel.addCity(DbMap(0, type.nameApiCity, type.fullNameCity, type.pointCity.lan, type.pointCity.lon))
                findNavController().navigate(R.id.mainMenu)
            })
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}