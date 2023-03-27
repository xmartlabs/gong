package com.xmartlabs.gong.ui.screens.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.statusBarsPadding
import com.xmartlabs.gong.R
import com.xmartlabs.gong.data.model.toShortString
import com.xmartlabs.gong.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

/**
 * Created by mirland on 25/04/20.
 */
@Composable
fun WelcomeScreen() {
    val viewModel: WelcomeScreenViewModel = getViewModel()
    val state by viewModel.state.collectAsState()
    WelcomeContent(
        userName = state.userName,
        locationString = state.location?.toShortString() ?: "",
    )
}

@Composable
private fun WelcomeContentPreview(
    userName: String = "xmartlabs",
    locationString: String = "Uruguay",
) = WelcomeContent(userName, locationString)

@Composable
fun WelcomeContent(
    userName: String,
    locationString: String,
) {
    Scaffold(
        topBar = { AppTopBar() },
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(padding)
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
fun AppTopBar(modifier: Modifier = Modifier) {
    Column(modifier = modifier.statusBarsPadding()) {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
        )
    }
}
