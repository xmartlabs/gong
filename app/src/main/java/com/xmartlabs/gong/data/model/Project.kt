package com.xmartlabs.gong.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Pablo on 24/03/23.
 */
@Entity
@Serializable
data class Project(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val url: String,
    @SerialName("image_url")
    val imageUrl: String,
    val language: String,
)
