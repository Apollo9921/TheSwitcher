package com.example.theswitcher_nunosilva.ui.details

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.theswitcher_nunosilva.R
import com.example.theswitcher_nunosilva.core.Green
import com.example.theswitcher_nunosilva.core.White
import com.example.theswitcher_nunosilva.core.mediaQueryWidth
import com.example.theswitcher_nunosilva.core.normal
import com.example.theswitcher_nunosilva.core.small
import com.example.theswitcher_nunosilva.model.Division

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(navController: NavHostController, division: Division) {
    Scaffold(
        topBar = { DetailsTopBar(navController, division) }
    ) {

    }
}

@Composable
private fun DetailsTopBar(navController: NavHostController, division: Division) {
    val imageSize =
        if (mediaQueryWidth() <= small) {
            25.dp
        } else if (mediaQueryWidth() <= normal) {
            35.dp
        } else {
            45.dp
        }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Green)
            .padding(20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Add",
                modifier = Modifier
                    .size(imageSize)
                    .clickable { navController.navigateUp() }
            )
            Text(
                text = stringResource(id = R.string.back),
                color = White,
                fontWeight = FontWeight.W400,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                textAlign = TextAlign.Center,
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = division.name,
                color = White,
                fontWeight = FontWeight.W400,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(2f)
            )
        }
    }
}