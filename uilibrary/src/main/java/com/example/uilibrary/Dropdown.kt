package com.example.uilibrary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

enum class DropdownState { Enabled, Hover, Pressed, Focus, ReadOnly, Error, Success, Disabled }

@Composable
fun CustomDropdown(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    state: DropdownState = DropdownState.Enabled,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val borderColor = when (state) {
        DropdownState.Enabled -> Color.Gray
        DropdownState.Hover -> Color.Blue
        DropdownState.Pressed -> Color.DarkGray
        DropdownState.Focus -> Color.Cyan
        DropdownState.ReadOnly -> Color.LightGray
        DropdownState.Error -> Color.Red
        DropdownState.Success -> Color.Green
        DropdownState.Disabled -> Color.Gray.copy(alpha = 0.5f)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.body2)

        // Wrapping in Box to make it clickable
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = state == DropdownState.Enabled) { expanded = true }
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                enabled = state != DropdownState.Disabled,
                isError = state == DropdownState.Error,
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon",
                        modifier = Modifier.clickable(enabled = state == DropdownState.Enabled) { expanded = true }
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor,
                    disabledBorderColor = borderColor,
                    errorBorderColor = Color.Red
                )
            )
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    if (state != DropdownState.Disabled && state != DropdownState.ReadOnly) {
                        onOptionSelected(option)
                        expanded = false
                    }
                }) {
                    Text(option)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomDropdown() {
    var selectedOption by remember { mutableStateOf("Select an option") }

    CustomDropdown(
        label = "Transaction Type",
        options = listOf("Deposit", "Withdrawal", "Transfer"),
        selectedOption = selectedOption,
        onOptionSelected = { selectedOption = it },
        state = DropdownState.Enabled
    )
}
