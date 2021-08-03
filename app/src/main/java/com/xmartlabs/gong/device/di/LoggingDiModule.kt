package com.xmartlabs.gong.device.di

import com.xmartlabs.gong.LoggerTools
import org.koin.core.module.Module

/**
 * Created by mirland on 25/04/20.
 */
object LoggingDiModule {
    val logging: Module = LoggerTools.provideKoinDebugModule()
}
