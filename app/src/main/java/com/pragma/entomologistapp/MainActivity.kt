package com.pragma.entomologistapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.pragma.entomologistapp.domain.usecases.entomologist.GetFirstTimeEntomologistPreferencesUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject lateinit var getFirstTimeEntomologistPreferencesUseCase: GetFirstTimeEntomologistPreferencesUseCase

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        //START SPLASH SCREEN
        val screenSplash = installSplashScreen()
        screenSplash.setKeepOnScreenCondition{ false }

        super.onCreate(savedInstanceState)

        //SE RECUPERA EL NAVCONTROLLER
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //SE CONFIGURA EL TOOLBAR DE NAVEGACIÓN CON NAVCONTROLLER
        appBarConfiguration = AppBarConfiguration( navController.graph )
        setupActionBarWithNavController( navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}