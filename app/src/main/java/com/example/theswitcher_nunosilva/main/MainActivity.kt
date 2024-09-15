package com.example.theswitcher_nunosilva.main

import android.content.Context
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.theswitcher_nunosilva.biometric.CheckIfBiometricIsAvailable
import com.example.theswitcher_nunosilva.core.Green
import com.example.theswitcher_nunosilva.core.TheSwitcher_NunoSilvaTheme
import com.example.theswitcher_nunosilva.model.DivisionDatabase
import com.example.theswitcher_nunosilva.model.prepopulateDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

var keepSplashOpened = true

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            keepSplashOpened
        }
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Green.hashCode(), Green.hashCode())
        )
        checkFirstTimeLaunch()
        val mainViewModel: MainViewModel by viewModels()
        setContent {
            TheSwitcher_NunoSilvaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        CheckIfBiometricIsAvailable(mainViewModel)
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mainViewModel.shouldClose.collect { shouldClose ->
                    if (shouldClose) {
                        finish()
                    }
                }
            }
        }
    }

    private fun checkFirstTimeLaunch() {
        val sharedPrefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPrefs.getBoolean("first_launch", true)
        if (isFirstLaunch) {
            roomCallback()
            sharedPrefs.edit().putBoolean("first_launch", false).apply()
        }
    }

    private fun roomCallback() {
        CoroutineScope(Dispatchers.IO).launch {
            prepopulateDatabase(DivisionDatabase.getDatabase(applicationContext))
        }
    }
}