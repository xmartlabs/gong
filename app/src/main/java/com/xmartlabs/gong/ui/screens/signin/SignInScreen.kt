package com.xmartlabs.gong.ui.screens.signin

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.xmartlabs.gong.ui.Screens
import com.xmartlabs.gong.ui.common.extensions.observeResult
import com.xmartlabs.gong.ui.composables.RoundedCornersPasswordTextField
import com.xmartlabs.gong.ui.composables.RoundedCornersTextField
import com.xmartlabs.gong.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel
import java.util.Locale

@Composable
fun SignInScreen(navController: NavHostController) {
  val viewModel: SignInScreenViewModel = getViewModel()
  val user by viewModel.userLiveData.observeAsState(initial = "")
  val password by viewModel.passwordLiveData.observeAsState(initial = "")
  val lifecycleOwner = LocalLifecycleOwner.current
  val context = LocalContext.current
  SignInContent(
      user = user,
      password = password,
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

@Composable
private fun SignInContentPreview(
    user: String = "xmartlabs",
    password: String = "xmartlabs",
) = SignInContent(user,
    password = password,
    onUserEdited = { },
    onPasswordEdited = { },
    onSignInButtonClicked = { }
)

@Preview
@Composable
private fun SignInLightPreview() {
  AppTheme {
    SignInContentPreview()
  }
}

@Preview
@Composable
fun SignInDarkPreview() {
  AppTheme(darkTheme = true) {
    SignInContent()
  }
}

@Suppress("LongMethod")
@Composable
fun SignInContent(
    user: String = "xmartlabs",
    password: String = "xmartlabs",
    onUserEdited: (String) -> Unit = {},
    onPasswordEdited: (String) -> Unit = {},
    onSignInButtonClicked: () -> Unit = {},
) {
  Scaffold {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp)
    ) {
      val (welcomeText, signInText, userIdEditText, passwordEditText, signInButton) = createRefs()
      Text(
          text = "Welcome",
          style = AppTheme.typography.subtitle1,
          modifier = Modifier.constrainAs(welcomeText) {
            bottom.linkTo(signInText.top)
            start.linkTo(signInText.start)
            top.linkTo(parent.top, margin = 150.dp)
          }
      )
      Text(
          text = "Sign in",
          style = MaterialTheme.typography.h2,
          modifier = Modifier.constrainAs(signInText) {
            start.linkTo(parent.start)
            top.linkTo(parent.top, margin = 150.dp)
          }
      )
      RoundedCornersTextField(
          value = user,
          label = { Text("Username") },
          onValueChange = onUserEdited,
          modifier = Modifier
              .constrainAs(userIdEditText) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(signInText.bottom, margin = 35.dp)
              }
      )
      RoundedCornersPasswordTextField(
          value = password,
          label = { Text("Password") },
          onValueChange = onPasswordEdited,
          modifier = Modifier
              .constrainAs(passwordEditText) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(userIdEditText.bottom, margin = 10.dp)
              }
      )
      Button(
          onClick = onSignInButtonClicked,
          modifier = Modifier
              .fillMaxWidth()
              .padding(top = 20.dp, bottom = 20.dp)
              .constrainAs(signInButton) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom, margin = 68.dp)
              }
      ) {
        Text(
            text = "Sign In".toUpperCase(Locale.ROOT),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.button,
        )
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
