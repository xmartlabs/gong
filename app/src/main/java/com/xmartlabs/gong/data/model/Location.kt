package com.xmartlabs.gong.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Created by mirland on 28/04/20.
 */
@Entity
data class Location(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val status: String,
    val country: String,
    val countryCode: String,
    val region: String,
    val regionName: String,
    val city: String,
    val zip: Int,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val isp: String,
    val org: String,
    val query: String,
    val timestamp: Date = Date()
)
