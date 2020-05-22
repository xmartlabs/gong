package com.xmartlabs.gong.domain.repository

import com.xmartlabs.gong.data.repository.location.LocationRemoteSource

/**
 * Created by mirland on 28/04/20.
 */
class LocationRepository(private val locationRemoteSource: LocationRemoteSource) {
  suspend fun getLocation() = locationRemoteSource.getLocation()
}
