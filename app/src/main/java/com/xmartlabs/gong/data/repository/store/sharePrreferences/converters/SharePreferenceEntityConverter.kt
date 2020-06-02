package com.xmartlabs.gong.data.repository.store.sharePrreferences.converters

import androidx.annotation.WorkerThread

/**
 * Created by mirland on 03/05/20.
 */
interface SharePreferenceEntityConverter <T> {
  @WorkerThread
  fun toString(t: T): String
  @WorkerThread
  fun fromString(value: String?): T?
}
