package com.xmartlabs.template.data.model

/**
 * Created by mirland on 28/04/20.
 */
data class Location(
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
    val query: String
)
