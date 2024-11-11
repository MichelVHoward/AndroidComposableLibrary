package com.howard.composable.composelibrary.dropdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun <T> ComboBox(
    map: Map<String, T>,
    initialKey: String,
    label: String = "Select an option",
    updateState: (T) -> Unit
) {

    if (!map.containsKey(initialKey)) {
        throw IllegalArgumentException("Illegal use of combo box")
    }

    val options = map.keys.toList()
    var selectedOption by remember { mutableStateOf(initialKey) }
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = selectedOption) {
        updateState(map[selectedOption]!!)
    }

    Column(modifier = Modifier) {
        // TextField to show the selected option
        OutlinedTextField(
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .testTag("EditIcon")
                        .clickable { expanded = !expanded },
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                )
            },
            value = selectedOption,
            onValueChange = { selectedOption = it },
            label = { Text(label) },
            readOnly = true, // Make TextField read-only
            modifier = Modifier
                .fillMaxWidth()
            // Toggle dropdown on click
        )

        // DropdownMenu to show the list of options
        DropdownMenu(
            // containerColor = MaterialTheme.colorScheme.primaryContainer,
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("DropdownMenu")

        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    modifier = Modifier.testTag("Item${index + 1}"),
                    text = { Text(option.toString()) },
                    onClick = {
                        selectedOption = option // Set selected option
                        expanded = false // Close dropdown
                    }
                )
            }
        }
    }
}
