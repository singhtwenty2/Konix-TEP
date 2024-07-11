package com.singhtwenty2.konix.feature_home.presentation.screen.company_info

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.singhtwenty2.konix.core.ui.theme.RED
import com.singhtwenty2.konix.core.ui.theme.ZERODHA_DARK
import com.singhtwenty2.konix.feature_home.presentation.screen.home_screen.HomeScreenViewModel
import com.singhtwenty2.konix.feature_home.util.CompanyResponseHandler

@Composable
fun CompanyInfoScreenComposable(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController,
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
) {

    val state = viewModel.state
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val uiColor = if (isSystemInDarkTheme()) ZERODHA_DARK else MaterialTheme.colorScheme.background

    LaunchedEffect(viewModel, context) {
        viewModel.companyInfoResult.collect { result ->
            when (result) {
                is CompanyResponseHandler.Success -> {
                    val companyDetails = result.data
                    companyDetails?.let {
                        state.value = state.value.copy(
                            companyDetails = it,
                            error = null,
                            isLoading = false
                        )
                    }
                }

                is CompanyResponseHandler.BadRequest -> {
                    state.value = state.value.copy(
                        error = "Bad Request",
                        isLoading = false
                    )
                }

                is CompanyResponseHandler.InternalServerError -> {
                    state.value = state.value.copy(
                        error = "Internal Server Error",
                        isLoading = false
                    )
                }

                is CompanyResponseHandler.UnAuthorized -> {
                    state.value = state.value.copy(
                        error = "UnAuthorized",
                        isLoading = false
                    )
                }

                is CompanyResponseHandler.UnknownError -> {
                    state.value = state.value.copy(
                        error = "Unknown Error",
                        isLoading = false
                    )
                }
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
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp)
                    .clip(
                        RoundedCornerShape(4.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = onBuyClick
            ) {
                Text(
                    text = "BUY",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp)
                    .clip(
                        RoundedCornerShape(4.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RED,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = onSellClick
            ) {
                Text(
                    text = "SELL",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        state.value.companyDetails?.let {
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .align(Alignment.Start),
                text = "${it.name} (${it.symbol})",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .align(Alignment.Start),
                text = "MARKET-CAP ${it.marketCap} INR",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .align(Alignment.Start),
                text = "SECTOR ${it.sector}",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clickable {
                            vibrator.vibrate(30)
                            navController.navigate("exchange_detail_screen/1")
                        },
                    text = "NSE",
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clickable {
                            vibrator.vibrate(30)
                            navController.navigate("exchange_detail_screen/2")
                        },
                    text = "BSE",
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        vibrator.vibrate(30)
                        navController.navigate("stock_chart_screen/${it.id}")
                    },
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Rounded.GraphicEq,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    text = "View Stock Chart",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
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
                text = it.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
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
                text = "IPO DATE ${it.ipoDate}",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .align(Alignment.Start),
                text = "Headquarter ${it.headquarters}",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .align(Alignment.Start),
                text = "CEO ${it.ceo}",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .align(Alignment.Start),
                text = "Number Of Employees ${it.employees}",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .align(Alignment.Start),
                text = "Founded ${it.foundedDate}",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
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
                    .align(Alignment.Start)
                    .clickable {
                        vibrator.vibrate(30)
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.website)).apply {
                            setPackage("com.android.chrome")
                        }
                        context.startActivity(intent)
                    },
                text = "Official Website Of ${it.name}",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}