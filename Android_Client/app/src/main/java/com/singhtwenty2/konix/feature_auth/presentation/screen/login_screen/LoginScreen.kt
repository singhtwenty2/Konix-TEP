package com.singhtwenty2.konix.feature_auth.presentation.screen.login_screen

import android.content.Context
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.MarkEmailRead
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.singhtwenty2.konix.R
import com.singhtwenty2.konix.core.ui.theme.ZERODHA_DARK
import com.singhtwenty2.konix.feature_auth.presentation.component.FormInputFieldComposable
import com.singhtwenty2.konix.feature_auth.presentation.component.TopSegmentComposable
import com.singhtwenty2.konix.feature_auth.util.AuthResponseHandler

@Composable
fun LoginScreenComposable(
    modifier: Modifier = Modifier,
    viewModel: LoginScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val uiColor = if (isSystemInDarkTheme()) ZERODHA_DARK else MaterialTheme.colorScheme.background

    LaunchedEffect(viewModel, context) {
        viewModel.loginResult.collect { result ->
            when (result) {
                is AuthResponseHandler.Success -> {
                    Toast.makeText(
                        context,
                        "Login successful",
                        Toast.LENGTH_SHORT
                    ).show()
//                    navController.navigate("home_feature") {
//                        popUpTo("auth_feature") {
//                            inclusive = true
//                        }
//                    }
                }

                is AuthResponseHandler.BadRequest -> {
                    Toast.makeText(
                        context,
                        "Bad Request. Please check your input",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is AuthResponseHandler.InternalServerError -> {
                    Toast.makeText(
                        context,
                        "Internal Server Error. Please try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is AuthResponseHandler.UnAuthorized -> {
                    Toast.makeText(
                        context,
                        "UnAuthorized. Please login again",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is AuthResponseHandler.UnknownError -> {
                    Toast.makeText(
                        context,
                        "Unknown error occurred. Please try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    LaunchedEffect(viewModel, context) {
        viewModel.userStatusResult.collect { result ->
            when (result) {
                is AuthResponseHandler.Success -> {
                    val kycStatus = result.data?.isKycDone ?: false
                    val dematStatus = result.data?.isDematCreated ?: false
                    if (kycStatus && dematStatus) {
                        navController.navigate("home_feature") {
                            popUpTo("auth_feature") {
                                inclusive = true
                            }
                        }
                    } else if (!kycStatus) {
                        navController.navigate("kyc_screen") {
                            popUpTo("login_screen") {
                                inclusive = true
                            }
                        }
                    } else if (!dematStatus) {
                        navController.navigate("demat_screen") {
                            popUpTo("login_screen") {
                                inclusive = true
                            }
                        }
                    }
                }

                is AuthResponseHandler.BadRequest -> {
                    Toast.makeText(
                        context,
                        "Bad Request. Please check your input(KYC/DEMAT)",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is AuthResponseHandler.InternalServerError -> {
                    Toast.makeText(
                        context,
                        "Internal Server Error. Please try again later(KYC/DEMAT)",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is AuthResponseHandler.UnAuthorized -> {
                    Toast.makeText(
                        context,
                        "UnAuthorized. Please login again(KYC/DEMAT)",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is AuthResponseHandler.UnknownError -> {
                    Toast.makeText(
                        context,
                        "Unknown error occurred. Please try again later(KYC/DEMAT)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(uiColor)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TopSegmentComposable(
            subHeadLine = stringResource(
                id = R.string.login_screen_subtitle
            )
        )
        Spacer(modifier = Modifier.padding(16.dp))
        FormInputFieldComposable(
            label = "Email",
            icon = Icons.Rounded.MarkEmailRead,
            keyboardType = KeyboardType.Email,
            keyboardCapitalization = KeyboardCapitalization.None,
            autocorrect = false,
            initialValue = state.value.email
        ) {
            viewModel.onEvent(LoginUiEvent.EmailChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        FormInputFieldComposable(
            label = "Password",
            icon = Icons.Rounded.Lock,
            keyboardType = KeyboardType.Password,
            keyboardCapitalization = KeyboardCapitalization.None,
            autocorrect = false,
            initialValue = state.value.password
        ) {
            viewModel.onEvent(LoginUiEvent.PasswordChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 28.dp),
            enabled = !state.value.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            ),
            onClick = {
                if (state.value.email.isEmpty() || state.value.password.isEmpty()) {
                    vibrator.vibrate(40)
                    Toast.makeText(
                        context,
                        "Please fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.onEvent(LoginUiEvent.LoginClicked)
                    vibrator.vibrate(30)
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.login_button),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable {
                        navController.navigate("signup_screen")
                    }
            )
        }
    }
    if (state.value.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}