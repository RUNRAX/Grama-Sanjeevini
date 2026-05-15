package com.example.healthwealth.data.network

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseNetwork {
    private const val SUPABASE_URL = "https://mrtdgfkuosyqjzeyebkd.supabase.co"
    private const val SUPABASE_KEY = "sb_publishable_1q7cZpjFE7k9IvkkmMzlnA_FkotWq20"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
        install(Auth)
    }
}
