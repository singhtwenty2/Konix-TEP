package com.singhtwenty2.konix.feature_home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.singhtwenty2.konix.core.ui.theme.Green10
import com.singhtwenty2.konix.core.ui.theme.RED
import com.singhtwenty2.konix.feature_home.domain.model.CompanyListing
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun CompanyItemComposable(
    modifier: Modifier = Modifier,
    company: CompanyListing,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = company.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = company.marketCap.toString() + " INR",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    val change = remember { mutableFloatStateOf(generateRandomNumber()) }

                    LaunchedEffect(change.floatValue) {
                        while (true) {
                            delay(1500)
                            change.floatValue = generateRandomNumber()
                        }
                    }

                    if (change.floatValue > 0) {
                        Text(
                            text = "+${change.floatValue} %",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Green10,
                        )
                    } else if (change.floatValue < 0) {
                        Text(
                            text = "${change.floatValue} %",
                            style = MaterialTheme.typography.bodyMedium,
                            color = RED,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "(${company.symbol})",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

private fun generateRandomNumber(): Float {
    val sign = if (Random.nextBoolean()) "+" else "-"
    return String.format("%s%.2f", sign, Random.nextFloat() * 10).toFloat()
}
