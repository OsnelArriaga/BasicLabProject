package com.example.basiclabproject.ui.stylepresets.buttons

import android.widget.Button
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basiclabproject.ui.theme.Green20
import com.example.basiclabproject.ui.theme.Green40
import com.example.basiclabproject.ui.theme.Green60

// Definir estilos comunes como funciones de extensión
@Composable
fun buttonTextStyle(): TextStyle {
    return TextStyle(
        color = Color.White,
        fontSize = 16.sp
    )
}

@Composable
fun buttonShape() = RoundedCornerShape(8.dp)

@Composable
fun buttonSingInStyle(): ButtonColors {
    return ButtonDefaults.buttonColors(
        containerColor = Green60,
        disabledContainerColor = Color.LightGray
    )
}

@Composable
fun buttonDefaultStyle(): ButtonColors {
    return ButtonDefaults.buttonColors(
        containerColor = Green60,
        disabledContainerColor = Color.LightGray
    )
}
