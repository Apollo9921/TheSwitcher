package com.example.theswitcher_nunosilva.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.theswitcher_nunosilva.model.Division
import com.example.theswitcher_nunosilva.navigation.CustomNavType.DivisionType
import com.example.theswitcher_nunosilva.ui.details.DetailsScreen
import com.example.theswitcher_nunosilva.ui.home.HomeScreen
import kotlin.reflect.typeOf

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Destination = Destination.Home
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Destination.Home> {
            HomeScreen(navController)
        }

        composable<Destination.Details>(
            typeMap = mapOf(
                typeOf<Division>() to DivisionType
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(500)
                )
            }
        ) {
            val arguments = it.toRoute<Destination.Details>()
            DetailsScreen(navController, arguments.division)
        }
    }
}