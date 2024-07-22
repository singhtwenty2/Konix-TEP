package com.singhtwenty2.konix.feature_auth.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.singhtwenty2.konix.R

@Composable
fun SecuredInputFieldCompasable(
    modifier: Modifier = Modifier,
    label: String,
    keyboardType: KeyboardType,
    keyboardCapitalization: KeyboardCapitalization,
    autocorrect: Boolean,
    initialValue: String,
    onTextChanged: (String) -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = initialValue,
            onValueChange = onTextChanged,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = label
                )
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = keyboardCapitalization,
                autoCorrect = autocorrect,
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    val icon =
                        if (isPasswordVisible) ImageVector.vectorResource(id = R.drawable.visibility_off_) else ImageVector.vectorResource(
                            id = R.drawable.baseline_visibility_24
                        )
                    Icon(
                        imageVector = icon,
                        contentDescription = "Taggable Password Visibility Option",
                        tint = if (isPasswordVisible) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                }
            },
            keyboardActions = KeyboardActions {
                isPasswordVisible = !isPasswordVisible
            }
        )
    }
}