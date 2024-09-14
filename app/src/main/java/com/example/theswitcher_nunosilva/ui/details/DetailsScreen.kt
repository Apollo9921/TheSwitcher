package com.example.theswitcher_nunosilva.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.example.theswitcher_nunosilva.core.detailsBackgroundSize
import com.example.theswitcher_nunosilva.core.detailsImageSize
import com.example.theswitcher_nunosilva.core.detailsStateSize
import com.example.theswitcher_nunosilva.core.mediaQueryWidth
import com.example.theswitcher_nunosilva.core.normal
import com.example.theswitcher_nunosilva.core.small
import com.example.theswitcher_nunosilva.model.Division

@Composable
fun DetailsScreen(navController: NavHostController, division: Division) {
    Scaffold(
        topBar = { DetailsTopBar(navController, division) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(it)
        ) {
            DetailsScreenContent(division)
        }
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
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { navController.navigateUp() }
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

@Composable
private fun DetailsScreenContent(division: Division) {
    val backgroundSize = detailsBackgroundSize()
    val imageSize = detailsImageSize()
    val stateSize = detailsStateSize()
    val isOn = division.switch
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(backgroundSize)
                .background(MaterialTheme.colorScheme.tertiary),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = if (isOn) painterResource(id = R.drawable.switch_on) else painterResource(id = R.drawable.switch_off),
                contentDescription = "Switch",
                modifier = Modifier
                    .size(imageSize)
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = stringResource(id = R.string.division_state, division.name),
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.W400,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = if (isOn) stringResource(id = R.string.switch_on) else stringResource(id = R.string.switch_off),
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold,
            fontSize = stateSize,
            textAlign = TextAlign.Center,
        )
    }
}