package com.xmartlabs.template.domain.usecase

import com.xmartlabs.template.data.model.Location
import com.xmartlabs.template.data.repository.location.LocationRepository
import com.xmartlabs.template.domain.usecase.common.CoroutineUseCase

/**
 * Created by mirland on 28/04/20.
 */
interface GetLocationUseCase : CoroutineUseCase<GetLocationUseCase.Params, Location> {
  class Params
}

class GetLocationUseCaseImpl(private val locationRepository: LocationRepository) : GetLocationUseCase {
  override suspend fun execute(params: GetLocationUseCase.Params): Location = locationRepository.getLocation()
}
