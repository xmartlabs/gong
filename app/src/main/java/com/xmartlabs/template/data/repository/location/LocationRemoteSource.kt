package com.xmartlabs.template.data.repository.location

import com.xmartlabs.template.data.service.LocationServiceApi

/**
 * Created by mirland on 28/04/20.
 */
class LocationRemoteSource(private val locationApi: LocationServiceApi) {
  suspend fun getLocation() = locationApi.getLocation()
}
