@file:OptIn(ExperimentalCoilApi::class)

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
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
private fun ViewContent(padding: PaddingValues, projects: List<Project>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        items(projects) { project ->
            ProjectComposable(project)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun ProjectComposable(project: Project) {
    val uriHandler = LocalUriHandler.current
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        onClick = { uriHandler.openUri(project.url) },
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(project.imageUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.FillHeight
        )
        Column {
            Image(
                painter,
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
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(text = project.name, style = AppTheme.typography.h6)
            }

            Row(Modifier.padding(start = 16.dp, end = 24.dp, top = 5.dp, bottom = 10.dp)) {
                Text(
                    text = project.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.subtitleTextColor,
                )
            }
        }
    }
}

@Composable
private fun WelcomeContentPreview() = WelcomeContent(
    listOf(
        Project(
            id = 1,
            name = "Gong",
            description = "Xmartlabs' Android Base Project Template",
            url = "https://github.com/xmartlabs/gong",
            imageUrl = "https://kytmgsnkaxgrjgckdmnv.supabase.co/storage/v1/object/public/images/banner_gong.png?t=" +
                    "2022-08-31T18%3A44%3A53.317Z",
            language = "kotlin",
        )
    )
)

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
