package com.example.basiclabproject.feature.auth.login

import android.media.browse.MediaBrowser
import android.net.Uri
import android.os.Looper.prepare
import android.provider.CalendarContract.Colors
import android.widget.Button
import android.widget.Filter
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
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
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.basiclabproject.R
import com.example.basiclabproject.ui.stylepresets.TextInputs.textFieldStandard
import com.example.basiclabproject.ui.stylepresets.buttons.buttonSingInStyle
import com.example.basiclabproject.ui.theme.Green60
import org.w3c.dom.Text

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun SignInScreen(navController: NavController) {

    //viewModel
    val viewModel: SignInViewModel = hiltViewModel()

    val uiState = viewModel.state.collectAsState()

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    val passwordFocusRequester = FocusRequester()

    val focusManager: FocusManager = LocalFocusManager.current

    //bg uri
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


//    Scaffold(modifier = Modifier.fillMaxSize()) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(it)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            Image(
//                painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(80.dp)
//                    .background(Color.Black)
//            )
//
//            OutlinedTextField(
//                value = email,
//                onValueChange = {email = it},
//                label = { Text(text = "Email") },
//                placeholder = { Text(text = "Enter your email") },
//                modifier = Modifier.fillMaxWidth())
//
//            OutlinedTextField(
//                value = password,
//                onValueChange = {password = it},
//                label = { Text(text = "Password") },
//                placeholder = { Text(text = "Enter your password") },
//                modifier = Modifier.fillMaxWidth(),
//                visualTransformation = PasswordVisualTransformation()
//            )
//
//            Spacer(modifier = Modifier.size(16.dp))
//
//            if (uiState.value == SignInState.Loading){
//                CircularProgressIndicator()
//            }else{
//
//                Button(
//                    onClick = { },
//                    modifier = Modifier.fillMaxWidth(),
//                    //enabled = email.isNotEmpty() && password.isNotEmpty() && uiState.value == SignInState.Nothing || uiState.value == SignInState.Error
//                ) {
//
//                    Text(text = "Sign In")
//                }
//            }
//
//            Button(onClick = { navController.navigate("signup") },
//                modifier = Modifier.fillMaxWidth()) {
//
//                Text(text = "Sign Up")
//            }
//        }
//
//    }
//
//}

    Box(modifier = Modifier.fillMaxSize()) {

//        Image(
//            painter = painterResource(id = R.drawable.background),
//            contentDescription = "Background",
//            contentScale = ContentScale.FillHeight,
//            modifier = Modifier.matchParentSize()
//        )

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
                z = 1f
            }
        })
    }

    Column(
        Modifier
            .navigationBarsPadding()
            .padding(horizontal = 42.dp, vertical = 32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 90.dp)
        )

        Text(
            text = "CodeLab", fontSize = 35.sp, fontWeight = FontWeight.Bold, color = Color.White
        )

        TextField(
            value = email,
            leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = null) },
            onValueChange = {email = it},
            label = { Text(text = "Email") },
            placeholder = { Text(text = "TuEmail@gmail.com")},
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldStandard(),
            singleLine = true,
            shape = RoundedCornerShape(25.dp)
            )

        TextField(
            value = password,
            leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) },
            onValueChange = {password = it},
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
        if (uiState.value == SignInState.Loading){
            CircularProgressIndicator(color = Green60)
        }else{

            Button(
                onClick = { viewModel.signIn(email, password) },
                modifier = Modifier.fillMaxWidth(),
                colors = buttonSingInStyle(),
                enabled = email.isNotEmpty() && password.isNotEmpty() && uiState.value == SignInState.Nothing || uiState.value == SignInState.Error
            ) {

                Text(text = "Iniciar sesión", Modifier.padding(vertical = 8.dp))
            }
        }

        //ResetPassword Button
        TextButton(onClick = { }) {
            Text(
                text = "¿Olviaste tu contraseña?",
                color = Color.White,
                textDecoration = TextDecoration.Underline
            )
        }

        //Register Button
        OutlinedButton(
            onClick = { navController.navigate("signup") },
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(3.dp, Green60),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Red
            )
        ) {

            Text(text = "Registrarse", Modifier.padding(vertical = 8.dp))
        }

        Text(text = "¿No tienes una cuenta?", color = Color.White)

    }
}

//inputs
@Composable
fun TextInput(
    inputType: InputType, focusRequester: FocusRequester? = null, KeyboardActions: KeyboardActions
) {

    var value: String by remember { mutableStateOf("") }

    TextField(
        value = value,
        onValueChange = { value = it },
        Modifier
            .fillMaxWidth()
            .focusOrder(focusRequester = focusRequester ?: FocusRequester()),
        leadingIcon = { Icon(inputType.icon, contentDescription = null) },
        label = { Text(text = inputType.label) },
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
    val visualTransformation: VisualTransformation,
) {

    object Email : InputType(
        label = "Correo electrónico", icon = Icons.Default.Email, keyboardOptions = KeyboardOptions(
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
    SignInScreen(navController = rememberNavController())
}