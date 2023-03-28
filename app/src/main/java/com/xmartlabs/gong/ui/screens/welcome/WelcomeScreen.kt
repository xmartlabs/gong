package com.xmartlabs.gong.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.xmartlabs.gong.R
import com.xmartlabs.gong.data.model.Project
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
        projects = state.projects,
    )
}
//
//@Composable
//private fun WelcomeContentPreview(
//    userName: String = "xmartlabs",
//    locationString: String = "Uruguay",
//) = WelcomeContent(userName, locationString)

@Composable
fun WelcomeContent(
    projects: List<Project>,
) {
    Scaffold(
        topBar = { AppTopBar() },
        content = { padding ->
            ViewContent(padding, projects)
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ViewContent(padding: PaddingValues, projects: List<Project>) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        LazyColumn {
            items(projects) { project ->
                val paddingModifier = Modifier.padding(10.dp)
                val uriHandler = LocalUriHandler.current
                Card(
                    elevation = 10.dp,
                    modifier = paddingModifier.fillMaxSize(),
                    onClick = { uriHandler.openUri(project.url) },
                ) {
                    Column {
                        Image(
                            rememberImagePainter(project.imageUrl),
                            contentDescription = null,
                            Modifier
                                .fillMaxSize()
                                .height(150.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Column(Modifier.fillMaxWidth()) {
                                // TITLE
                                Text(text = project.name, style = AppTheme.typography.h6)
                            }
                        }

                        Row(Modifier.padding(start = 16.dp, end = 24.dp, top = 5.dp)) {

                            // Description text
                            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                                Text(
                                    text = project.description,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    style = AppTheme.typography.body2,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun WelcomePreview() {
//    AppTheme {
//        WelcomeContentPreview()
//    }
}

@Preview
@Composable
fun WelcomePreviewDark() {
//    AppTheme(darkTheme = true) {
//        WelcomeContentPreview()
//    }
}

@Composable
fun AppTopBar(modifier: Modifier = Modifier) {
    Column(modifier = modifier.statusBarsPadding()) {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
        )
    }
}

@Composable
fun LoadImage(project: Project) {

}
