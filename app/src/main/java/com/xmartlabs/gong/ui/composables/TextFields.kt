package com.xmartlabs.gong.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.xmartlabs.gong.ui.GongColors
import com.xmartlabs.gong.ui.Shapes

@Preview
@Composable
fun RoundedCornersTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: @Composable () -> Unit = {},
    onValueChange: (String) -> Unit = {},
) {
  TextField(
      value = value,
      label = label,
      onValueChange = onValueChange,
      textStyle = MaterialTheme.typography.body1,
      shape = Shapes.roundedBox,
      colors = textFieldColors(
          focusedIndicatorColor = GongColors.transparent,
          unfocusedIndicatorColor = GongColors.transparent,
      ),
      modifier = modifier.fillMaxWidth(),
  )
}

@Preview
@Composable
fun RoundedCornersPasswordTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: @Composable () -> Unit = {},
    onValueChange: (String) -> Unit = {},
) {
  TextField(
      value = value,
      label = label,
      onValueChange = onValueChange,
      textStyle = MaterialTheme.typography.body1,
      shape = Shapes.roundedBox,
      colors = textFieldColors(
          focusedIndicatorColor = GongColors.transparent,
          unfocusedIndicatorColor = GongColors.transparent,
      ),
      visualTransformation = PasswordVisualTransformation(),
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
      modifier = modifier.fillMaxWidth(),
  )
}
