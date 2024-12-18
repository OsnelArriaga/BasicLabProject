package com.example.basiclabproject

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.basiclabproject.feature.auth.login.SignInScreen
import com.example.basiclabproject.feature.auth.signup.SignUpScreen
import com.example.basiclabproject.navigation.Screens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SetupNavGraph(){

    val navController = rememberNavController()

    val currentUser = FirebaseAuth.getInstance().currentUser

    val start = if (currentUser != null) "home" else "login"

    NavHost(
        navController = navController,
        startDestination = start
    ){

        composable(
            route = Screens.LoginScreen.route
        ){
            SignInScreen(navController)
        }

        composable(
            route = Screens.SignUpScreen.route
        ){
            SignUpScreen(navController)
        }

    }




}