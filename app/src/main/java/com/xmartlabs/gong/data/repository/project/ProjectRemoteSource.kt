package com.xmartlabs.gong.data.repository.project

import com.xmartlabs.gong.data.service.ProjectServiceApi

/**
 * Created by Pablo on 24/03/23.
 */
class ProjectRemoteSource(private val projectApi: ProjectServiceApi) {
    suspend fun getProjects() = projectApi.getProjects()
}
