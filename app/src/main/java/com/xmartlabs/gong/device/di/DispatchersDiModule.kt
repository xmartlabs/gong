package com.xmartlabs.gong.device.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

/**
 * Created by mirland on 04/08/20.
 */
val DEFAULT_DISPATCHER = StringQualifier("DEFAULT_DISPATCHER")
val IO_DISPATCHER = StringQualifier("IO_DISPATCHER")
val MAIN_DISPATCHER = StringQualifier("MAIN_DISPATCHER")

object DispatchersDiModule {
    val dispatchers = module {
    single(DEFAULT_DISPATCHER) { Dispatchers.Default }
    single(IO_DISPATCHER) { Dispatchers.IO }
    single(MAIN_DISPATCHER) { Dispatchers.Main }
  }
}
