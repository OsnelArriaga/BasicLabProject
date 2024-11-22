package com.example.basiclabproject.feature.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.basiclabproject.R

@Composable
fun SignInScreen(navController: NavController){

    //viewModel
    val viewModel: SignInViewModel = hiltViewModel()

    val uiState = viewModel.state.collectAsState()

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    LaunchedEffect(key1 = uiState.value){
        when(uiState.value){
            is SignInState.Success -> {
                navController.navigate("home")
            }
            is SignInState.Error -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.Black)
            )

            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Enter your email") },
                modifier = Modifier.fillMaxWidth())

            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter your password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.size(16.dp))

            if (uiState.value == SignInState.Loading){
                CircularProgressIndicator()
            }else{

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    //enabled = email.isNotEmpty() && password.isNotEmpty() && uiState.value == SignInState.Nothing || uiState.value == SignInState.Error
                ) {

                    Text(text = "Sign In")
                }
            }

            Button(onClick = { navController.navigate("signup") },
                modifier = Modifier.fillMaxWidth()) {

                Text(text = "Sign Up")
            }
        }

    }

}

@Preview(
    showSystemUi = true, showBackground = true
)
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = rememberNavController())
}