package com.example.basiclabproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.basiclabproject.feature.auth.TerminosYCondiciones
import com.example.basiclabproject.feature.auth.login.SignInScreen
import com.example.basiclabproject.feature.auth.signup.SignUpScreen
import com.example.basiclabproject.feature.courseScreen.CourseScreen
import com.example.basiclabproject.feature.home.HomeScreen
import com.example.basiclabproject.navigation.DETAIL_ARGUMENT_KEY
import com.example.basiclabproject.navigation.Screens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SetupNavGraph(
    navController: NavHostController
){
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

        composable(
            route = Screens.HomeScreen.route
        ){
            HomeScreen(navController)
        }

        composable(
            route = Screens.CourseScreen.route,
            arguments = listOf(
                navArgument(DETAIL_ARGUMENT_KEY){
                type = NavType.StringType
            })
        ) {
            val cursoId = it.arguments?.getString(DETAIL_ARGUMENT_KEY) ?: ""
//            Log.d("Args", it.arguments?.getString(DETAIL_ARGUMENT_KEY).toString())

            CourseScreen(navController, cursoId)
        }

        composable(
            route = Screens.TerminosYCondiciones.route
        ){
            TerminosYCondiciones(navController)
        }

    }

}
