package com.xmartlabs.gong.data.repository.store.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xmartlabs.gong.data.model.Project
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pablo on 24/03/23.
 */
@Dao
interface LocationDao {
    @Query("SELECT * FROM project")
    fun getLastLocation(): Flow<List<Project>>

    @Insert
    fun insert(project: Project)
}
