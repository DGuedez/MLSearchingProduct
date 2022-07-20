package com.mlcandidate.davidguedez

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigationController()
        //goToSearchProduct()
    }

    private fun setUpNavigationController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun goToSearchProduct() {
        navController.navigate(R.id.action_mainFragment_to_foundProductsResult)
    }

    override fun onSupportNavigateUp(): Boolean {

        val navigate = navController.navigateUp()
        if (!navigate) {
            finish()
        }
        return navigate

    }

}