package com.singhtwenty2.konix.feature_auth.presentation.screen.kyc_screen

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
import androidx.compose.material.icons.rounded.FiberNew
import androidx.compose.material.icons.rounded.HomeWork
import androidx.compose.material.icons.rounded.Inventory
import androidx.compose.material.icons.rounded.LocalFlorist
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Work
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
import com.singhtwenty2.konix.feature_auth.presentation.component.FormInputFieldComposable
import com.singhtwenty2.konix.feature_auth.presentation.component.CustomDropdownComposable
import com.singhtwenty2.konix.feature_auth.presentation.component.TopSegmentComposable
import com.singhtwenty2.konix.feature_auth.util.AuthResponseHandler

@Composable
fun KycScreenComposable(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: KycScreenViewModel = hiltViewModel()
) {
    var state = viewModel.state.value
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val uiColor = if (isSystemInDarkTheme()) ZERODHA_DARK else MaterialTheme.colorScheme.background

    LaunchedEffect(viewModel, context) {
        viewModel.kycResult.collect{ result ->
            when(result) {
                is AuthResponseHandler.Success -> {
                    vibrator.vibrate(100)
                    navController.navigate("demat_screen") {
                        popUpTo("kyc_screen") {
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
                id = R.string.kyc_screen_sub_headline
            )
        )
        Spacer(modifier = Modifier.padding(16.dp))
        FormInputFieldComposable(
            label = "Phone Number",
            icon = Icons.Rounded.Phone,
            keyboardType = KeyboardType.Number,
            keyboardCapitalization = KeyboardCapitalization.None,
            autocorrect = false,
            initialValue = state.phoneNumber
        ) {
            viewModel.onEvent(KycUiEvent.PhoneNumberChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        FormInputFieldComposable(
            label = "Aadhar Number",
            icon = Icons.Rounded.Newspaper,
            keyboardType = KeyboardType.Number,
            keyboardCapitalization = KeyboardCapitalization.None,
            autocorrect = false,
            initialValue = state.aadharNumber
        ) {
            viewModel.onEvent(KycUiEvent.AadharNumberChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        FormInputFieldComposable(
            label = "Pan Number",
            icon = Icons.Rounded.FiberNew,
            keyboardType = KeyboardType.Text,
            keyboardCapitalization = KeyboardCapitalization.Characters,
            autocorrect = false,
            initialValue = state.panNumber
        ) {
            viewModel.onEvent(KycUiEvent.PanNumberChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        FormInputFieldComposable(
            label = "Annual Income",
            icon = Icons.Rounded.Money,
            keyboardType = KeyboardType.Number,
            keyboardCapitalization = KeyboardCapitalization.None,
            autocorrect = false,
            initialValue = state.annualIncome
        ) {
            viewModel.onEvent(KycUiEvent.AnnualIncomeChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        AddressInputFieldComposable(
            label = "Permanent Address",
            icon = Icons.Rounded.HomeWork,
            keyboardType = KeyboardType.Text,
            keyboardCapitalization = KeyboardCapitalization.Words,
            autocorrect = false,
            initialValue = state.address,
        ) {
            viewModel.onEvent(KycUiEvent.AddressChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomDropdownComposable(
            label = "Employment Status",
            icon = Icons.Rounded.Work,
            selectedItem = state.employmentStatus,
            items = listOf(
                "EMPLOYED",
                "SELF_EMPLOYED",
                "UNEMPLOYED",
                "STUDENT",
                "RETIRED",
                "OTHER"
            )
        ) {
            viewModel.onEvent(KycUiEvent.EmploymentStatusChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomDropdownComposable(
            label = "Investment Experience",
            icon = Icons.Rounded.Inventory,
            selectedItem = state.investmentExperience,
            items = listOf(
                "NONE",
                "LIMITED",
                "GOOD",
                "EXTENSIVE"
            )
        ) {
            viewModel.onEvent(KycUiEvent.InvestmentExperienceChanged(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomDropdownComposable(
            label = "Risk Tolerance",
            icon = Icons.Rounded.LocalFlorist,
            selectedItem = state.riskTolerance,
            items = listOf(
                "LOW",
                "MEDIUM",
                "HIGH"
            )
        ) {
            viewModel.onEvent(KycUiEvent.RiskToleranceChanged(it))
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
                if(state.phoneNumber.isEmpty() || state.aadharNumber.isEmpty() || state.panNumber.isEmpty() || state.annualIncome.isEmpty() || state.address.isEmpty() || state.employmentStatus.isEmpty() || state.investmentExperience.isEmpty() || state.riskTolerance.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Please fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                    vibrator.vibrate(100)
                } else {
                    vibrator.vibrate(30)
                    viewModel.onEvent(KycUiEvent.SubmitKyc)
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.kyc_screen_submit_button),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
    if(state.isLoading) {
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