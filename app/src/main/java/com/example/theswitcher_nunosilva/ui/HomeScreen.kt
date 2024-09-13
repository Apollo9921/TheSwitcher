package com.example.theswitcher_nunosilva.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.theswitcher_nunosilva.R
import com.example.theswitcher_nunosilva.core.ErrorScreen
import com.example.theswitcher_nunosilva.core.Green
import com.example.theswitcher_nunosilva.core.ShowEmptyScreen
import com.example.theswitcher_nunosilva.core.White
import com.example.theswitcher_nunosilva.core.mediaQueryWidth
import com.example.theswitcher_nunosilva.core.normal
import com.example.theswitcher_nunosilva.core.small
import com.example.theswitcher_nunosilva.main.keepSplashOpened
import com.example.theswitcher_nunosilva.model.Division
import org.koin.androidx.compose.koinViewModel

private var divisionsData = mutableStateListOf<Division>()
private var viewModel: HomeScreenViewModel? = null

@Composable
fun HomeScreen(navController: NavHostController) {
    viewModel = koinViewModel<HomeScreenViewModel>()
    Scaffold(
        topBar = { HomeTopBar() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(it)
        ) {
            GetDivisions(navController)
        }
        keepSplashOpened = false
    }
}

@Composable
private fun HomeTopBar() {
    val imageSize =
        if (mediaQueryWidth() <= small) {
            25.dp
        } else if (mediaQueryWidth() <= normal) {
            35.dp
        } else {
            45.dp
        }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Green)
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            color = White,
            fontWeight = FontWeight.W400,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(2f)
        )
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Add",
                modifier = Modifier
                    .size(imageSize)
            )
        }
    }
}

@Composable
private fun GetDivisions(navController: NavHostController) {
    when {
        viewModel?.isSuccess?.value == true -> {
            divisionsData = viewModel?.divisionsData ?: SnapshotStateList()
            if (divisionsData.isEmpty()) {
                ShowEmptyScreen()
            } else {
                HomeScreenContent(navController)
            }
        }

        viewModel?.isError?.value == true -> {
            ErrorScreen(viewModel?.messageError?.value ?: "")
        }
    }
}

@Composable
private fun HomeScreenContent(navController: NavHostController) {
    val state = rememberLazyListState()
    LazyColumn(
        state = state,
        modifier = Modifier.fillMaxSize()
    ) {
        items(divisionsData.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = divisionsData[index].name,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.W400,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                )
                Switch(
                    checked = divisionsData[index].switch,
                    onCheckedChange = {  },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.secondary,
                        checkedTrackColor = MaterialTheme.colorScheme.tertiary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.tertiary
                    )
                )
            }
            HorizontalDivider(
                color = MaterialTheme.colorScheme.tertiary,
                thickness = 1.dp
            )
        }
    }
}