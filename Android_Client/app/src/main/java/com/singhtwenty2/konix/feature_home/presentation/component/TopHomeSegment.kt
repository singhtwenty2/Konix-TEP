package com.singhtwenty2.konix.feature_home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Person3
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.singhtwenty2.konix.R

@Composable
fun TopHomeSegmentComposable(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    onPortfolioClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onProfileClick) {
                Icon(
                    modifier = Modifier
                        .size(65.dp),
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile"
                )
            }
            IconButton(onClick = onPortfolioClick) {
                Icon(
                    modifier = Modifier
                        .size(65.dp),
                    tint = MaterialTheme.colorScheme.error,
                    painter = painterResource(id = R.drawable.portfolio),
                    contentDescription = "Portfolio"
                )
            }
        }
    }
}