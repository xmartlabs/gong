package com.xmartlabs.template.device.di

import com.xmartlabs.template.domain.usecase.LoadUserUseCase
import com.xmartlabs.template.domain.usecase.LoadUserUseCaseImpl
import com.xmartlabs.template.domain.usecase.SignInUseCase
import com.xmartlabs.template.domain.usecase.SignInUseCaseImpl
import org.koin.dsl.module

/**
 * Created by mirland on 25/04/20.
 */
object UseCaseDiModule {
  val useCases = module {
    factory<SignInUseCase> { SignInUseCaseImpl(get()) }
    factory<LoadUserUseCase> { LoadUserUseCaseImpl(get()) }
  }
}
