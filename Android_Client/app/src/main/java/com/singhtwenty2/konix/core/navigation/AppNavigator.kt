package com.singhtwenty2.konix.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.singhtwenty2.konix.feature_auth.presentation.screen.demat_account_screen.DematAccountCreationScreenComposable
import com.singhtwenty2.konix.feature_auth.presentation.screen.kyc_screen.KycScreenComposable
import com.singhtwenty2.konix.feature_auth.presentation.screen.login_screen.LoginScreenComposable
import com.singhtwenty2.konix.feature_auth.presentation.screen.signup_screen.SignupScreenComposable
import com.singhtwenty2.konix.feature_auth.presentation.screen.verify_otp_screen.VerifyOtpScreenComposable
import com.singhtwenty2.konix.feature_home.presentation.screen.exchange_screen.ExchangeDetailScreenComposable
import com.singhtwenty2.konix.feature_home.presentation.screen.home_screen.HomeScreenComposable
import com.singhtwenty2.konix.feature_home.presentation.screen.stock_chart_screen.StockChartScreenComposable

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
            composable("kyc_screen") {
                KycScreenComposable(navController = navHostController)
            }
            composable("demat_screen") {
                DematAccountCreationScreenComposable(navController = navHostController)
            }
        }
        navigation(
            startDestination = "home_screen",
            route = "home_feature",
        ) {
            composable("home_screen") {
                HomeScreenComposable(navController = navHostController)
            }
            composable("company_detail_screen") {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Company Detail Screen")
                }
            }
            composable(
                route = "exchange_detail_screen/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { entry ->
                entry.arguments?.getInt("id")?.let { id ->
                    ExchangeDetailScreenComposable(
                        navController = navHostController,
                        id = id
                    )
                }
            }
            composable(
                route = "stock_chart_screen/{companyId}",
                arguments = listOf(
                    navArgument("companyId") {
                        type = NavType.IntType
                    }
                )
            ) { entry ->
                entry.arguments?.getInt("companyId")?.let { companyId ->
                    StockChartScreenComposable(
                        navController = navHostController,
                        companyId = companyId
                    )
                }
            }
            composable("profile_screen") {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Profile Screen")
                }
            }
            composable("portfolio_screen") {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Portfolio Screen")
                }
            }
        }
    }
}