package com.pragma.entomologistapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.pragma.entomologistapp.domain.usecases.GetEntomologistPreferencesUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject lateinit var entomologistPreferencesUseCase: GetEntomologistPreferencesUseCase

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        //START SPLASH SCREEN
        val screenSplash = installSplashScreen()
        screenSplash.setKeepOnScreenCondition{ false }

        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration( navController.graph )
        setupActionBarWithNavController( navController, appBarConfiguration)


        canNavigateNewRegister()

    }

    private fun canNavigateNewRegister() {

        //SE CONFIGURA NUEVO START DESTINATION

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED){

                entomologistPreferencesUseCase().collect{ firstTime: Boolean ->

                    val graph = navController.navInflater.inflate(R.navigation.nav_graph)

                    if (firstTime) {

                        graph.setStartDestination(R.id.recordFragment )

                    } else {

                        graph.setStartDestination(R.id.signUpFragment )

                    }

                    navController.graph = graph

                }

            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}