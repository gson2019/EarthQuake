package com.example.bubble.earthquake.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.example.bubble.earthquake.R
import kotlinx.android.synthetic.main.fragment_earthquake_list.*

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = Navigation.findNavController(
            this,
            R.id.nav_host_fragment
        )
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when(destination.id) {
//            R.id.earthquakeListFragment -> {
//                supportActionBar?.setDisplayHomeAsUpEnabled(false)
//            }
//            R.id.earthquakeMapFragment -> {
//                supportActionBar?.setDisplayHomeAsUpEnabled(true)
//                supportActionBar?.setDisplayShowHomeEnabled(true)
//            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            Navigation.findNavController(
                this,
                R.id.nav_host_fragment
            ).navigateUp()
        }
        return true
    }
}

