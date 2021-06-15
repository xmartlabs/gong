package com.xmartlabs.gong.data.model

import kotlinx.serialization.Serializable

/**
 * Created by mirland on 25/04/20.
 */
@Serializable
data class User(val id: String, val name: String, val email: String)
