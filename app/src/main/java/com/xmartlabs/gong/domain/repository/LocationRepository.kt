package com.xmartlabs.gong.domain.repository

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.xmartlabs.gong.data.model.Location
import com.xmartlabs.gong.data.repository.location.LocationLocalSource
import com.xmartlabs.gong.data.repository.location.LocationRemoteSource
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

/**
 * Created by mirland on 28/04/20.
 */
class LocationRepository(
  private val locationLocalSource: LocationLocalSource,
  private val locationRemoteSource: LocationRemoteSource,
) {
  private val locationStore = StoreBuilder
    .from(
      fetcher = Fetcher.of<Unit, Location> {
        Timber.d("New location is requested")
        locationRemoteSource.getLocation()
      },
      sourceOfTruth = SourceOfTruth.of(
        reader = { locationLocalSource.getLocation() },
        writer = { _, location -> locationLocalSource.saveLocation(location) },
      )
    )
    .build()

  fun getLocation(forceRefresh: Boolean = true): Flow<StoreResponse<Location>> =
    locationStore.stream(StoreRequest.cached(Unit, forceRefresh))
}
