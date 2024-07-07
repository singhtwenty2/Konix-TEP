package com.singhtwenty2.konix.feature_auth.presentation.screen.verify_otp_screen

import android.content.Context
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Numbers
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
import com.singhtwenty2.konix.feature_auth.presentation.screen.component.AuthInputFieldComposable
import com.singhtwenty2.konix.feature_auth.presentation.screen.component.TopSegmentComposable
import com.singhtwenty2.konix.feature_auth.util.AuthResponseHandler

@Composable
fun VerifyOtpScreenComposable(
    modifier: Modifier = Modifier,
    viewModel: VerifyOtpScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    LaunchedEffect(viewModel, context) {
        viewModel.otpResult.collect { result ->
            when (result) {
                is AuthResponseHandler.Success -> {
                    Toast.makeText(
                        context,
                        "OTP verified successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("login_screen")
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
    ) {
        TopSegmentComposable(
            subHeadLine = stringResource(
                id = R.string.verify_otp_subtitle
            )
        )
        Spacer(modifier = Modifier.padding(16.dp))
        AuthInputFieldComposable(
            label = "OTP",
            icon = Icons.Rounded.Numbers,
            keyboardType = KeyboardType.Number,
            keyboardCapitalization = KeyboardCapitalization.None,
            autocorrect = false,
            initialValue = state.value.otp
        ) {
            viewModel.onEvent(VerifyOtpUiEvent.OtpChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 28.dp),
            enabled = !state.value.isOtpVerifyLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            ),
            onClick = {
                if (state.value.otp.length != 6) {
                    Toast.makeText(
                        context,
                        "Please enter a valid OTP",
                        Toast.LENGTH_SHORT
                    ).show()
                    vibrator.vibrate(40)
                } else {
                    viewModel.onEvent(VerifyOtpUiEvent.VerifyOtpClicked)
                    vibrator.vibrate(30)
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.verify_otp_button),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
    if (state.value.isOtpVerifyLoading) {
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