package com.example.theswitcher_nunosilva.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.theswitcher_nunosilva.core.Green
import com.example.theswitcher_nunosilva.core.TheSwitcher_NunoSilvaTheme
import com.example.theswitcher_nunosilva.model.DivisionDatabase
import com.example.theswitcher_nunosilva.model.prepopulateDatabase
import com.example.theswitcher_nunosilva.navigation.NavGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

var keepSplashOpened = true

class MainActivity : ComponentActivity() {

    private var navController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            keepSplashOpened
        }
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Green.hashCode(), Green.hashCode())
        )
        checkFirstTimeLaunch()
        setContent {
            TheSwitcher_NunoSilvaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        navController = rememberNavController()
                        NavGraph(navController = navController ?: return@Scaffold)
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