package com.xmartlabs.gong.domain.usecase

import com.xmartlabs.gong.data.model.Location
import com.xmartlabs.gong.domain.repository.LocationRepository
import com.xmartlabs.gong.domain.usecase.common.CoroutineUseCase

/**
 * Created by mirland on 28/04/20.
 */
interface GetLocationUseCase : CoroutineUseCase<GetLocationUseCase.Params, Location> {
  class Params
}

class GetLocationUseCaseImpl(private val locationRepository: LocationRepository) : GetLocationUseCase {
  override suspend fun execute(params: GetLocationUseCase.Params): Location = locationRepository.getLocation()
}
