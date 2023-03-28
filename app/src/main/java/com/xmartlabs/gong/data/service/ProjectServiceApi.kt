package com.xmartlabs.gong.data.service

import com.xmartlabs.gong.data.model.Project
import retrofit2.http.GET

/**
 * Created by Pablo on 24/03/23.
 */
// API should be declared here
interface ProjectServiceApi {
    companion object {
        private const val URL_GET_PROJECTS = "rest/v1/projects?select=*"
    }

    @GET(URL_GET_PROJECTS)
    suspend fun getProjects(): List<Project>
}
