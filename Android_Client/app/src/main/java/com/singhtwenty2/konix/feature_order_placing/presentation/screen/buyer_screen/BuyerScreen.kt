package com.singhtwenty2.konix.feature_order_placing.presentation.screen.buyer_screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.singhtwenty2.konix.core.ui.theme.ZERODHA_DARK
import com.singhtwenty2.konix.feature_auth.presentation.component.FormInputFieldComposable
import com.singhtwenty2.konix.feature_order_placing.util.OrderResponseHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@Composable
fun BuyerScreenComposable(
    modifier: Modifier = Modifier,
    companyId: Int,
    orderType: String,
    navController: NavController,
    viewModel: BuyerScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val uiColor = if (isSystemInDarkTheme()) ZERODHA_DARK else MaterialTheme.colorScheme.background
    val scope = rememberCoroutineScope()
    val timerState = remember { mutableIntStateOf(2) }
    val headline = if(orderType == "BUY") "BUY" else "SELL"

    LaunchedEffect(viewModel, context) {
        viewModel.orderResult.collect { result ->
            when (result) {
                is OrderResponseHandler.Success -> {
                    Toast
                        .makeText(
                            context,
                            "Order Placed Successfully",
                            Toast.LENGTH_SHORT
                        )
                }

                is OrderResponseHandler.BadRequest -> {
                    Toast
                        .makeText(
                            context,
                            "Bad Request",
                            Toast.LENGTH_SHORT
                        )
                }

                is OrderResponseHandler.InternalServerError -> {
                    Toast
                        .makeText(
                            context,
                            "Internal Server Error",
                            Toast.LENGTH_SHORT
                        )
                }

                is OrderResponseHandler.UnAuthorized -> {
                    Toast
                        .makeText(
                            context,
                            "UnAuthorized",
                            Toast.LENGTH_SHORT
                        )
                }

                is OrderResponseHandler.UnknownError -> {
                    Toast
                        .makeText(
                            context,
                            "Unknown Error",
                            Toast.LENGTH_SHORT
                        )
                }
            }
        }
    }

    LaunchedEffect(viewModel, companyId) {
        viewModel.startFetchingPrice(companyId)
        while (true) {
            for (i in 2 downTo 0) {
                timerState.intValue = i
                delay(1000)
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(uiColor)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            modifier = Modifier
                .padding(vertical = 4.dp),
            text = headline,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 1.dp,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        Text(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .align(Alignment.Start),
            text = "Type Iceberg",
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .align(Alignment.Start),
            text = "MIS Intraday",
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 1.dp,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp),
                text = "Current Price ${state.price} INR",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(4.dp))
            FormInputFieldComposable(
                label = "Quantity",
                icon = Icons.Rounded.Quiz,
                keyboardType = KeyboardType.Number,
                keyboardCapitalization = KeyboardCapitalization.None,
                autocorrect = false,
                initialValue = state.quantity.toString(),
            ) { text ->
                scope.launch {
                    val quantity =
                        text.toIntOrNull() ?: 0 // default to 0 if conversion fails
                    viewModel.onEvent(BuyerUiEvent.QuantityChanged(quantity))
                }
            }
        }
        Text(
            modifier = Modifier
                .padding(vertical = 4.dp),
            text = "Fetching price in ${timerState.intValue} seconds...",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
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
                if (state.quantity > 0) {
                    scope.launch {
                        viewModel.onEvent(BuyerUiEvent.BuyClicked(companyId, orderType))
                        vibrator.vibrate(30)
                        navController.navigate("order_detail_screen")
                    }

                } else {
                    Toast
                        .makeText(
                            context,
                            "Quantity should be greater than 0",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        ) {
            Text(
                text = "Place Order",
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