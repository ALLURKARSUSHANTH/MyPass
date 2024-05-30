package com.mypass.pass.screenui

sealed class Screen(val route: String) {
     data object Login : Screen("LoginScreen")
     data object HomeScreen : Screen("HomeScreen")
     data object RegistrationScreen : Screen("RegistrationScreen")
}