package com.example.theswitcher_nunosilva.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
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
import com.example.theswitcher_nunosilva.navigation.Destination
import com.example.theswitcher_nunosilva.ui.home.addDivision.AddDivisionPopup
import com.example.theswitcher_nunosilva.ui.home.deleteDivision.SwipeToDeleteContainer
import com.example.theswitcher_nunosilva.ui.home.order.ShowDropDownMenu
import org.koin.androidx.compose.koinViewModel

private var divisionsData = mutableStateListOf<Division>()
private var viewModel: HomeScreenViewModel? = null
private var showContent = mutableStateOf(false)
private var selectedOrderIndex = mutableIntStateOf(0)

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
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
        ) {
            GetDivisions(navController)
        }
        keepSplashOpened = false
    }
}

@Composable
private fun HomeTopBar() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf(
        stringResource(id = R.string.order_by_id),
        stringResource(id = R.string.order_by_name)
    )
    val isAddVisible = remember { mutableStateOf(false) }
    if (isAddVisible.value) {
        AddDivisionPopup(
            divisionsData = divisionsData,
            onDismissRequest = { isAddVisible.value = false },
            onSave = {
                divisionsData.clear()
                viewModel?.addDivision(it)
                isAddVisible.value = false
            }
        )
    }
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
            Column {
                Image(
                    painter = painterResource(id = R.drawable.order),
                    contentDescription = "Add",
                    modifier = Modifier
                        .size(imageSize)
                        .rotate(90f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { expanded = !expanded }
                )
                ShowDropDownMenu(
                    expanded = expanded,
                    items = items,
                    onClickOption = { it ->
                        when (it) {
                            0 -> {
                                divisionsData.sortBy { it.id }
                            }

                            1 -> {
                                divisionsData.sortBy { it.name.lowercase() }
                            }
                        }
                        selectedOrderIndex.intValue = it
                        expanded = false
                    },
                    onDismissRequest = { expanded = false }
                )
            }
            Image(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Add",
                modifier = Modifier
                    .size(imageSize)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { isAddVisible.value = true }
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
                showContent.value = true
                viewModel?.isSuccess?.value = false
            }
        }

        viewModel?.isError?.value == true -> {
            ErrorScreen(viewModel?.messageError?.value ?: "")
        }

        showContent.value -> {
            if (selectedOrderIndex.intValue == 0) {
                divisionsData.sortBy { it.id }
            } else {
                divisionsData.sortBy { it.name.lowercase() }
            }
            HomeScreenContent(navController)
        }
    }
}

@Composable
private fun HomeScreenContent(navController: NavHostController) {
    var isItemDeleted by remember { mutableStateOf(false) }
    val deletedIndex = remember { mutableIntStateOf(0) }
    if (isItemDeleted) {
        isItemDeleted = false
        divisionsData.removeAt(deletedIndex.intValue)
    }
    val state = rememberLazyListState()
    LazyColumn(
        state = state,
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = divisionsData.size,
            key = { divisionsData[it].id }
        ) { index ->
            var isOn by remember { mutableStateOf(divisionsData[index].switch) }
            SwipeToDeleteContainer(
                item = divisionsData[index],
                onDelete = {
                    viewModel?.deleteDivision(divisionsData[index])
                    deletedIndex.intValue = index
                    isItemDeleted = true
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            navController.navigate(
                                Destination.Details(divisionsData[index]),
                                navOptions = navOptions {
                                    launchSingleTop = true
                                }
                            )
                        },
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
                        checked = isOn,
                        onCheckedChange = {
                            viewModel?.updateDivisionMode(divisionsData[index].id, it)
                            isOn = it
                        },
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
}