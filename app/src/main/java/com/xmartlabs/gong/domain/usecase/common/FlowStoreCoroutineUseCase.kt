package com.xmartlabs.gong.domain.usecase.common

import com.dropbox.android.external.store4.StoreResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by mirland on 11/6/21.
 */
abstract class FlowStoreCoroutineUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(params: P): Flow<StoreResponse<R>> = execute(params)
        .flowOn(coroutineDispatcher)

    fun invokeAsProcessResult(params: P) = execute(params)
        .mapToProcessResult()

    /**
     * Override this to set the code to be executed.
     */
    abstract fun execute(params: P): Flow<StoreResponse<R>>
}
