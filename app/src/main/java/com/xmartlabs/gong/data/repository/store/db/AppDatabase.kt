package com.xmartlabs.gong.data.repository.store.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xmartlabs.gong.data.model.Project

/**
 * Created by Pablo on 24/03/23.
 */
@Database(entities = [Project::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
