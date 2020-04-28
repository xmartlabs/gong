package com.xmartlabs.template.domain.usecase

import com.xmartlabs.template.domain.usecase.common.FlowCoroutineUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

/**
 * Created by mirland on 27/04/20.
 */
@OptIn(ExperimentalTime::class)
interface TimeTrackerUseCase : FlowCoroutineUseCase<TimeTrackerUseCase.Params, Duration> {
  data class Params(val startTime: Date)
}

class TimeTrackerUseCaseImpl : TimeTrackerUseCase {
  @OptIn(ExperimentalTime::class)
  override fun execute(params: TimeTrackerUseCase.Params): Flow<Duration> = flow {
    while (true) {
      @Suppress("MagicNumber")
      delay(1800)
      val duration = (Date().time - params.startTime.time).milliseconds
      println("Duration: ${duration.inMilliseconds} in millis")
      emit(duration)
    }
  }
}
