package com.xmartlabs.gong.data.repository.project

import com.xmartlabs.gong.data.model.Project
import com.xmartlabs.gong.data.repository.store.db.LocationDao

/**
 * Created by Pablo on 24/03/23.
 */
class ProjectLocalSource(private val locationDao: LocationDao) {
    fun getProjects() = locationDao.getProjects()

    fun saveProjects(projects: List<Project>) = locationDao.insert(projects)
}
