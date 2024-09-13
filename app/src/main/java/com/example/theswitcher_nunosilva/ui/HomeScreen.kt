package com.example.theswitcher_nunosilva.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.theswitcher_nunosilva.main.keepSplashOpened

@Composable
fun HomeScreen(navController: NavHostController) {
    Text(text = "Home Screen")
    keepSplashOpened = false
}