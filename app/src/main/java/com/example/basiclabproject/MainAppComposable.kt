package com.example.basiclabproject

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.basiclabproject.feature.home.HomeScreen
import com.example.basiclabproject.feature.auth.login.SignInScreen
import com.example.basiclabproject.feature.auth.signup.SignUpScreen
import com.example.basiclabproject.feature.courseScreen.CourseScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainApp(){

    Surface(modifier = Modifier.fillMaxSize())
    {
        val navController = rememberNavController()

        val currentUser = FirebaseAuth.getInstance().currentUser

        val start = if (currentUser != null) "home" else "login"


        NavHost(navController = navController, startDestination = start) {

            composable("login"){
                SignInScreen(navController)
            }

            composable("signup"){
                SignUpScreen(navController)
            }

            composable("home"){
                HomeScreen(navController)
            }


            //Navegacion dinamica para los cursos de AspectosBasicos
//            composable("chat/{aspectosBasicosId}", arguments = listOf(
//                navArgument("aspectosBasicosId"){
//                    type = NavType.StringType
//                }
//            )){
//                val courseScreenId = it.arguments?.getString("aspectosBasicosId") ?: ""
//                CourseScreen(navController, 12, "Intermedio")
//            }

        }
    }

}