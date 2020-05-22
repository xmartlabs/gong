package com.xmartlabs.template.domain.repository

import com.xmartlabs.template.data.repository.location.LocationRemoteSource

/**
 * Created by mirland on 28/04/20.
 */
class LocationRepository(private val locationRemoteSource: LocationRemoteSource) {
  suspend fun getLocation() = locationRemoteSource.getLocation()
}
