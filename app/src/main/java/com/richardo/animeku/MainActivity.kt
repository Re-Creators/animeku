package com.richardo.animeku

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // Defining toolbar and create it as action bar
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)


        navController = findNavController(R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        Log.d("MainActivity", "onCreate Called")

        // Hiding toolbar when destination on homeFragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.listAllFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottom_nav.visibility = View.GONE

                }
                R.id.detailFragment -> {
                    toolbar.visibility = View.GONE
                    bottom_nav.visibility = View.GONE
                }
                R.id.voiceActorFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottom_nav.visibility = View.GONE
                }
                R.id.searchFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottom_nav.visibility = View.GONE
                }
                 else -> {
                     toolbar.visibility = View.GONE
                     bottom_nav.visibility = View.VISIBLE
                 }
            }
        }

    }

    // Enable Navigate up button in action bar
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.option_menu, menu)
//
//        val searchItem  = menu?.findItem(R.id.search)
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView : SearchView = searchItem?.actionView as SearchView
//
////        searchView.setIconifiedByDefault(false)
////        searchItem.expandActionView()
////        searchView.requestFocus()
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//
//        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                Log.d("MainActivity", "onQueryTextSubmit: $query")
//                return false
//            }
//        })

//        return true
//    }


}