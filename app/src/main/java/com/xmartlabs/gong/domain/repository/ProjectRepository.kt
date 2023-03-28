package com.xmartlabs.gong.domain.repository

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.xmartlabs.gong.data.model.Project
import com.xmartlabs.gong.data.repository.project.ProjectLocalSource
import com.xmartlabs.gong.data.repository.project.ProjectRemoteSource
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

/**
 * Created by mirland on 28/04/20.
 */
class ProjectRepository(
    private val projectLocalSource: ProjectLocalSource,
    private val projectRemoteSource: ProjectRemoteSource,
) {
    private val projectStore = StoreBuilder
        .from(
            fetcher = Fetcher.of<Unit, List<Project>> {
                Timber.d("Project is requested")
                val a = projectRemoteSource.getProjects()
                Timber.d("Project is requested")
                Timber.d("Project is requested")
a
            },
//            sourceOfTruth = SourceOfTruth.of(
//                reader = { projectLocalSource.getProjects() },
//                writer = { _, value -> projectLocalSource.replaceProjects(value) },
//            )
        )
        .build()

    fun getProjects(forceRefresh: Boolean = true): Flow<StoreResponse<List<Project>>> =
        projectStore.stream(StoreRequest.cached(Unit, forceRefresh))
}
