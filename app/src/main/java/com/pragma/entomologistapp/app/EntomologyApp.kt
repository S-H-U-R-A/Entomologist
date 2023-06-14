package com.pragma.entomologistapp.app

import android.app.Application
import androidx.lifecycle.lifecycleScope
import com.pragma.entomologistapp.R
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch

@HiltAndroidApp
class EntomologyApp : Application() {}