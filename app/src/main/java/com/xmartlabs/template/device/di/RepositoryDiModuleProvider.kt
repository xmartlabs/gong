package com.xmartlabs.template.device.di

import com.xmartlabs.template.data.repository.auth.UserLocalSource
import com.xmartlabs.template.data.repository.auth.UserRemoteSource
import com.xmartlabs.template.data.repository.auth.UserRepository
import com.xmartlabs.template.data.repository.location.LocationRemoteSource
import com.xmartlabs.template.data.repository.location.LocationRepository
import org.koin.dsl.module

/**
 * Created by mirland on 25/04/20.
 */
object RepositoryDiModuleProvider {
  val sources = module {
    single { LocationRemoteSource(get()) }
    single { UserLocalSource() }
    single { UserRemoteSource() }
  }
  val repositories = module {
    single { LocationRepository(get()) }
    single { UserRepository(get(), get()) }
  }
}
