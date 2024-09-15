package com.example.theswitcher_nunosilva.ui.home.order

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ShowDropDownMenu(
    expanded: Boolean,
    items: List<String>,
    onClickOption: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest() },
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        items.forEachIndexed { index, s ->
            DropdownMenuItem(
                text = { Text(text = s) },
                onClick = {
                    onClickOption(index)
                    onDismissRequest()
                })
        }
    }
}