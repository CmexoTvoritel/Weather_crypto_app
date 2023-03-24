package com.example.weather_crypto_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_main_menu.*


class MainMenu : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weather_choose.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainMenu_to_city_Weather2)
        }

        map_choose.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainMenu_to_city_Map)
        }

        crypto_choose.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainMenu_to_crypto_Add)
        }
    }

}