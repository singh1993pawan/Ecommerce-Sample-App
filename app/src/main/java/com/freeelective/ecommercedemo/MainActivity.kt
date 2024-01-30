package com.freeelective.ecommercedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.freeelective.ecommercedemo.databinding.ActivityMainBinding
import com.freeelective.ecommercedemo.helper.UpdateCartValueCallback

class MainActivity : AppCompatActivity(),UpdateCartValueCallback {

    private lateinit var badgeTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar:Toolbar=binding.toolbar
        setSupportActionBar(toolbar)
//        val menu = toolbar.menu
//        val cartItem = menu.findItem(R.id.action_cart)
//        val badge = cartItem?.actionView?.findViewById<TextView>(R.id.badge)
//        badge?.text="10"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        // Get the MenuItem for the custom layout
        val menuItem = menu.findItem(R.id.action_cart)

        // Access the action view for the MenuItem
        val actionView = menuItem.actionView

        // Find the TextView representing the badge
        badgeTextView = actionView?.findViewById(R.id.badge)!!

        return true
    }

    override fun updateCart() {
        badgeTextView?.text = "${badgeTextView.text.toString().toInt()+1}"
    }

}