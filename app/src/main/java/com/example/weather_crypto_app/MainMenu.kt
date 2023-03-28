package com.example.weather_crypto_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_crypto_app.presentation.ui.adapters.MainMenuAdapter
import com.example.weather_crypto_app.models.MainMenuModel
import com.example.weather_crypto_app.models.MainMenuModules


class MainMenu : Fragment(), MainMenuAdapter.Listener {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MainMenuAdapter(addMenuItems())
        adapter.clickCallback = { type ->
            when (type) {
                MainMenuModules.MAP -> findNavController().navigate(R.id.city_Map) //TODO
                MainMenuModules.WEATHER -> findNavController().navigate(R.id.city_Weather)
                MainMenuModules.COINS -> findNavController().navigate(R.id.crypto_Add)
            }
        }
        recyclerView = view.findViewById(R.id.rv_main_menu)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun addMenuItems(): List<MainMenuModel> {
        val items = mutableListOf<MainMenuModel>()
        items.add(MainMenuModel("Карта", "Выбрать", type = MainMenuModules.MAP))
        items.add(MainMenuModel("Погода", "Выбрать", type = MainMenuModules.WEATHER))
        items.add(MainMenuModel("Курс криптовалют", "Выбрать", type = MainMenuModules.COINS))
        return items
    }

    companion object {
        fun newInstance() = MainMenu()
    }

    override fun onCLick(mainMenuModel: MainMenuModel) {

    }

}