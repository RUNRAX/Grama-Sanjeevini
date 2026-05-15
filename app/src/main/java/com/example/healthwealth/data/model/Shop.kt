package com.example.healthwealth.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Shop(
    val id: String = "",
    val name: String = "",
    val village: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    @SerialName("pharmacist_name") val pharmacistName: String = "",
    val phone: String = "",
    @SerialName("distance_km") val distanceKm: Double = 0.0 // computed at runtime or mocked
)
