package com.xmartlabs.gong.device.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.xmartlabs.gong.Config
import com.xmartlabs.gong.data.repository.auth.UserLocalSource
import com.xmartlabs.gong.data.repository.auth.UserRemoteSource
import com.xmartlabs.gong.data.repository.location.LocationLocalSource
import com.xmartlabs.gong.data.repository.location.LocationRemoteSource
import com.xmartlabs.gong.data.repository.session.SessionLocalSource
import com.xmartlabs.gong.data.repository.store.db.AppDatabase
import com.xmartlabs.gong.data.repository.store.sharePrreferences.SharePreferenceStore
import com.xmartlabs.gong.data.repository.store.sharePrreferences.SharePreferenceStoreImpl
import com.xmartlabs.gong.domain.repository.LocationRepository
import com.xmartlabs.gong.domain.repository.SessionRepository
import com.xmartlabs.gong.domain.repository.UserRepository
import org.koin.dsl.module

/**
 * Created by mirland on 25/04/20.
 */
object RepositoryDiModuleProvider {
  val stores = module {
    single<SharePreferenceStore> {
      SharePreferenceStoreImpl(get<Context>().getSharedPreferences(Config.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE))
    }
    single {
      Room.databaseBuilder(get(), AppDatabase::class.java, Config.DB_NAME)
          .build()
    }
    single { get<AppDatabase>().locationDao() }
  }
  val sources = module {
    single { LocationRemoteSource(get()) }
    single { LocationLocalSource(get()) }
    single { UserLocalSource() }
    single { UserRemoteSource() }
    single { SessionLocalSource(get(), Gson()) }
  }
  val repositories = module {
    single { LocationRepository(get(), get()) }
    single { UserRepository(get(), get(), get()) }
    single { SessionRepository(get()) }
  }
}
