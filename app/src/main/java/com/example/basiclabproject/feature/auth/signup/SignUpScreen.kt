package com.example.basiclabproject.feature.auth.signup

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.basiclabproject.R
import com.example.basiclabproject.ui.stylepresets.TextInputs.textFieldStandard
import com.example.basiclabproject.ui.stylepresets.buttons.buttonDefaultStyle
import com.example.basiclabproject.ui.theme.Green60
import com.example.basiclabproject.ui.theme.Purple80

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun SignUpScreen(navController: NavController) {

    //ViewModel
    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    var name by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    var cPassword by remember { mutableStateOf("") }

    val uri = RawResourceDataSource.buildRawResourceUri(R.raw.sginbg)

    val context = LocalContext.current

    val gradientColors = listOf(Color.Cyan, Color.Blue, Green60)

    val passwordFocusRequester = FocusRequester()

    val focusManager: FocusManager = LocalFocusManager.current

    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is SignUpState.Success -> {
                navController.navigate("home")
            }

            is SignUpState.Error -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {

        var checked by remember { mutableStateOf(true) }


        val exoPlayer = remember(context) {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(uri))
                prepare()
                playWhenReady = true
                repeatMode = Player.REPEAT_MODE_ALL
            }
        }
        AndroidView(factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
            }
        })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(23.dp, 130.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            brush = Brush.linearGradient(
                                colors = gradientColors
                            )
                        )
                    ) {
                        append("Crea tu")
                    }
                }, fontSize = 60.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Left
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            brush = Brush.linearGradient(
                                colors = gradientColors
                            )
                        )
                    ) {
                        append("cuenta")
                    }
                }, fontSize = 60.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Left
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(23.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                placeholder = { Text(text = "Enter your name") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldStandard(),
                shape = RoundedCornerShape(25.dp),
                singleLine = true
                )

//            TextInput(
//                InputType.User,
//                name = name,
//                KeyboardActions = KeyboardActions(onNext = {
//                    focusManager.clearFocus()
//                })
//            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Enter your email") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldStandard(),
                shape = RoundedCornerShape(25.dp),
                singleLine = true
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter your password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = textFieldStandard(),
                shape = RoundedCornerShape(25.dp),
                singleLine = true
            )

            TextField(
                value = cPassword,
                onValueChange = { cPassword = it },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Confirm your password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                isError = password.isEmpty() && password != cPassword,
                colors = textFieldStandard(),
                shape = RoundedCornerShape(25.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.size(16.dp))

            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it }
            )

            Button(
                onClick = {
                    viewModel.signUp(name, email, password)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = buttonDefaultStyle(),
                enabled = checked && name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password == cPassword && cPassword.isNotEmpty()
            ) {

                Text(text = "Crear cuenta", Modifier.padding(vertical = 8.dp))
            }

        }
    }

}

//inputs
@Composable
fun TextInput(
    inputType: InputType, focusRequester: FocusRequester? = null, KeyboardActions: KeyboardActions, name :String
) {

    var value: String by remember { mutableStateOf("") }

    TextField(
        value = name,
        onValueChange = { value = it },
        Modifier
            .fillMaxWidth()
            .focusOrder(focusRequester = focusRequester ?: FocusRequester()),
        leadingIcon = { Icon(inputType.icon, contentDescription = null) },
        label = { Text(text = inputType.label) },
        shape = RoundedCornerShape(25.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedContainerColor = Color.White,
            disabledIndicatorColor = Color.Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.White,
            focusedPlaceholderColor = Color.Gray,
            focusedLabelColor = Color.Black,
        ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = KeyboardActions
    )

}

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {

    object Email : InputType(
        label = "Correo Gmail", icon = Icons.Default.Email, keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next, keyboardType = KeyboardType.Email
        ), visualTransformation = VisualTransformation.None
    )

    object User : InputType(
        label = "Nombre", icon = Icons.Default.Email, keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next, keyboardType = KeyboardType.Email
        ), visualTransformation = VisualTransformation.None
    )

    object Password : InputType(
        label = "Contraseña", icon = Icons.Default.Lock, keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ), visualTransformation = PasswordVisualTransformation()
    )

}


@Preview(
    showSystemUi = true, showBackground = true
)
@Composable
fun SignInScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}