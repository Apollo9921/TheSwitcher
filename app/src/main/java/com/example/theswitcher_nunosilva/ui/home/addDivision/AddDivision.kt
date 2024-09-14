package com.example.theswitcher_nunosilva.ui.home.addDivision

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.theswitcher_nunosilva.R
import com.example.theswitcher_nunosilva.model.Division

@Composable
fun AddDivisionPopup(
    divisionsData: SnapshotStateList<Division>,
    onDismissRequest: () -> Unit,
    onSave: (String) -> Unit
) {
    val context = LocalContext.current
    val divisionsNames = divisionsData.map { it.name }
    var text by remember { mutableStateOf("") }
    val repeatedDivision = stringResource(id = R.string.division_already_exists)
    val emptyDivision = stringResource(id = R.string.division_name_empty)
    AlertDialog(
        onDismissRequest = {},
        title = { Text(stringResource(id = R.string.add_division)) },
        text = {
            Column {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = {
                        Text(
                            text = stringResource(id = R.string.add_division_name),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                when {
                    text.isEmpty() -> {
                        Toast.makeText(context, emptyDivision, Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    divisionsNames.contains(text) -> {
                        Toast.makeText(context, repeatedDivision, Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    else -> onSave(text)
                }
            }) {
                Text(
                    text = stringResource(id = R.string.save_division_button),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(
                    text = stringResource(id = R.string.cancel_division_button),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        })
}