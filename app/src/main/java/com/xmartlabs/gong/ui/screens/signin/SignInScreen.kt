package com.xmartlabs.gong.ui.screens.signin

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.xmartlabs.gong.ui.Screens
import com.xmartlabs.gong.ui.composables.RoundedCornersPasswordTextField
import com.xmartlabs.gong.ui.composables.RoundedCornersTextField
import com.xmartlabs.gong.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel
import java.util.Locale

@Composable
fun SignInScreen(navController: NavHostController) {
    val viewModel: SignInScreenViewModel = getViewModel()
    val state by viewModel.state.collectAsState()
    val event: SignInViewModelEvent? by viewModel.oneShotEvents.collectAsState(initial = null)

    SignInEvents(navController, event)
    SignInContent(
        user = state.userName,
        password = state.password,
        onUserEdited = { viewModel.submitAction(SignInUiAction.ChangeUserName(it)) },
        onPasswordEdited = { viewModel.submitAction(SignInUiAction.ChangePassword(it)) },
        onSignInButtonClicked = { viewModel.submitAction(SignInUiAction.SignIn) }
    )
}

@Composable
private fun SignInEvents(
    navController: NavHostController,
    event: SignInViewModelEvent?,
    context: Context = LocalContext.current,
) {
    // We only want the event stream to be attached once
    // even if there are multiple re-compositions
    LaunchedEffect(event) {
        when (event) {
            SignInViewModelEvent.NavigateToDashboard ->
                navController.navigate(Screens.WELCOME) {
                    popUpTo(Screens.SIGN_IN) { inclusive = true }
                }
            is SignInViewModelEvent.SignInError -> showSignInError(event.throwable, context)
            else -> Unit
        }
    }
}

private fun showSignInError(throwable: Throwable, context: Context) {
    if (throwable is SecurityException) {
        // In a real project, the string should be defined as a string resource.
        Toast.makeText(
            context,
            "password or username is wrong, try with userId = 'xmartlabs', password 'xmartlabs'",
            Toast.LENGTH_SHORT
        ).show()
    }
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
        SignInContentPreview()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Suppress("LongMethod")
@Composable
fun SignInContent(
    user: String,
    password: String,
    onUserEdited: (String) -> Unit,
    onPasswordEdited: (String) -> Unit,
    onSignInButtonClicked: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
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
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                ),
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
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                modifier = Modifier
                    .focusRequester(focusRequester)
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
                    text = "Sign In".uppercase(Locale.ROOT),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
}
