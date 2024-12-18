package com.example.basiclabproject.ui.stylepresets.TextInputs

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun textFieldStandard() : TextFieldColors{
    return TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedContainerColor = Color.White,
            disabledIndicatorColor = Color.Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.White,
            focusedPlaceholderColor = Color.Gray,
            focusedLabelColor = Color.Black,
    )
}