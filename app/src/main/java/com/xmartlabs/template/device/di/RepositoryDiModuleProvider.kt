package com.xmartlabs.template.device.di

import com.xmartlabs.template.data.repository.auth.UserLocalSource
import com.xmartlabs.template.data.repository.auth.UserRemoteSource
import com.xmartlabs.template.data.repository.auth.UserRepository
import org.koin.dsl.module

/**
 * Created by mirland on 25/04/20.
 */
object RepositoryDiModuleProvider {
  val repositories = module {
    single { UserRepository(UserLocalSource(), UserRemoteSource()) }
  }
}
