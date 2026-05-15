package com.example.healthwealth.data.network

import android.util.Log
import com.example.healthwealth.data.MockDataProvider
import io.github.jan.supabase.postgrest.from

object SeedUtil {
    /**
     * Call this function ONCE to populate the Supabase tables
     * from the local MockDataProvider.
     * Make sure you have tables `shops` and `medicines` created in Supabase.
     */
    suspend fun seedDatabase() {
        try {
            // Seed Shops
            val shops = MockDataProvider.shops
            SupabaseNetwork.client.from("shops").insert(shops)
            Log.d("SeedUtil", "Shops seeded successfully")

            // Seed Medicines
            val medicines = MockDataProvider.medicines
            SupabaseNetwork.client.from("medicines").insert(medicines)
            Log.d("SeedUtil", "Medicines seeded successfully")

        } catch (e: Exception) {
            Log.e("SeedUtil", "Error seeding database: \${e.message}", e)
        }
    }
}
