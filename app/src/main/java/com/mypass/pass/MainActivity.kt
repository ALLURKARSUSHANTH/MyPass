package com.mypass.pass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.mypass.pass.screenui.AuthenticationViewModel
import com.mypass.pass.screenui.Navigation
import com.mypass.pass.ui.theme.MyPassTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyPassTheme {
                MaterialTheme(
                    colorScheme = MaterialTheme.colorScheme
                ) {
                    Navigation(authViewModel = AuthenticationViewModel())
                }
            }
        }
    }
}