package com.xmartlabs.gong.ui.screens.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.xmartlabs.gong.R
import com.xmartlabs.gong.data.model.toShortString
import com.xmartlabs.gong.device.common.getOrNull
import org.koin.androidx.compose.getViewModel

/**
 * Created by mirland on 25/04/20.
 */

@Composable
fun WelcomeScreen() {
  val viewModel: WelcomeScreenViewModel = getViewModel()
  Scaffold(
      topBar = { GongTopBar() },
  ) {
    Surface(color = colorResource(id = R.color.design_default_color_surface)) {
      Column(
          verticalArrangement = Arrangement.Center,
          modifier = Modifier.fillMaxSize()
      ) {
        val userResult by viewModel.userLiveData.observeAsState()
        Text(
            text = "Hi ${userResult?.getOrNull()?.name}",
            style = MaterialTheme.typography.h3.copy(
                fontSize = 40.sp,
                color = colorResource(id = android.R.color.darker_gray)
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        val locationResult by viewModel.locationLiveData.observeAsState()
        val location = locationResult?.getOrNull()
        val locationString = location?.toShortString() ?: ""
        Text(
            text = "You signed in from: $locationString!",
            style = MaterialTheme.typography.body2.copy(
                color = colorResource(id = android.R.color.darker_gray)
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
      }
    }
  }
}

@Preview
@Composable
fun WelcomePreview() {
  WelcomeScreen()
}

@Composable
fun GongTopBar() {
  TopAppBar(
      backgroundColor = colorResource(id = R.color.primaryColor),
      contentColor = colorResource(id = R.color.design_default_color_surface)
  ) {
    Text(
        text = stringResource(id = R.string.app_name),
        style = MaterialTheme.typography.body1,
        modifier = Modifier.align(Alignment.CenterVertically)
    )
  }
}
