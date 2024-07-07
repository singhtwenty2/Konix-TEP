package com.singhtwenty2.konix.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.singhtwenty2.konix.core.navigation.AppNavigatorComposable
import com.singhtwenty2.konix.core.ui.theme.KonixTheme
import com.singhtwenty2.konix.feature_auth.presentation.screen.login_screen.LoginScreenComposable
import com.singhtwenty2.konix.feature_auth.presentation.screen.signup_screen.SignupScreenComposable
import com.singhtwenty2.konix.feature_auth.presentation.screen.verify_otp_screen.VerifyOtpScreenComposable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KonixTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    AppNavigatorComposable(navHostController = navController)
                }
            }
        }
    }
}