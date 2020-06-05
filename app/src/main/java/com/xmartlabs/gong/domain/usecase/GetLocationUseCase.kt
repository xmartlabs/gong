package com.xmartlabs.gong.domain.usecase

import com.xmartlabs.gong.data.model.Location
import com.xmartlabs.gong.domain.repository.LocationRepository
import com.xmartlabs.gong.domain.usecase.common.FlowCoroutineUseCase

/**
 * Created by mirland on 28/04/20.
 */
interface GetLocationUseCase : FlowCoroutineUseCase<GetLocationUseCase.Params, Location> {
  class Params
}

class GetLocationUseCaseImpl(private val locationRepository: LocationRepository) : GetLocationUseCase {
  override fun execute(params: GetLocationUseCase.Params) = locationRepository.getLocation()
}
