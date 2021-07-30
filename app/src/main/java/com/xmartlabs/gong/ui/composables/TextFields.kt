package com.xmartlabs.gong.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.xmartlabs.gong.R
import com.xmartlabs.gong.ui.theme.AppTheme

@Preview
@Composable
fun RoundedCornersTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: @Composable () -> Unit = {},
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit = {},
) {
    TextField(
        value = value,
        label = label,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.body1,
        shape = AppTheme.shapes.roundedBox,
        colors = textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier.fillMaxWidth(),
    )
}

@Preview
@Composable
fun RoundedCornersPasswordTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: @Composable () -> Unit = {},
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit = {},
) {
    var passwordShown by remember { mutableStateOf(false) }
    TextField(
        value = value,
        label = label,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.body1,
        shape = AppTheme.shapes.roundedBox,
        colors = textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        visualTransformation = if (!passwordShown) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = singleLine,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconToggleButton(checked = passwordShown, onCheckedChange = { passwordShown = !passwordShown }) {
                Icon(
                    painter = painterResource(
                        id = if (!passwordShown) R.drawable.ic_show_password else R.drawable.ic_hide_password
                    ),
                    contentDescription = null,
                )
            }
        },
        modifier = modifier.fillMaxWidth(),
    )
}
