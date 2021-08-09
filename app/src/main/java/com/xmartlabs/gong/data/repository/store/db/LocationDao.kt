package com.xmartlabs.gong.data.repository.store.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xmartlabs.gong.data.model.Location
import kotlinx.coroutines.flow.Flow

/**
 * Created by mirland on 31/05/20.
 */
@Dao
interface LocationDao {
    @Query("SELECT * FROM location ORDER BY timestamp DESC LIMIT 1")
    fun getLastLocation(): Flow<Location?>

    @Insert
    fun insert(location: Location)
}
