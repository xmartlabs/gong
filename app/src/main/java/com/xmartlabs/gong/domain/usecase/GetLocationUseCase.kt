package com.xmartlabs.gong.domain.usecase

import com.dropbox.android.external.store4.StoreResponse
import com.xmartlabs.gong.data.model.Location
import com.xmartlabs.gong.domain.repository.LocationRepository
import com.xmartlabs.gong.domain.usecase.common.FlowStoreCoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

/**
 * Created by mirland on 28/04/20.
 */
class GetLocationUseCase(
    private val locationRepository: LocationRepository,
    dispatcher: CoroutineDispatcher
) : FlowStoreCoroutineUseCase<Unit, Location>(dispatcher) {
  override fun execute(params: Unit): Flow<StoreResponse<Location>> = locationRepository.getLocation()
}
