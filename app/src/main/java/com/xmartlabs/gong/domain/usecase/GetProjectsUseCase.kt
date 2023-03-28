package com.xmartlabs.gong.domain.usecase

import com.dropbox.android.external.store4.StoreResponse
import com.xmartlabs.gong.data.model.Project
import com.xmartlabs.gong.domain.repository.ProjectRepository
import com.xmartlabs.gong.domain.usecase.common.FlowStoreCoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

/**
 * Created by mirland on 28/04/20.
 */
class GetProjectsUseCase(
    private val projectRepository: ProjectRepository,
    dispatcher: CoroutineDispatcher,
) : FlowStoreCoroutineUseCase<Unit, List<Project>>(dispatcher) {
    override fun execute(params: Unit): Flow<StoreResponse<List<Project>>> = projectRepository.getProjects()
}
