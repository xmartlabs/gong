package com.xmartlabs.gong.domain.usecase.common

import com.xmartlabs.gong.device.common.ProcessResult
import com.xmartlabs.gong.device.common.ProcessState
import com.xmartlabs.gong.device.common.asProcessState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Created by mirland on 26/4/21.
 */
fun <T> Flow<T>.mapToProcessResult(): Flow<ProcessResult<T>> =
  map<T, ProcessResult<T>> { value -> ProcessState.Success(value) }
    .catch { error -> emit(ProcessState.Failure(error)) }

fun <T> Flow<T>.mapToProcessState(): Flow<ProcessState<T>> = mapToProcessResult()
  .map { it.asProcessState() }
  .onStart { emit(ProcessState.Loading) }

fun <T> Flow<ProcessResult<T>>.mapResultToProcessState(): Flow<ProcessState<T>> = map { it.asProcessState() }
  .onStart { emit(ProcessState.Loading) }
