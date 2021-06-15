package com.xmartlabs.gong.data.model.service.settings

import com.xmartlabs.gong.data.model.User
import kotlinx.serialization.Serializable

/**
 * Created by mirland on 14/6/21.
 */
@Serializable
data class AppSettings(
    val sessionToken: String? = null,
    val sessionUser: User? = null,
)
