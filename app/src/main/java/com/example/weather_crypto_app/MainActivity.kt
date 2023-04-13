package com.example.weather_crypto_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.AppBarConfiguration

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val menu: Menu = toolbar.menu
        val searchItem: MenuItem = menu.findItem(R.id.search_info)
        val editItem: MenuItem = menu.findItem(R.id.edit_button)


        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.mainHostNavActivity
        ) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.mainMenu -> {
                    editItem.title = "Править"
                    searchItem.setVisible(false)
                    editItem.setVisible(true)
                    editItem.setOnMenuItemClickListener {
                        navController.navigate(R.id.editMenu)
                        return@setOnMenuItemClickListener true
                    }
                }
                R.id.city_Map, R.id.city_Weather, R.id.crypto_Add -> {
                    searchItem.setVisible(true)
                    editItem.setVisible(false)
                }
                R.id.editMenu -> {
                    editItem.title = "Готово"
                    editItem.setVisible(true)
                    searchItem.setVisible(false)
                    editItem.setOnMenuItemClickListener {
                        navController.navigate(R.id.mainMenu)
                        return@setOnMenuItemClickListener true
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}