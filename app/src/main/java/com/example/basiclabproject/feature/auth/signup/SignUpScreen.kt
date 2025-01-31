package com.example.basiclabproject.feature.auth.signup

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.basiclabproject.R
import com.example.basiclabproject.ui.stylepresets.TextInputs.textFieldStandard
import com.example.basiclabproject.ui.stylepresets.buttons.buttonPrimaryStyle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {

    //ViewModel
    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    var name by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    var cPassword by remember { mutableStateOf("") }

    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is SignUpState.Success -> {
                navController.navigate("home")
            }

            is SignUpState.Error -> {
                Toast.makeText(context, "Datos de sesión incorrectos...", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        topBar = {
            TopAppBar(
                modifier = Modifier.size(120.dp, 80.dp),
                title = {
                    BackButton(onBackTouch = { navController.popBackStack() })
                }
            )
        }
    )
    {

        var checked by remember { mutableStateOf(true) }

//        VideoPlayer(uri)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

//            Icon(
//                imageVector = Icons.Filled.AccountCircle,
//                contentDescription = "Account",
//                tint = Color.White,
//                modifier = Modifier.size(90.dp)
//            )

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 110.dp)
            )

            Text(
                text = "Crea tu cuenta", fontSize = 32.sp, fontWeight = FontWeight.Bold
            )

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Nombre de usuario") },
                placeholder = { Text(text = "Nombre para el inicio") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldStandard(),
                shape = RoundedCornerShape(25.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Home",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Correo electrónico") },
                placeholder = { Text(text = "Correo para tu cuenta") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldStandard(),
                shape = RoundedCornerShape(25.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Contraseña") },
                placeholder = { Text(text = "Mínimo 8 caracteres") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = textFieldStandard(),
                shape = RoundedCornerShape(25.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Home",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            )

            TextField(
                value = cPassword,
                onValueChange = { cPassword = it },
                label = { Text(text = "Confirmar contraseña") },
                placeholder = { Text(text = "Vuelve a escribir tu contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                isError = password.isEmpty() && password != cPassword,
                colors = textFieldStandard(),
                shape = RoundedCornerShape(25.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Home",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            )

            //Aviso para aceptar los términos y condiciones
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.tertiaryContainer,
                            RoundedCornerShape(15.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it }
                    )

                    Column(
                        modifier = Modifier.padding(top = 15.dp),
                        verticalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "Al marcar esta casilla, estarás aceptando los",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )

                        TextButton(
                            modifier = Modifier.height(30.dp),
                            onClick = { navController.navigate("terminosYCondiciones") }
                        ) {
                            Text(
                                text = "Términos y Condiciones",
                                textDecoration = TextDecoration.Underline,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )

                        }
                    }
                }
            }

            Button(
                onClick = {
                    viewModel.signUp(name, email, password)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = buttonPrimaryStyle(),
                enabled = checked && name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password == cPassword && cPassword.isNotEmpty()
            ) {

                Text(text = "Crear cuenta", Modifier.padding(vertical = 8.dp))
            }

            //Informacion sobre seguridad
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
                    .background(Color.Transparent, RoundedCornerShape(15.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = " Tus datos están seguros bajo cifrado",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                )
            }
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
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
        }
    })
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

//Boton regresar
@Composable
fun BackButton(onBackTouch: () -> Unit) {
    IconButton(
        onClick = onBackTouch,
        modifier = Modifier
            .padding(0.dp, 20.dp)
            .size(60.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        )
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back Button",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
    }
}