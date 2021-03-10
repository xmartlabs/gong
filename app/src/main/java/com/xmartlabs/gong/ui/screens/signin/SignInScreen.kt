package com.xmartlabs.gong.ui.screens.signin

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.xmartlabs.gong.device.common.getOrNull
import com.xmartlabs.gong.ui.Screens
import com.xmartlabs.gong.ui.common.extensions.observeResult
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.milliseconds

@Composable
fun SignInScreen(navController: NavHostController) {
  val viewModel: SignInScreenViewModel = viewModel()
  val user by viewModel.userLiveData.observeAsState(initial = "")
  val password by viewModel.passwordLiveData.observeAsState(initial = "")
  val time by viewModel.viewModelTime.observeAsState()
  val lifecycleOwner = LocalLifecycleOwner.current
  val context = LocalContext.current
  SignInContent(
      user = user,
      password = password,
      time = (time?.getOrNull() ?: 0L.milliseconds),
      onUserEdited = { viewModel.updateUser(it) },
      onPasswordEdited = { viewModel.updatePassword(it) },
      onSignInButtonClicked = {
        signIn(viewModel = viewModel,
            lifecycleOwner = lifecycleOwner,
            context = context,
            navController = navController)
      }
  )
}

@Suppress("LongMethod")
@Preview
@Composable
fun SignInContent(
    user: String = "xmartlabs",
    password: String = "xmartlabs",
    time: Duration = 0L.milliseconds,
    onUserEdited: (String) -> Unit = {},
    onPasswordEdited: (String) -> Unit = {},
    onSignInButtonClicked: () -> Unit = {},
) {
  Surface(color = Color.White) {
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
      val (hiAllTextView, userIdEditText, passwordEditText, signInButton, viewModelTimeTextView) = createRefs()

      Text(text = "Hi all",
          fontSize = 20.sp,
          modifier = Modifier.constrainAs(hiAllTextView) {
            end.linkTo(parent.end)
            start.linkTo(parent.start)
            top.linkTo(parent.top, margin = 150.dp)
          })
      TextField(
          value = user,
          label = { Text("Username") },
          onValueChange = onUserEdited,
          textStyle = TextStyle(fontSize = 20.sp),
          colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.Transparent),
          modifier = Modifier
              .constrainAs(userIdEditText) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(hiAllTextView.bottom)
              }
              .width(200.dp)
      )
      TextField(
          value = password,
          label = { Text("Password") },
          onValueChange = onPasswordEdited,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
          textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
          colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.Transparent),
          visualTransformation = PasswordVisualTransformation(),
          modifier = Modifier
              .constrainAs(passwordEditText) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(userIdEditText.bottom)
              }
              .width(200.dp)
      )
      Text(
          text = "View model time, ${time.inSeconds} seconds",
          modifier = Modifier.constrainAs(viewModelTimeTextView) {
            end.linkTo(parent.end, margin = 5.dp)
            bottom.linkTo(signInButton.top)
          }
      )
      Button(
          onClick = onSignInButtonClicked,
          colors = buttonColors(backgroundColor = colorResource(id = android.R.color.darker_gray)),
          modifier = Modifier
              .fillMaxWidth()
              .constrainAs(signInButton) {
                bottom.linkTo(parent.bottom, margin = 15.dp)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
              }
              .padding(10.dp)
      ) {
        Text(text = "Sign In".toUpperCase(Locale.ROOT), color = Color.White, textAlign = TextAlign.Center)
      }
    }
  }
}

private fun signIn(
    viewModel: SignInScreenViewModel,
    lifecycleOwner: LifecycleOwner,
    context: Context,
    navController: NavHostController,
) = with(viewModel) {
  signIn.observeResult(lifecycleOwner,
      onFailure = { throwable ->
        if (throwable is SecurityException) {
          Toast.makeText(
              context,
              "password or username is wrong, try with userId = 'xmartlabs', password 'xmartlabs'",
              Toast.LENGTH_SHORT
          ).show()
        }
      },
      onSuccess = {
        navController.navigate(Screens.WELCOME) {
          popUpTo(Screens.SIGN_IN) { inclusive = true }
        }
      }
  )
  viewModel.signIn()
}
