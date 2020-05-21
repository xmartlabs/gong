package com.xmartlabs.template.device.di

import android.content.Context
import com.google.gson.Gson
import com.xmartlabs.template.Config
import com.xmartlabs.template.data.repository.auth.UserLocalSource
import com.xmartlabs.template.data.repository.auth.UserRemoteSource
import com.xmartlabs.template.domain.repository.UserRepository
import com.xmartlabs.template.data.repository.location.LocationRemoteSource
import com.xmartlabs.template.domain.repository.LocationRepository
import com.xmartlabs.template.data.repository.session.SessionLocalSource
import com.xmartlabs.template.domain.repository.SessionRepository
import com.xmartlabs.template.data.repository.store.SharePreferenceStore
import com.xmartlabs.template.data.repository.store.SharePreferenceStoreImpl
import org.koin.dsl.module

/**
 * Created by mirland on 25/04/20.
 */
object RepositoryDiModuleProvider {
  val stores = module {
    single<SharePreferenceStore> {
      SharePreferenceStoreImpl(get<Context>().getSharedPreferences(Config.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE))
    }
  }
  val sources = module {
    single { LocationRemoteSource(get()) }
    single { UserLocalSource() }
    single { UserRemoteSource() }
    single { SessionLocalSource(get(), Gson()) }
  }
  val repositories = module {
    single { LocationRepository(get()) }
    single { UserRepository(get(), get(), get()) }
    single { SessionRepository(get()) }
  }
}
