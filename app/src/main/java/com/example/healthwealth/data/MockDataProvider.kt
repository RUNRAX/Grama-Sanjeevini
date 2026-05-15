package com.example.healthwealth.data

import com.example.healthwealth.data.model.Medicine
import com.example.healthwealth.data.model.MedicineCategory
import com.example.healthwealth.data.model.Shop
import kotlin.random.Random

/**
 * Provides hardcoded mock data for 100 village pharmacies and 150+ medicines.
 * This simulates a Firebase Firestore backend for demo purposes.
 */
object MockDataProvider {

    private val villageNames = listOf(
        "Hosahalli", "Dodderi", "Kanivenarayanapura", "Thalagavara", "Bellur",
        "Amani Byrathasandra", "Melur", "Sidlaghatta", "Vijayapura", "Jangamakote",
        "Devanahalli", "Channasandra", "Bidanuru", "Hulivana", "Mandya",
        "Maddur", "Koppa", "Malavalli", "Pandavapura", "Srirangapatna"
    )

    private val shopPrefixes = listOf(
        "Sanjeevini", "Lakshmi", "Gowda", "Sri Venkateshwara", "Arogya",
        "Vikas", "New Life", "Healing Touch", "Village Care", "People's",
        "Community", "Green Cross", "Healthy", "Care Plus", "Global"
    )

    private val shopSuffixes = listOf(
        "Medicals", "Pharmacy", "Medical Store", "Drug House", "Pharma",
        "Health Center", "Clinic & Medicals", "General Store & Pharmacy"
    )

    val shops: List<Shop> = (1..100).map { i ->
        val village = villageNames.random()
        val name = "${shopPrefixes.random()} ${shopSuffixes.random()}"
        Shop(
            id = "shop_$i",
            name = name,
            village = village,
            lat = 12.0 + Random.nextDouble(2.0),
            lng = 77.0 + Random.nextDouble(2.0),
            pharmacistName = "Pharmacist $i",
            phone = "9${Random.nextInt(100000000, 999999999)}",
            distanceKm = Random.nextDouble(0.5, 25.0)
        )
    }

    private val now = System.currentTimeMillis()
    private val day = 86_400_000L

    private val commonMeds = listOf(
        Triple("Paracetamol 500mg", "Acetaminophen", MedicineCategory.FEVER_PAIN),
        Triple("Dolo 650", "Paracetamol 650mg", MedicineCategory.FEVER_PAIN),
        Triple("Combiflam", "Ibuprofen + Paracetamol", MedicineCategory.FEVER_PAIN),
        Triple("Vicks Action 500", "Paracetamol + Phenylephrine", MedicineCategory.COUGH_COLD),
        Triple("Ascoril LS", "Ambroxol + Levosalbutamol", MedicineCategory.COUGH_COLD),
        Triple("Zentel", "Albendazole", MedicineCategory.STOMACH_UPSET),
        Triple("Digene", "Antacid", MedicineCategory.STOMACH_UPSET),
        Triple("Pudin Hara", "Herbal Extract", MedicineCategory.STOMACH_UPSET),
        Triple("Betadine", "Povidone-Iodine", MedicineCategory.FIRST_AID),
        Triple("Band-Aid", "Adhesive Bandage", MedicineCategory.FIRST_AID),
        Triple("Dettol", "Antiseptic", MedicineCategory.FIRST_AID),
        Triple("Becosules", "Vitamin B Complex", MedicineCategory.VITAMINS),
        Triple("Revital", "Multivitamin", MedicineCategory.VITAMINS),
        Triple("Limcee", "Vitamin C", MedicineCategory.VITAMINS),
        Triple("Amoxicillin 500mg", "Amoxicillin", MedicineCategory.ANTIBIOTIC),
        Triple("Azithromycin 500mg", "Azithromycin", MedicineCategory.ANTIBIOTIC),
        Triple("Insulin", "Insulin Glargine", MedicineCategory.LIFE_SAVING),
        Triple("ORS", "WHO Formula", MedicineCategory.LIFE_SAVING),
        Triple("Amlodipine 5mg", "Amlodipine Besylate", MedicineCategory.CHRONIC),
        Triple("Metformin 500mg", "Metformin HCl", MedicineCategory.CHRONIC)
    )

    val medicines: List<Medicine> = (1..200).map { i ->
        val shop = shops.random()
        val medBase = commonMeds.random()
        Medicine(
            id = "med_$i",
            name = medBase.first,
            genericName = medBase.second,
            category = medBase.third,
            price = Random.nextDouble(10.0, 500.0),
            stock = Random.nextInt(0, 50),
            expiryDate = now + Random.nextLong(30 * day, 730 * day),
            shopId = shop.id,
            unit = listOf("strip", "vial", "bottle", "tablet").random()
        )
    }

    fun getShopById(shopId: String): Shop? = shops.find { it.id == shopId }
}
