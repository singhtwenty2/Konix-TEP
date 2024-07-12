package com.singhtwenty2.konix.feature_auth.presentation.screen.demat_account_screen

import android.content.Context
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.filled.FiberNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.BrowserNotSupported
import androidx.compose.material.icons.rounded.FiberNew
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
import com.singhtwenty2.konix.feature_auth.presentation.component.AddressInputFieldComposable
import com.singhtwenty2.konix.feature_auth.presentation.component.CustomDropdownComposable
import com.singhtwenty2.konix.feature_auth.presentation.component.FormInputFieldComposable
import com.singhtwenty2.konix.feature_auth.presentation.component.TopSegmentComposable
import com.singhtwenty2.konix.feature_auth.util.AuthResponseHandler

@Composable
fun DematAccountCreationScreenComposable(
    modifier: Modifier = Modifier,
    viewModel: DematAccountCreationViewModel = hiltViewModel(),
    navController: NavController
) {
    var state = viewModel.state.value
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val uiColor = if (isSystemInDarkTheme()) ZERODHA_DARK else MaterialTheme.colorScheme.background

    LaunchedEffect(viewModel, context) {
        viewModel.dematResult.collect { result ->
            when (result) {
                is AuthResponseHandler.Success -> {
                    navController.navigate("home_feature") {
                        popUpTo("auth_feature") {
                            inclusive = true
                        }
                    }
                }

                is AuthResponseHandler.BadRequest -> {
                    state = state.copy(error = "Bad Request")
                }

                is AuthResponseHandler.InternalServerError -> {
                    state = state.copy(error = "Internal Server Error")
                }

                is AuthResponseHandler.UnAuthorized -> {
                    state = state.copy(error = "UnAuthorized")
                }

                is AuthResponseHandler.UnknownError -> {
                    state = state.copy(error = "Unknown Error")
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
        Spacer(modifier = Modifier.height(32.dp))
        TopSegmentComposable(
            subHeadLine = stringResource(
                id = R.string.demat_account_creation
            )
        )
        Spacer(modifier = Modifier.padding(16.dp))
        FormInputFieldComposable(
            label = "Account Holder Name",
            icon = Icons.Default.Person,
            keyboardType = KeyboardType.Text,
            keyboardCapitalization = KeyboardCapitalization.Words,
            autocorrect = false,
            initialValue = state.accountHolderName
        ) {
            viewModel.onEvent(DematAccountCreationUiEvent.AccountHolderNameChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        FormInputFieldComposable(
            label = "Phone Number",
            icon = Icons.Default.Phone,
            keyboardType = KeyboardType.Phone,
            keyboardCapitalization = KeyboardCapitalization.None,
            autocorrect = false,
            initialValue = state.phoneNumber
        ) {
            viewModel.onEvent(DematAccountCreationUiEvent.PhoneNumberChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        AddressInputFieldComposable(
            label = "Current Address",
            icon = Icons.Default.Person,
            keyboardType = KeyboardType.Text,
            keyboardCapitalization = KeyboardCapitalization.Words,
            autocorrect = false,
            initialValue = state.address
        ) {
            viewModel.onEvent(DematAccountCreationUiEvent.AddressChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        FormInputFieldComposable(
            label = "PAN Number",
            icon = Icons.Default.FiberNew,
            keyboardType = KeyboardType.Text,
            keyboardCapitalization = KeyboardCapitalization.None,
            autocorrect = false,
            initialValue = state.panNumber
        ) {
            viewModel.onEvent(DematAccountCreationUiEvent.PanNumberChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        FormInputFieldComposable(
            label = "Nominee Name",
            icon = Icons.Default.PersonAdd,
            keyboardType = KeyboardType.Text,
            keyboardCapitalization = KeyboardCapitalization.Words,
            autocorrect = false,
            initialValue = state.nominee
        ) {
            viewModel.onEvent(DematAccountCreationUiEvent.NomineeChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomDropdownComposable(
            label = "Nominee Relation",
            icon = Icons.Rounded.FiberNew,
            selectedItem = state.nomineeRelation,
            items = listOf(
                "FATHER",
                "MOTHER",
                "SISTER",
                "BROTHER",
                "WIFE",
                "HUSBAND",
                "DAUGHTER",
                "SON",
                "OTHERS"
            )
        ) {
            viewModel.onEvent(DematAccountCreationUiEvent.NomineeRelationChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomDropdownComposable(
            label = "Broker Name",
            icon = Icons.Rounded.BrowserNotSupported,
            selectedItem = state.brokerName,
            items = listOf(
                "KONIX",
                "ZERODHA",
                "UPSTOX",
                "ANGEL BROKING",
                "ICICI DIRECT",
                "OTHERS"
            )
        ) {
            viewModel.onEvent(DematAccountCreationUiEvent.BrokerNameChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 28.dp),
            enabled = !state.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            ),
            onClick = {
                if (state.accountHolderName.isEmpty() || state.phoneNumber.isEmpty() || state.address.isEmpty() || state.panNumber.isEmpty() || state.nominee.isEmpty() || state.nomineeRelation.isEmpty() || state.brokerName.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Please fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                    vibrator.vibrate(100)
                } else {
                    vibrator.vibrate(30)
                    viewModel.onEvent(DematAccountCreationUiEvent.CreateAccountClicked)
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.create_demat_account),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
    if (state.isLoading) {
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