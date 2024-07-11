@file:OptIn(ExperimentalMaterial3Api::class)

package com.singhtwenty2.konix.feature_home.presentation.screen.home_screen

import android.content.Context
import android.os.Vibrator
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.singhtwenty2.konix.core.ui.theme.BG_V1
import com.singhtwenty2.konix.core.ui.theme.ZERODHA_DARK
import com.singhtwenty2.konix.feature_home.presentation.component.CompanyItemComposable
import com.singhtwenty2.konix.feature_home.presentation.component.TopHomeSegmentComposable
import com.singhtwenty2.konix.feature_home.presentation.screen.company_info.CompanyInfoScreenComposable
import com.singhtwenty2.konix.feature_home.util.CompanyResponseHandler
import kotlinx.coroutines.launch

@Composable
fun HomeScreenComposable(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.value.isRefreshing
    )
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val uiColor = if (isSystemInDarkTheme()) ZERODHA_DARK else MaterialTheme.colorScheme.background

    LaunchedEffect(viewModel, context) {
        viewModel.companyListingResult.collect { result ->
            when (result) {
                is CompanyResponseHandler.Success -> {
                    val companies = result.data
                    companies?.let {
                        state.value = state.value.copy(
                            companyList = companies,
                            isLoading = false
                        )
                    } ?: run {
                        state.value = state.value.copy(
                            isLoading = false,
                            error = "No companies found"
                        )
                    }
                }

                is CompanyResponseHandler.BadRequest -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }

                is CompanyResponseHandler.InternalServerError -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }

                is CompanyResponseHandler.UnAuthorized -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }

                is CompanyResponseHandler.UnknownError -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            CompanyInfoScreenComposable(
                navController = navController,
                onBuyClick = {
                    vibrator.vibrate(30)
                }
            ) {
                vibrator.vibrate(30)
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(uiColor)
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            TopHomeSegmentComposable(onProfileClick = {
                vibrator.vibrate(30)
                navController.navigate("profile_screen")
            }) {
                vibrator.vibrate(30)
                navController.navigate("portfolio_screen")
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.value.searchQuery,
                onValueChange = {
                    viewModel.onEvent(
                        HomeScreenUiEvents.SearchQueryChanged(it)
                    )
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Search...")
                },
                maxLines = 1,
                singleLine = true
            )
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.onEvent(HomeScreenUiEvents.RefreshCompanies)
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.value.companyList.size) { i ->
                        val company = state.value.companyList[i]
                        CompanyItemComposable(
                            company = company,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(
                                        HomeScreenUiEvents.CompanyClicked(company.id)
                                    )
                                    scope.launch {
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                    vibrator.vibrate(50)
                                    Log.d("HomeScreenComposable", "Company clicked: $company")
                                }
                                .padding(16.dp)
                        )
                        if (i < state.value.companyList.size) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
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