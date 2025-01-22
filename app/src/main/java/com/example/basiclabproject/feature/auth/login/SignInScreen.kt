package com.example.basiclabproject.feature.auth.login

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
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
import com.example.basiclabproject.feature.auth.signup.VideoPlayer
import com.example.basiclabproject.ui.stylepresets.TextInputs.textFieldStandard
import com.example.basiclabproject.ui.stylepresets.buttons.buttonSecondaryStyle
import com.example.basiclabproject.ui.stylepresets.buttons.buttonPrimaryStyle

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun SignInScreen(navController: NavController) {

    //viewModel
    val viewModel: SignInViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    //Bloquear ir hacia atras
    val shouldBlockBack = remember { true }
    BackHandler(enabled = shouldBlockBack){
        Log.w("BackHandler", "Bloqueado")
    }

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    //bg uri\
    val uri = RawResourceDataSource.buildRawResourceUri(R.raw.sginbg)

    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is SignInState.Success -> {
                navController.navigate("home")
            }

            is SignInState.Error -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
    ) {

        VideoPlayer(uri)

        Column(
            Modifier
                .navigationBarsPadding()
                .padding(horizontal = 42.dp, vertical = 32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 90.dp)
            )

            Text(
                text = "CodeLab",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            TextField(
                value = email,
                leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = null) },
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldStandard(),
                singleLine = true,
                shape = RoundedCornerShape(25.dp)
            )

            TextField(
                value = password,
                leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) },
                onValueChange = { password = it },
                label = { Text(text = "Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = textFieldStandard(),
                singleLine = true,
                shape = RoundedCornerShape(25.dp)
            )

//        TextInput(InputType.Email , KeyboardActions = KeyboardActions(onNext = {
//            passwordFocusRequester.requestFocus()
//        }))
//
//        TextInput(InputType.Password, KeyboardActions = KeyboardActions(onDone = {
//            focusManager.clearFocus()
//        }), focusRequester = passwordFocusRequester)

            //SigIn Button
            if (uiState.value == SignInState.Loading) {
                CircularProgressIndicator(color = Color.Green)
            } else {

                Button(
                    onClick = {
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            Toast.makeText(
                                context,
                                "Ingrese un correo electrónico válido",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        } else
                            viewModel.signIn(email, password)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = buttonPrimaryStyle(),
                    enabled = email.isNotEmpty() && password.isNotEmpty() && uiState.value == SignInState.Nothing || uiState.value == SignInState.Error
                ) {
                    Text(text = "Iniciar sesión", Modifier.padding(vertical = 8.dp))
                }
            }

            //ResetPassword Button
            TextButton(onClick = {
                if (email.isEmpty()) {
                    Toast.makeText(context, "Ingrese un correo electrónico", Toast.LENGTH_SHORT)
                        .show()
                    return@TextButton
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(
                        context,
                        "Ingrese un correo electrónico válido",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@TextButton
                } else {
                    viewModel.resetPassword(email)
                    Toast.makeText(
                        context,
                        "Se ha enviado un correo de recuperación",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }) {
                Text(
                    text = "¿Olviaste tu contraseña?",
                    color = Color.White,
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            //Register Button
            Button(
                onClick = { navController.navigate("signup") },
                modifier = Modifier.fillMaxWidth(),
                colors = buttonSecondaryStyle()
            ) {

                Text(text = "Registrarse", Modifier.padding(vertical = 8.dp))
            }

            Text(
                text = "¿No tienes una cuenta?",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )

        }
    }
}

//Reproductor de video
@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(uri: Uri) {

    val context = LocalContext.current

    // Crear el ExoPlayer solo una vez
    val exoPlayer = remember(uri) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ALL
        }
    }

    // Liberar el ExoPlayer cuando la composición se elimine
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Mostrar el reproductor de video
    AndroidView(factory = { context ->
        PlayerView(context).apply {
            player = exoPlayer
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        }
    })
}

@Preview(
    showSystemUi = true, showBackground = true
)
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = rememberNavController())
}