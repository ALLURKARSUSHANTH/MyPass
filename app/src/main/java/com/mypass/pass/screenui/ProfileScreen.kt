package com.mypass.pass.screenui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userEmail = remember { mutableStateOf(TextFieldValue(currentUser?.email ?: "")) }
    var showDialog by remember { mutableStateOf(false) }
    var changePass by remember { mutableStateOf(false) }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .height(65.dp)
                    .width(300.dp),
                value = userEmail.value,
                onValueChange = { userEmail.value = it },
                label = { Text("Your Email Id :") },
                maxLines = 1,
                readOnly = true,
                visualTransformation = VisualTransformation.None,
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) }
            )
            TextButton(onClick = {
                changePass = true
            }) {
                Text(text = "Change Password Through Email ", fontWeight = FontWeight.Bold)
            }


            Button(onClick = {
                showDialog = true
            }) {
                Text(text = "SignOut", Modifier, fontWeight = FontWeight.Bold)
            }
        }
    if (changePass) {
        AlertDialog(onDismissRequest = { changePass = false },
            title = { Text(text = "Change Password") },
            text = { Text(text = "Do You Really want to Change Your Password") },
            confirmButton = {
                Button(onClick = {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(userEmail.value.text)
                    changePass= false
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(onClick = { changePass = false }) {
                    Text(text = "No")
                }
            }
        )
    }
    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false },
            title = { Text(text = "SIGN OUT") },
            text = { Text(text = "Do You Really want to SIGN OUT ") },
            confirmButton = {
                Button(onClick = {
                    FirebaseAuth.getInstance().signOut()
                    showDialog=false
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog=false }) {
                    Text(text = "No")
                }
            }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserProfile() {
    Scaffold(modifier = Modifier
        .padding(20.dp),
        content = {
            Column(
                modifier = Modifier
            ) {
                ProfileScreen()
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun NewPassPreview() {
    Scaffold(modifier = Modifier
        .padding(30.dp),
        content = {
            UserProfile()
        }
    )
}
