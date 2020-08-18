package com.xmartlabs.gong.domain.usecase

import com.xmartlabs.gong.data.model.Location
import com.xmartlabs.gong.domain.repository.LocationRepository
import com.xmartlabs.gong.domain.usecase.common.FlowCoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

/**
 * Created by mirland on 28/04/20.
 */
class GetLocationUseCase(
    private val locationRepository: LocationRepository,
    dispatcher: CoroutineDispatcher
) : FlowCoroutineUseCase<Unit, Location>(dispatcher) {
  override fun execute(params: Unit): Flow<Location> = locationRepository.getLocation()
}
