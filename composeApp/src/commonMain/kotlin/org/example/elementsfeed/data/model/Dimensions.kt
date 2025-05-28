package org.example.elementsfeed.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Dimensions(
    val width: Double,
    val height: Double,
    val depth: Double
)
