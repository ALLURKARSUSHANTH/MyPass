package com.mypass.pass.screenui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var emailHasError by mutableStateOf(false)
    var emailError by mutableStateOf("")
    var passwordHasError by mutableStateOf(false)
    var passwordError by mutableStateOf("")

    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Authentication successful
                        callback(true, null)
                    } else {
                        // Authentication failed
                        val errorMessage = task.exception?.message ?: "Unknown error"
                        callback(false, errorMessage)
                    }
                }
        } catch (e: Exception) {
            "${e.message}"
        }
    }

    fun textValidation() {
        when {
            email.isEmpty() -> {
                emailHasError = true
                emailError = "This Field is required"
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                emailHasError = true
                emailError = "Invalid Email"
            }

            password.isEmpty() -> {
                passwordHasError = true
                passwordError = "Password cannot be Empty"
            }

            password.length < 8 -> {
                passwordHasError = true
                passwordError = "Password must be at least 8 characters long"
            }

            !password.any { it.isLetter() } -> {
                passwordHasError = true
                passwordError = "Password must contain at least one letter"
            }

            !password.any { it.isDigit() } -> {
                passwordHasError = true
                passwordError = "Password must contain at least one digit"
            }

        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Login(navController: NavController, loginViewModel : LoginViewModel = viewModel(), context: Context = LocalContext.current) {
    val scope = rememberCoroutineScope()
    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    modifier = Modifier
                        .height(65.dp)
                        .width(300.dp),
                    value = loginViewModel.email, onValueChange = { loginViewModel.email = it },
                    label = { Text("Email") },
                    maxLines = 1,
                    isError = loginViewModel.emailHasError,
                    visualTransformation = VisualTransformation.None,
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) }
                )
                if (loginViewModel.emailHasError) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = loginViewModel.emailError,
                        color = Color.Red
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                OutlinedTextField(
                    modifier = Modifier
                        .height(65.dp)
                        .width(300.dp),
                    value = loginViewModel.password,

                    onValueChange = { loginViewModel.password = it },
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text("Password") },
                    maxLines = 1,
                    isError = loginViewModel.passwordHasError,
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) }
                )
                if (loginViewModel.passwordHasError) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = loginViewModel.passwordError,
                        color = Color.Red
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(40.dp)
                )
                Button(modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(100.dp),
                    onClick = {
                        loginViewModel.textValidation()
                        if (loginViewModel.email.isNotEmpty() && loginViewModel.password.isNotEmpty()) {
                            scope.launch {
                                loginViewModel.loginUser(
                                    loginViewModel.email,
                                    loginViewModel.password
                                ) { success, errorMessage ->
                                    if (success) {
                                        // Authentication successful
                                        Toast.makeText(
                                            context,
                                            "Login Successful!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        // Navigate to HomeScreen
                                        navController.navigate(Screen.HomeScreen.route)
                                    } else {
                                        // Authentication failed
                                        Toast.makeText(
                                            context,
                                            "Login Failed: $errorMessage",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    }
                ) {
                    Text(text = "LOGIN")
                }

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                Text(text = "Don't have an Account!?SignUp!")

                TextButton(onClick = { navController.navigate(Screen.RegistrationScreen.route) }) {
                    Text(text = "Register now!")
                }

            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    val navController= rememberNavController()
    Login(navController = navController, loginViewModel = viewModel())
  }