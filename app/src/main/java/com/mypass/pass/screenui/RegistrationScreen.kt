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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistrationScreen(navController: NavController, validViewModel: Validation =viewModel(), context: Context = LocalContext.current) {
    val scope = rememberCoroutineScope()
    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = validViewModel.email,
                    onValueChange = { validViewModel.email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = validViewModel.emailHasError,
                    maxLines = 1,
                    leadingIcon = { Icon(Icons.Filled.MailOutline, contentDescription = null) }

                )
                if (validViewModel.emailHasError) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = validViewModel.emailError,
                        color = Red
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = validViewModel.password,
                    onValueChange = { validViewModel.password = it },
                    label = { Text("Password") },
                    isError = validViewModel.passwordHasError,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },

                    )
                if (validViewModel.passwordHasError) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = validViewModel.passwordError,
                        color = Red
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        validViewModel.textValidation()
                        if (!validViewModel.emailHasError && !validViewModel.passwordHasError) {
                            scope.launch {
                                validViewModel.registerUser(
                                    validViewModel.email,
                                    validViewModel.password
                                ) { success, errorMessage ->
                                    if (success) {
                                        // Authentication successful
                                        Toast.makeText(
                                            context,
                                            "Login Successful!",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
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
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Register")
                }
            }
        }
    )
}

class Validation: ViewModel(){
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var emailHasError by mutableStateOf(false)
    var emailError by mutableStateOf("")
    var passwordHasError by mutableStateOf(false)
    var passwordError by mutableStateOf("")

     fun registerUser(email: String, password: String,callback: (Boolean, String?) -> Unit) {
        try {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{ task ->
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

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {
    val navController = rememberNavController()
    RegistrationScreen(navController = navController)
}


