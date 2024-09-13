package com.example.theswitcher_nunosilva.core

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Black,
    secondary = White,
    tertiary = Grey
)

private val LightColorScheme = lightColorScheme(
    primary = White,
    secondary = Black,
    tertiary = Grey
)

@Composable
fun TheSwitcher_NunoSilvaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

    MaterialTheme(
      colorScheme = colorScheme,
      content = content
    )
}