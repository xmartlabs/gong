package com.xmartlabs.template.device.di

import com.xmartlabs.template.domain.usecase.GetLocationUseCase
import com.xmartlabs.template.domain.usecase.GetLocationUseCaseImpl
import com.xmartlabs.template.domain.usecase.LoadUserUseCase
import com.xmartlabs.template.domain.usecase.LoadUserUseCaseImpl
import com.xmartlabs.template.domain.usecase.SignInUseCase
import com.xmartlabs.template.domain.usecase.SignInUseCaseImpl
import com.xmartlabs.template.domain.usecase.TimeTrackerUseCase
import com.xmartlabs.template.domain.usecase.TimeTrackerUseCaseImpl
import org.koin.dsl.module

/**
 * Created by mirland on 25/04/20.
 */
object UseCaseDiModule {
  val useCases = module {
    factory<GetLocationUseCase> { GetLocationUseCaseImpl(get()) }
    factory<LoadUserUseCase> { LoadUserUseCaseImpl(get()) }
    factory<SignInUseCase> { SignInUseCaseImpl(get()) }
    factory<TimeTrackerUseCase> { TimeTrackerUseCaseImpl() }
  }
}
