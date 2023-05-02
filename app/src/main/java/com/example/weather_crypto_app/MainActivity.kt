package com.example.weather_crypto_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.appbar.MaterialToolbar
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.navigationBarColor = ColorUtils.setAlphaComponent(
            ContextCompat.getColor(this, R.color.white), 0
        )
        MapKitFactory.setApiKey("2eb5effa-b61b-4f6e-8294-8a3cbac2b5ce")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        val menu: Menu = toolbar.menu
        val searchItem: MenuItem = menu.findItem(R.id.search_info)
        val editItem: MenuItem = menu.findItem(R.id.edit_button)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.mainHostNavActivity
        ) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainMenu -> {
                    editItem.title = "Править"
                    searchItem.isVisible = false
                    editItem.isVisible = true
                    editItem.setOnMenuItemClickListener {
                        navController.navigate(R.id.editMenu)
                        return@setOnMenuItemClickListener true
                    }
                }
                R.id.city_Map, R.id.city_Weather, R.id.crypto_Add -> {
                    searchItem.isVisible = true
                    editItem.isVisible = false
                }
                R.id.editMenu -> {
                    editItem.title = "Готово"
                    editItem.isVisible = true
                    searchItem.isVisible = false
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}