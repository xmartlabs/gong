package com.xmartlabs.template.data.repository.location

/**
 * Created by mirland on 28/04/20.
 */
class LocationRepository(private val locationRemoteSource: LocationRemoteSource) {
  suspend fun getLocation() = locationRemoteSource.getLocation()
}
