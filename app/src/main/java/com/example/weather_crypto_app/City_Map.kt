package com.example.weather_crypto_app

import android.os.Bundle
import android.view.*
import android.widget.TextView
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
import com.example.weather_crypto_app.models.CityMapModel
import com.example.weather_crypto_app.presentation.ui.adapters.CityMapAdapter
import kotlinx.android.synthetic.main.activity_main.*

class City_Map : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var mapViewModel: MapViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView
    lateinit var adapter: CityMapAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city__map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()

        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)

        recyclerView = view.findViewById(R.id.rv_city_map)

        searchView = (activity as AppCompatActivity).toolbar.menu.findItem(R.id.search_info).actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })

        adapter = CityMapAdapter(addMapItems())



        adapter.clickCallback = { type->
            mapViewModel.readAllData.observe(viewLifecycleOwner, Observer { it  ->
                if(it.isNotEmpty()) mapViewModel.updateMap(DbMap(1, type.nameApiCity, type.fullNameCity, type.pointCity.lan, type.pointCity.lon))
                else mapViewModel.addCity(DbMap(0, type.nameApiCity, type.fullNameCity, type.pointCity.lan, type.pointCity.lon))
                bundle.putString("CityMap", type.nameApiCity)
                findNavController().navigate(R.id.mainMenu, bundle)
            })
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
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