package com.singhtwenty2.konix.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.singhtwenty2.konix.feature_auth.presentation.screen.login_screen.LoginScreenComposable
import com.singhtwenty2.konix.feature_auth.presentation.screen.signup_screen.SignupScreenComposable
import com.singhtwenty2.konix.feature_auth.presentation.screen.verify_otp_screen.VerifyOtpScreenComposable

@Composable
fun AppNavigatorComposable(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = "auth_feature",
    ) {
        navigation(
            startDestination = "login_screen",
            route = "auth_feature",
        ) {
            composable("login_screen") {
                LoginScreenComposable(navController = navHostController)
            }
            composable("signup_screen") {
                 SignupScreenComposable(navController = navHostController)
            }
            composable("verify_otp_screen") {
                 VerifyOtpScreenComposable(navController = navHostController)
            }
        }
        navigation(
            startDestination = "home_screen",
            route = "home_feature",
        ) {
            composable("home_screen") {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "Home Screen")
                }
            }
        }
    }
}