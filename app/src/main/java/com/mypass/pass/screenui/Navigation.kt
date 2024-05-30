package com.mypass.pass.screenui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

class AuthenticationViewModel : ViewModel() {
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private val firebaseAuth = FirebaseAuth.getInstance()

    init {
        _isLoggedIn.value = firebaseAuth.currentUser != null

        firebaseAuth.addAuthStateListener { auth ->
            _isLoggedIn.value = auth.currentUser != null
        }
    }
}



@Composable
fun Navigation(authViewModel: AuthenticationViewModel) {

    val navController = rememberNavController()
    val isLoggedIn by authViewModel.isLoggedIn.observeAsState(initial = false)


    NavHost(navController = navController, startDestination =if (isLoggedIn) Screen.HomeScreen.route else Screen.Login.route) {
        composable(route = Screen.Login.route) {
            Login(navController = navController,loginViewModel=viewModel(), context = LocalContext.current)
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(NavigationViewModel())
        }
        composable(route = Screen.RegistrationScreen.route) {
            RegistrationScreen(navController= navController)
        }
    }
}