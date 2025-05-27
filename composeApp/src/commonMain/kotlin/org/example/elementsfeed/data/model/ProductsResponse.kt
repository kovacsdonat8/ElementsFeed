package org.example.elementsfeed.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
