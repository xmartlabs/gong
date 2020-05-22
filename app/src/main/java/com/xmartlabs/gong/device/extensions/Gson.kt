package com.xmartlabs.gong.device.extensions

import com.google.gson.Gson

/**
 * Created by mirland on 03/05/20.
 */
inline fun <reified T> Gson.fromJson(json: String?): T = this.fromJson<T>(json, T::class.java)
