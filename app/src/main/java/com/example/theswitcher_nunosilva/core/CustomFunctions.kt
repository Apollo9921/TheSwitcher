package com.example.theswitcher_nunosilva.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theswitcher_nunosilva.R

val small = 600.dp
val normal = 840.dp

@Composable
fun mediaQueryWidth(): Dp {
    return LocalContext.current.resources.displayMetrics.widthPixels.dp / LocalDensity.current.density
}

@Composable
fun ShowEmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.empty_divisions),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ErrorScreen(error: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = error,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun detailsBackgroundSize(): Dp {
    return if (mediaQueryWidth() <= small) {
        200.dp
    } else if (mediaQueryWidth() <= normal) {
        300.dp
    } else {
        400.dp
    }
}

@Composable
fun detailsImageSize(): Dp {
    return if (mediaQueryWidth() <= small) {
        200.dp
    } else if (mediaQueryWidth() <= normal) {
        300.dp
    } else {
        400.dp
    }
}

@Composable
fun detailsStateSize(): TextUnit {
    return if (mediaQueryWidth() <= small) {
        50.sp
    } else if (mediaQueryWidth() <= normal) {
        100.sp
    } else {
        150.sp
    }
}