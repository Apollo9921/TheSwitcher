package com.example.theswitcher_nunosilva.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.theswitcher_nunosilva.core.TheSwitcher_NunoSilvaTheme
import com.example.theswitcher_nunosilva.navigation.NavGraph

var keepSplashOpened = true

class MainActivity : ComponentActivity() {

    private var navController: NavHostController? = null

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            keepSplashOpened
        }
        enableEdgeToEdge()
        setContent {
            TheSwitcher_NunoSilvaTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        navController = rememberNavController()
                        NavGraph(navController = navController ?: return@Scaffold)
                    }
                }
            }
        }
    }
}