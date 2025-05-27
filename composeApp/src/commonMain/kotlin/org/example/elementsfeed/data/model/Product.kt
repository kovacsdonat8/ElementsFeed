package org.example.elementsfeed.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val description: String,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val tags: List<String>,
    val brand: String,
    val sku: String,
    val weight: Int,
    val dimensions: List<Double>,
    val warrantyInformation: String,
    val shippingInformation: String,
    val availabilityStatus: String,
    val reviews: List<Review>,
    val returnPolicy: String,
    val minimumOrderQuantity: Int,
    val meta: Meta,
    val images: List<String>,
    val thumbnail: String
)