package com.xmartlabs.gong.data.repository.store.db

import androidx.room.TypeConverter
import java.util.Date

/**
 * Created by mirland on 31/05/20.
 */
class RoomConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = if (value == null) null else Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}
