package com.xmartlabs.gong.ui.screens.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xmartlabs.gong.R
import com.xmartlabs.gong.data.model.toShortString
import com.xmartlabs.gong.device.common.getOrNull
import com.xmartlabs.gong.ui.Themes
import org.koin.androidx.compose.getViewModel

/**
 * Created by mirland on 25/04/20.
 */
@Composable
fun WelcomeScreen() {
  val viewModel: WelcomeScreenViewModel = getViewModel()
  val userResult by viewModel.userLiveData.observeAsState()
  val locationResult by viewModel.locationLiveData.observeAsState()
  val location = locationResult?.getOrNull()
  val locationString = location?.toShortString() ?: ""
  Themes.GongTheme {
    WelcomeContent(
        userName = userResult?.getOrNull()?.name ?: "",
        locationString = locationString,
    )
  }
}

@Composable
fun WelcomeContent(
    userName: String = "xmartlabs",
    locationString: String = "Uruguay",
) {
  Scaffold(
      topBar = { GongTopBar() },
  ) {
    Surface {
      Column(
          verticalArrangement = Arrangement.Center,
          modifier = Modifier.fillMaxSize()
      ) {
        Text(
            text = "Hi $userName",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "You signed in from: $locationString!",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
      }
    }
  }
}

@Preview
@Composable
fun WelcomePreview() {
  Themes.GongTheme {
    WelcomeContent()
  }
}

@Preview
@Composable
fun WelcomePreviewDark() {
  Themes.GongTheme(darkTheme = true) {
    WelcomeContent()
  }
}

@Composable
fun GongTopBar() {
  TopAppBar(contentPadding = PaddingValues(top = 51.dp)) {
    Text(
        text = stringResource(id = R.string.app_name),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h6,
        modifier = Modifier
            .align(Alignment.CenterVertically)
            .fillMaxWidth()
    )
  }
}
