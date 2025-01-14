package com.example.basiclabproject.ui.stylepresets.TextInputs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun textFieldStandard(): TextFieldColors {
    return TextFieldDefaults.colors(
        disabledIndicatorColor = Color.Transparent,
        unfocusedContainerColor = MaterialTheme.colorScheme.inversePrimary,
        unfocusedIndicatorColor = Color.Transparent,
        unfocusedTextColor = MaterialTheme.colorScheme.primary,
        unfocusedPlaceholderColor = Color.Gray,
        unfocusedSupportingTextColor = Color.Gray,
        unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
        errorIndicatorColor = Color.Transparent,
        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
        errorLabelColor = MaterialTheme.colorScheme.error,
        focusedIndicatorColor = Color.Transparent,
        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        focusedPlaceholderColor = Color.Gray,
        focusedSupportingTextColor = Color.Gray,
        focusedLabelColor = MaterialTheme.colorScheme.secondary,
        focusedTextColor = MaterialTheme.colorScheme.secondary,
    )
}