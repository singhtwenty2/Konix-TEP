package com.singhtwenty2.konix.feature_portfolio.presentation.screen.portfolio_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.singhtwenty2.konix.core.ui.theme.ZERODHA_DARK

@Composable
fun PortfolioScreenComposable(
    modifier: Modifier = Modifier,
    viewModel: PortfolioScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    val context = LocalContext.current
    val uiColor = if (isSystemInDarkTheme()) ZERODHA_DARK else MaterialTheme.colorScheme.background
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.value.isRefreshing
    )
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            viewModel.onEvent(PortfolioScreenUiEvent.OnRefresh)
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .background(uiColor)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            if (state.isError) {
                Toast
                    .makeText(
                        context,
                        state.error,
                        Toast.LENGTH_SHORT
                    ).show()
            }
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "Portfolio Details",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    count = state.portfolio.size
                ) {
                    PortfolioDetailComponent(
                        portfolio = state.portfolio[it]
                    )
                }
            }
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