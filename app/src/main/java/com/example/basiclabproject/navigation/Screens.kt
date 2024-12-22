package com.example.basiclabproject.navigation

const val DETAIL_ARGUMENT_KEY = "id"
//const val DETAIL_ARGUMENT_KEY2 = "dificultad"

sealed class Screens(val route: String) {

    object  LoginScreen : Screens("login")
    object  SignUpScreen : Screens("signUp")
    object  HomeScreen : Screens("home")
    object  CourseScreen : Screens("courseScreen/{$DETAIL_ARGUMENT_KEY}"){
        fun passId(id: String): String {
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id.toString())
        }
    }


}