package com.example.basiclabproject.navigation

sealed class Screens(val route: String) {

    object  LoginScreen : Screens("signIn_screen")
    object  SignUpScreen : Screens("signUp_screen")
    object  HomeScreen : Screens("home_screen")
    object  CourseScreen : Screens("course_screen/{id}")


}