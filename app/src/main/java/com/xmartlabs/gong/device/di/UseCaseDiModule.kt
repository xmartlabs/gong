package com.xmartlabs.gong.device.di

import com.xmartlabs.gong.domain.usecase.GetProjectsUseCase
import com.xmartlabs.gong.domain.usecase.GetSessionTypeUseCase
import com.xmartlabs.gong.domain.usecase.LoadUserUseCase
import com.xmartlabs.gong.domain.usecase.SignInUseCase
import com.xmartlabs.gong.domain.usecase.TimeTrackerUseCase
import org.koin.dsl.module

/**
 * Created by mirland on 25/04/20.
 */
object UseCaseDiModule {
    val useCases = module {
        factory { GetProjectsUseCase(get(), get(DEFAULT_DISPATCHER)) }
        factory { GetSessionTypeUseCase(get(), get(DEFAULT_DISPATCHER)) }
        factory { LoadUserUseCase(get(), get(DEFAULT_DISPATCHER)) }
        factory { SignInUseCase(get(), get(DEFAULT_DISPATCHER)) }
        factory { TimeTrackerUseCase(get(DEFAULT_DISPATCHER)) }
    }
}
