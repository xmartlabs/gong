package com.xmartlabs.gong.data.repository.location

import com.xmartlabs.gong.data.service.LocationServiceApi
import java.util.Date

/**
 * Created by mirland on 28/04/20.
 */
class LocationRemoteSource(private val locationApi: LocationServiceApi) {
  suspend fun getLocation() = locationApi.getLocation()
      .copy(timestamp = Date())
}
