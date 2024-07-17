package com.singhtwenty2.konix.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.singhtwenty2.konix.feature_order_placing.presentation.screen.buyer_screen.BuyerScreenComposable
import com.singhtwenty2.konix.feature_order_placing.presentation.screen.order_detail_screen.OrderScreenComposable
import com.singhtwenty2.konix.feature_portfolio.presentation.screen.portfolio_screen.PortfolioScreenComposable
import com.singhtwenty2.konix.feature_profile.presentation.profile_screen.ProfileScreenComposable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigatorComposable(
    navHostController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        enterTransition = {
            fadeIn(animationSpec = tween(550)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, tween(550)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(550)) + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(550)
            )
        },

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
                ProfileScreenComposable(navController = navHostController)
            }
            composable("portfolio_screen") {
                PortfolioScreenComposable(navController = navHostController)
            }
            composable(
                route = "buy_screen/{companyId}/{orderType}",
                arguments = listOf(
                    navArgument("companyId") {
                        type = NavType.IntType
                    },
                    navArgument("orderType") {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                val companyId = entry.arguments?.getInt("companyId") ?: 0
                val orderType = entry.arguments?.getString("orderType") ?: ""

                BuyerScreenComposable(
                    companyId = companyId,
                    orderType = orderType,
                    navController = navHostController
                )
            }
            composable(
                route = "order_detail_screen"
            ) {
                OrderScreenComposable(navController = navHostController)
            }
        }
    }
}
