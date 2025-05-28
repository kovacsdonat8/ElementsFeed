package org.example.elementsfeed.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val createdAt: String,
    val updatedAt: String,
    val barCode: String? = null,
    val qrCode: String? = null,
)
