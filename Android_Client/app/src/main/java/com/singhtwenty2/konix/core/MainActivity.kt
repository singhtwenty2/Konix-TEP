package com.singhtwenty2.konix.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.singhtwenty2.konix.core.ui.theme.KonixTheme
import com.singhtwenty2.konix.feature_auth.presentation.screen.signup_screen.SignupScreenComposable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KonixTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SignupScreenComposable()
                }
            }
        }
    }
}