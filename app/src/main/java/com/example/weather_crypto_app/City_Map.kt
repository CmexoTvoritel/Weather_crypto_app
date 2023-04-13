package com.example.weather_crypto_app

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.data.db.dbMap.DbMap
import com.example.weather_crypto_app.data.db.dbMap.MapViewModel
import com.example.weather_crypto_app.data.names.city.CityNamesMap
import com.example.weather_crypto_app.models.CityMapModel
import com.example.weather_crypto_app.presentation.ui.adapters.CityMapAdapter

class City_Map : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var mapViewModel: MapViewModel
    lateinit var adapter: CityMapAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city__map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        var nameCity: String

        adapter = CityMapAdapter(addMapItems())

        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        adapter.clickCallback = { type->
            mapViewModel.readAllData.observe(viewLifecycleOwner, Observer { it  ->
                if(it.isNotEmpty()) mapViewModel.updateMap(DbMap(1, type.nameApiCity))
                else mapViewModel.addCity(DbMap(0, type.nameApiCity))
                bundle.putString("CityMap", type.nameApiCity)
                findNavController().navigate(R.id.mainMenu, bundle)
            })
        }
        recyclerView = view.findViewById(R.id.rv_city_map)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu)
        val searchItem = menu.findItem(R.id.search_info)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun addMapItems(): List<CityMapModel> {
        val items = mutableListOf<CityMapModel>()
        val cityNamesMap: CityNamesMap = CityNamesMap()
        cityNamesMap.cityNames.forEach {
            items.add(it)
        }
        return items
    }
}