package com.singhtwenty2.konix.feature_home.presentation.screen.exchange_screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.singhtwenty2.konix.core.ui.theme.ZERODHA_DARK
import com.singhtwenty2.konix.feature_home.presentation.screen.home_screen.HomeScreenViewModel
import com.singhtwenty2.konix.feature_home.util.CompanyResponseHandler

@Composable
fun ExchangeDetailScreenComposable(
    modifier: Modifier = Modifier,
    viewModel: ExchangeScreenViewModel = hiltViewModel(),
    navController: NavController,
    id: Int
) {
    val state = viewModel.state
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val uiColor = if (isSystemInDarkTheme()) ZERODHA_DARK else MaterialTheme.colorScheme.background

    LaunchedEffect(key1 = id) {
        viewModel.getExchangeDetails(id)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(uiColor)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        state.value.exchangeDetail?.let {
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
                text = "Location ${it.location} INR",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .align(Alignment.Start),
                text = "TIMEZONE ${it.timeZone}",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge,
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
                text = "OPENING HOUR ${it.openingHours}",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .align(Alignment.Start),
                text = "CLOSING HOUR ${it.closingHours}",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .align(Alignment.Start),
                text = "CURRENCY ${it.currency} ESTABLISHED ${it.establishedDate}",
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