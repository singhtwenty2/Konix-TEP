package com.singhtwenty2.konix.feature_auth.presentation.screen.signup_screen

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
import androidx.compose.material.icons.rounded.Cake
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Man
import androidx.compose.material.icons.rounded.MarkEmailRead
import androidx.compose.material.icons.rounded.Person2
import androidx.compose.material.icons.twotone.Lock
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
import com.singhtwenty2.konix.feature_auth.presentation.component.CustomDropdownComposable
import com.singhtwenty2.konix.feature_auth.presentation.component.SecuredInputFieldCompasable
import com.singhtwenty2.konix.feature_auth.presentation.component.TopSegmentComposable
import com.singhtwenty2.konix.feature_auth.util.AuthResponseHandler

@Composable
fun SignupScreenComposable(
    modifier: Modifier = Modifier,
    viewModel: SignupScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.state
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val uiColor = if (isSystemInDarkTheme()) ZERODHA_DARK else MaterialTheme.colorScheme.background

    LaunchedEffect(viewModel, context) {
        viewModel.signupResult.collect { result ->
            when (result) {
                is AuthResponseHandler.Success -> {
                    Toast.makeText(
                        context,
                        "Signup successful. Please verify your email to login",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("verify_otp_screen")
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
            .background(uiColor)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TopSegmentComposable(
            subHeadLine = stringResource(
                id =R.string.signup_screen_subtitle
            ))
        Spacer(modifier = Modifier.padding(16.dp))
        FormInputFieldComposable(
            label = "Name",
            icon = Icons.Rounded.Person2,
            keyboardType = KeyboardType.Text,
            keyboardCapitalization = KeyboardCapitalization.Words,
            autocorrect = false,
            initialValue = state.value.name
        ) {
            viewModel.onEvent(SignupUiEvent.NameChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        FormInputFieldComposable(
            label = "Email",
            icon = Icons.Rounded.MarkEmailRead,
            keyboardType = KeyboardType.Email,
            keyboardCapitalization = KeyboardCapitalization.None,
            autocorrect = false,
            initialValue = state.value.email
        ) {
            viewModel.onEvent(SignupUiEvent.EmailChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        FormInputFieldComposable(
            label = "Age",
            icon = Icons.Rounded.Cake,
            keyboardType = KeyboardType.Number,
            keyboardCapitalization = KeyboardCapitalization.None,
            autocorrect = false,
            initialValue = state.value.age
        ) {
            viewModel.onEvent(SignupUiEvent.AgeChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomDropdownComposable(
            label = "Gender",
            icon = Icons.Rounded.Man,
            selectedItem = state.value.gender,
            items = listOf(
                "MALE",
                "FEMALE",
                "LGBTQ_GROUP"
            )
        ) {
            viewModel.onEvent(SignupUiEvent.GenderChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        SecuredInputFieldCompasable(
            label = "Password",
            keyboardType = KeyboardType.Password,
            keyboardCapitalization = KeyboardCapitalization.None,
            autocorrect = false,
            initialValue = state.value.password
        ) {
            viewModel.onEvent(SignupUiEvent.PasswordChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        SecuredInputFieldCompasable(
            label = "Confirm Password",
            keyboardType = KeyboardType.Password,
            keyboardCapitalization = KeyboardCapitalization.None,
            autocorrect = false,
            initialValue = state.value.confirmPassword
        ) {
            viewModel.onEvent(SignupUiEvent.ConfirmPasswordChanged(it))
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
                if (state.value.name.isEmpty() || state.value.email.isEmpty() || state.value.age.isEmpty() || state.value.password.isEmpty() || state.value.confirmPassword.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Please fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                    vibrator.vibrate(40)
                } else {
                    vibrator.vibrate(30)
                    viewModel.onEvent(SignupUiEvent.SignupClicked)
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.signup_button),
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
                text = "Already have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Login",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable {
                        navController.navigate("login_screen")
                    }
            )
        }
    }
    if(state.value.isLoading) {
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