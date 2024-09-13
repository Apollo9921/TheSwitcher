package com.example.theswitcher_nunosilva.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination {
    @Serializable
    data object Home : Destination()
}