package com.example.healthwealth.data.repository

import android.util.Log
import com.example.healthwealth.data.MockDataProvider
import com.example.healthwealth.data.model.ExpiryAlert
import com.example.healthwealth.data.model.Medicine
import com.example.healthwealth.data.model.Shop
import com.example.healthwealth.data.network.SupabaseNetwork
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for medicine and shop data.
 * Fetches from Supabase if configured, falls back to MockDataProvider on error or if empty.
 */
class MedicineRepository {

    data class SearchResult(
        val medicine: Medicine,
        val shop: Shop
    )

    private suspend fun fetchMedicines(): List<Medicine> = withContext(Dispatchers.IO) {
        try {
            val remote = SupabaseNetwork.client.from("medicines").select().decodeList<Medicine>()
            Log.d("MedicineRepository", "Remote medicines: ${remote.size}")
            if (remote.isEmpty()) {
                Log.d("MedicineRepository", "Supabase returned empty medicines, using mock")
                MockDataProvider.medicines
            } else {
                remote
            }
        } catch (e: Exception) {
            Log.e("MedicineRepository", "Failed to fetch medicines from Supabase, falling back to mock: ${e.message}", e)
            MockDataProvider.medicines
        }
    }

    private suspend fun fetchShops(): List<Shop> = withContext(Dispatchers.IO) {
        try {
            val remote = SupabaseNetwork.client.from("shops").select().decodeList<Shop>()
            Log.d("MedicineRepository", "Remote shops: ${remote.size}")
            if (remote.isEmpty()) {
                Log.d("MedicineRepository", "Supabase returned empty shops, using mock")
                MockDataProvider.shops
            } else {
                remote
            }
        } catch (e: Exception) {
            Log.e("MedicineRepository", "Failed to fetch shops from Supabase, falling back to mock: ${e.message}", e)
            MockDataProvider.shops
        }
    }

    /** Search medicines by name, generic name, or category across all shops */
    suspend fun searchMedicines(query: String, categoryId: String? = null): List<SearchResult> {
        val lowerQuery = query.lowercase()
        
        val medicines = fetchMedicines()
        val shops = fetchShops()
        
        return medicines
            .filter {
                (query.isBlank() || it.name.lowercase().contains(lowerQuery) || it.genericName.lowercase().contains(lowerQuery)) &&
                (categoryId == null || it.category.name == categoryId)
            }
            .mapNotNull { med ->
                shops.find { it.id == med.shopId }?.let { shop ->
                    SearchResult(med, shop)
                }
            }
            .sortedBy { it.shop.distanceKm }
    }

    /** Get all life-saving drugs with their shop info */
    suspend fun getLifeSavingDrugs(): List<SearchResult> {
        val medicines = fetchMedicines()
        val shops = fetchShops()
        
        return medicines
            .filter { it.isLifeSaving }
            .mapNotNull { med ->
                shops.find { it.id == med.shopId }?.let { shop ->
                    SearchResult(med, shop)
                }
            }
            .sortedWith(compareBy({ it.medicine.name }, { it.shop.distanceKm }))
    }

    /** Get medicines expiring within the given number of days */
    suspend fun getExpiryAlerts(withinDays: Int = 90): List<ExpiryAlert> {
        val now = System.currentTimeMillis()
        val cutoff = now + withinDays * 86_400_000L
        
        val medicines = fetchMedicines()
        val shops = fetchShops()
        
        return medicines
            .filter { it.expiryDate in (now + 1)..cutoff }
            .mapNotNull { med ->
                shops.find { it.id == med.shopId }?.let { shop ->
                    val daysLeft = ((med.expiryDate - now) / 86_400_000L).toInt()
                    val discount = when {
                        daysLeft < 7 -> 50
                        daysLeft < 30 -> 30
                        else -> 15
                    }
                    ExpiryAlert(med, shop, daysLeft, discount)
                }
            }
            .sortedBy { it.daysUntilExpiry }
    }

    /** Get all medicines for a specific shop */
    suspend fun getMedicinesForShop(shopId: String): List<Medicine> {
        return fetchMedicines().filter { it.shopId == shopId }
    }

    /** Get all shops */
    suspend fun getAllShops(): List<Shop> = fetchShops()

    /** Update stock for a specific medicine */
    suspend fun updateStock(medicineId: String, newStock: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            SupabaseNetwork.client.from("medicines")
                .update({ set("stock", newStock) }) {
                    filter { eq("id", medicineId) }
                }
            true
        } catch (e: Exception) {
            Log.e("MedicineRepository", "Failed to update stock", e)
            false
        }
    }
}
