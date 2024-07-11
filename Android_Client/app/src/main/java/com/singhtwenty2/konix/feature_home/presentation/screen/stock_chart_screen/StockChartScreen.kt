package com.singhtwenty2.konix.feature_home.presentation.screen.stock_chart_screen

import android.content.Context
import android.os.Build
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.singhtwenty2.konix.feature_home.presentation.component.StockChartComposable
import com.singhtwenty2.konix.feature_home.util.CompanyResponseHandler
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StockChartScreenComposable(
    modifier: Modifier = Modifier,
    viewModel: StockChartViewModel = hiltViewModel(),
    navController: NavController,
    companyId: Int
) {
    val state = viewModel.state
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(companyId) {
        viewModel.onEvent(StockChartUiEvent.OnRefresh(companyId))
    }

    LaunchedEffect(viewModel) {
        viewModel.stockPriceResult.collect { result ->
            when (result) {
                is CompanyResponseHandler.Success -> {
                    viewModel.state.value = state.value.copy(stockPrices = result.data)
                }

                is CompanyResponseHandler.BadRequest -> {
                    viewModel.state.value = state.value.copy(error = "Bad Request")
                }

                is CompanyResponseHandler.InternalServerError -> {
                    viewModel.state.value = state.value.copy(error = "Internal Server Error")
                }

                is CompanyResponseHandler.UnAuthorized -> {
                    viewModel.state.value = state.value.copy(error = "UnAuthorized")
                }

                is CompanyResponseHandler.UnknownError -> {
                    viewModel.state.value = state.value.copy(error = "Unknown Error")
                }
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.historicalStockPriceResult.collect { result ->
            when (result) {
                is CompanyResponseHandler.Success -> {
                    viewModel.state.value = state.value.copy(historicalStockPrices = result.data)
                }

                is CompanyResponseHandler.BadRequest -> {
                    viewModel.state.value = state.value.copy(error = "Bad Request")
                }

                is CompanyResponseHandler.InternalServerError -> {
                    viewModel.state.value = state.value.copy(error = "Internal Server Error")
                }

                is CompanyResponseHandler.UnAuthorized -> {
                    viewModel.state.value = state.value.copy(error = "UnAuthorized")
                }

                is CompanyResponseHandler.UnknownError -> {
                    viewModel.state.value = state.value.copy(error = "Unknown Error")
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cumulative Price Fetch",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Switch(
                checked = state.value.isCumulativePriceFetch,
                onCheckedChange = {
                    viewModel.onEvent(StockChartUiEvent.OnCumulativePriceFetch(it))
                    coroutineScope.launch {
                        vibrator.vibrate(50)
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
                    uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            )
        }
        if (state.value.isCumulativePriceFetch) {
            state.value.historicalStockPrices?.let {
                StockChartComposable(
                    stockPrices = it,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        } else {
            state.value.stockPrices?.let {
                StockChartComposable(
                    stockPrices = it,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}