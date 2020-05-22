package com.xmartlabs.gong.device.di

import com.xmartlabs.gong.domain.usecase.GetLocationUseCase
import com.xmartlabs.gong.domain.usecase.GetLocationUseCaseImpl
import com.xmartlabs.gong.domain.usecase.GetSessionTypeUseCase
import com.xmartlabs.gong.domain.usecase.GetSessionTypeUseCaseImpl
import com.xmartlabs.gong.domain.usecase.LoadUserUseCase
import com.xmartlabs.gong.domain.usecase.LoadUserUseCaseImpl
import com.xmartlabs.gong.domain.usecase.SignInUseCase
import com.xmartlabs.gong.domain.usecase.SignInUseCaseImpl
import com.xmartlabs.gong.domain.usecase.TimeTrackerUseCase
import com.xmartlabs.gong.domain.usecase.TimeTrackerUseCaseImpl
import org.koin.dsl.module

/**
 * Created by mirland on 25/04/20.
 */
object UseCaseDiModule {
  val useCases = module {
    factory<GetLocationUseCase> { GetLocationUseCaseImpl(get()) }
    factory<GetSessionTypeUseCase> { GetSessionTypeUseCaseImpl(get()) }
    factory<LoadUserUseCase> { LoadUserUseCaseImpl(get()) }
    factory<SignInUseCase> { SignInUseCaseImpl(get()) }
    factory<TimeTrackerUseCase> { TimeTrackerUseCaseImpl() }
  }
}
