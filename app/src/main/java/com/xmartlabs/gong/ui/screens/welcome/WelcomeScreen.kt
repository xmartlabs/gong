package com.xmartlabs.gong.ui.screens.welcome

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.accompanist.insets.statusBarsPadding
import com.xmartlabs.gong.R
import com.xmartlabs.gong.data.model.toShortString
import com.xmartlabs.gong.ui.Screens
import com.xmartlabs.gong.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

/**
 * Created by mirland on 25/04/20.
 */
@Composable
fun WelcomeScreen(navController: NavHostController) {
    val viewModel: WelcomeScreenViewModel = getViewModel()
    val state by viewModel.state.collectAsState()
    val event: WelcomeViewModelEvent? by viewModel.oneShotEvents.collectAsState(initial = null)

    WelcomeEvents(navController, event)
    WelcomeContent(
        userName = state.userName,
        locationString = state.location?.toShortString() ?: ""
    ) { viewModel.submitAction(WelcomeUiAction.Logout) }
}

@Composable
private fun WelcomeEvents(
    navController: NavHostController,
    event: WelcomeViewModelEvent?,
    context: Context = LocalContext.current,
) {
    // We only want the event stream to be attached once
    // even if there are multiple re-compositions
    LaunchedEffect(event) {
        when (event) {
            WelcomeViewModelEvent.NavigateToSignIn ->
                navController.navigate(Screens.SIGN_IN) {
                    popUpTo(Screens.WELCOME) { inclusive = true }
                }

            is WelcomeViewModelEvent.SignOutError -> showSignInError(event.throwable, context)
            else -> Unit
        }
    }
}

private fun showSignInError(throwable: Throwable, context: Context) {
    if (throwable is SecurityException) {
        // In a real project, the string should be defined as a string resource.
        Toast.makeText(
            context,
            "Logout error",
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
private fun WelcomeContentPreview(
    userName: String = "xmartlabs",
    locationString: String = "Uruguay",
) = WelcomeContent(userName, locationString, {})

@Composable
fun WelcomeContent(
    userName: String,
    locationString: String,
    onLogoutClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppTopBar(
                onLogoutClicked = onLogoutClicked,
            )
        },
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = "Hi $userName",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Text(
                text = "You signed in from: $locationString!",
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .alpha(if (locationString.isNotBlank()) 1f else 0f)
            )
        }
    }
}

@Preview
@Composable
fun WelcomePreview() {
    AppTheme {
        WelcomeContentPreview()
    }
}

@Preview
@Composable
fun WelcomePreviewDark() {
    AppTheme(darkTheme = true) {
        WelcomeContentPreview()
    }
}

@Composable
fun AppTopBar(modifier: Modifier = Modifier, onLogoutClicked: () -> Unit) {
    Column(modifier = modifier.statusBarsPadding()) {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            actions = {
                TextButton(
                    onClick = onLogoutClicked,
                    content = {
                        Text(
                            text = stringResource(id = R.string.logout),
                            style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onPrimary)
                        )
                    },
                )
            }
        )
    }
}
