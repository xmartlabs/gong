package com.xmartlabs.gong.data.repository.store.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xmartlabs.gong.data.model.Location

/**
 * Created by mirland on 31/05/20.
 */
@Database(entities = [Location::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
  abstract fun locationDao(): LocationDao
}
