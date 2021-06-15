package com.xmartlabs.gong.device.di

import org.koin.core.module.Module

/**
 * Created by mirland on 25/05/20.
 */
object DiAppModules {
  private val modules: List<Module> =
      DispatchersDiModule.dispatchers +
          LoggingDiModule.logging +
          NetworkDiModule.network +
          RepositoryDiModuleProvider.repositories +
          RepositoryDiModuleProvider.sources +
          RepositoryDiModuleProvider.stores +
          UseCaseDiModule.useCases +
          ViewModelDiModule.viewModels

  fun provideModules() = modules
}
