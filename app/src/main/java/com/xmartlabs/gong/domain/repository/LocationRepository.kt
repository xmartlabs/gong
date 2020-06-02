package com.xmartlabs.gong.domain.repository

import com.xmartlabs.gong.data.model.Location
import com.xmartlabs.gong.data.repository.location.LocationLocalSource
import com.xmartlabs.gong.data.repository.location.LocationRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import java.util.Date
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds
import kotlin.time.minutes

/**
 * Created by mirland on 28/04/20.
 */
@OptIn(ExperimentalTime::class)
class LocationRepository(
    private val locationLocalSource: LocationLocalSource,
    private val locationRemoteSource: LocationRemoteSource
) {
  companion object {
    private val REFRESH_LOCATION_DURATION = 1.minutes
  }

  private var lastRequestedLocation: Date? = null

  fun getLocation(): Flow<Location> {
    return if (shouldFetchNewLocation()) {
      val getNewLocationFlow = flow {
        val newLocation = locationRemoteSource.getLocation()
        locationLocalSource.saveLocation(newLocation)
        lastRequestedLocation = Date()
        emit(newLocation)
      }
      listOf(getNewLocationFlow, locationLocalSource.getLocation())
          .merge()
    } else {
      locationLocalSource.getLocation()
    }.filterNotNull()
  }

  private fun shouldFetchNewLocation(): Boolean {
    val previousRequest = lastRequestedLocation?.let { lastRequested -> Date() - lastRequested }
    return previousRequest == null || previousRequest > REFRESH_LOCATION_DURATION
  }

  private operator fun Date.minus(date: Date) = (time - date.time).milliseconds
}
