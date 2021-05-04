package com.xmartlabs.gong.device.di

/**
 * Created by mirland on 25/05/20.
 */
object DiAppModules {
  private val modules = listOf(
      DispatchersDiModule.dispatchers,
      NetworkDiModule.network,
      RepositoryDiModuleProvider.repositories,
      RepositoryDiModuleProvider.sources,
      RepositoryDiModuleProvider.stores,
      UseCaseDiModule.useCases,
      ViewModelDiModule.viewModels,
  )

  fun provideModules() = modules
}
