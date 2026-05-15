package com.example.healthwealth.data.model

data class ExpiryAlert(
    val medicine: Medicine,
    val shop: Shop,
    val daysUntilExpiry: Int,
    val suggestedDiscount: Int // percentage
) {
    val urgency: ExpiryUrgency
        get() = when {
            daysUntilExpiry < 7 -> ExpiryUrgency.CRITICAL
            daysUntilExpiry < 30 -> ExpiryUrgency.WARNING
            else -> ExpiryUrgency.WATCH
        }
}

enum class ExpiryUrgency(val label: String, val colorHex: String) {
    CRITICAL("Critical", "#D32F2F"),
    WARNING("Warning", "#FF9800"),
    WATCH("Watch", "#FFC107")
}
