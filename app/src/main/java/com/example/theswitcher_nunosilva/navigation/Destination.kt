package com.example.theswitcher_nunosilva.navigation

import com.example.theswitcher_nunosilva.model.Division
import kotlinx.serialization.Serializable

@Serializable
sealed class Destination {
    @Serializable
    data object Home : Destination()
    @Serializable
    data class Details(val division: Division) : Destination()
}