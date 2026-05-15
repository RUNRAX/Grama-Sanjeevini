package com.example.healthwealth.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Medicine(
    val id: String = "",
    val name: String = "",
    @SerialName("generic_name") val genericName: String = "",
    val category: MedicineCategory = MedicineCategory.GENERAL,
    val price: Double = 0.0,
    val stock: Int = 0,
    @SerialName("expiry_date") val expiryDate: Long = 0L, // epoch millis
    @SerialName("shop_id") val shopId: String = "",
    val unit: String = "strip" // strip, vial, bottle, tablet
) {
    val isLifeSaving: Boolean get() = category == MedicineCategory.LIFE_SAVING
}

@Serializable
enum class MedicineCategory(val displayName: String, val icon: String) {
    @SerialName("LIFE_SAVING") LIFE_SAVING("Life Saving", "🆘"),
    GENERAL("General", "💊"),
    OTC("Over the Counter", "🏪"),
    ANTIBIOTIC("Antibiotic", "🦠"),
    CHRONIC("Chronic Care", "❤️"),
    FEVER_PAIN("Fever & Pain", "🌡️"),
    COUGH_COLD("Cough & Cold", "🤧"),
    STOMACH_UPSET("Stomach Upset", "🤢"),
    SKIN_CARE("Skin Care", "🧴"),
    VITAMINS("Vitamins", "🍎"),
    EYE_EAR("Eye & Ear Drops", "💧"),
    FIRST_AID("First Aid", "🩹")
}
