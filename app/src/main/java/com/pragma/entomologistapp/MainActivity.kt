package com.pragma.entomologistapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.pragma.entomologistapp.databinding.ActivityMainBinding
import com.pragma.entomologistapp.util.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var loadingDialog: LoadingDialog? = null

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        //START SPLASH SCREEN
        val screenSplash = installSplashScreen()
        screenSplash.setKeepOnScreenCondition{ false }
        //CALL SUPER
        super.onCreate(savedInstanceState)
        //GET LAYOUT
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //SET TOOLBAR
        setSupportActionBar( binding.toolbar )
        //GET NAV-CONTROLLER
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        //SE CONFIGURA EL TOOLBAR DE NAVEGACIÓN CON NAVCONTROLLER
        appBarConfiguration = AppBarConfiguration( navController.graph )
        setupActionBarWithNavController( navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun showOrHideDialogLoading(show: Boolean) = if (show) showDialogLoading() else hideDialogLoading()

    private fun showDialogLoading(){
        if(loadingDialog == null) {
            loadingDialog = LoadingDialog().also {
                it.show(supportFragmentManager, "loading")
            }
        }else {
            loadingDialog?.let {
                it.dismiss()
                it.show(supportFragmentManager, "loading")
            }
        }
    }

    private fun hideDialogLoading(){
        loadingDialog?.dismiss()
    }


}