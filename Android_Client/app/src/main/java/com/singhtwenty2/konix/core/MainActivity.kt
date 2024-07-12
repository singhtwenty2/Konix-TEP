package com.singhtwenty2.konix.core

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.singhtwenty2.konix.core.navigation.AppNavigatorComposable
import com.singhtwenty2.konix.core.ui.theme.KonixTheme
import com.singhtwenty2.konix.core.util.isTokenExpired
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KonixTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    val sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
                    val token = sharedPreferences.getString("jwt", null)
                    val startDestination = if (token != null && !isTokenExpired(token)) {
                        "home_feature"
                    } else {
                        "auth_feature"
                    }
                    AppNavigatorComposable(
                        navHostController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}
