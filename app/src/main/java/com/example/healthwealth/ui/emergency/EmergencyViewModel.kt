package com.example.healthwealth.ui.emergency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.healthwealth.data.repository.MedicineRepository

class EmergencyViewModel : ViewModel() {

    private val repository = MedicineRepository()

    private val _emergencyDrugs = MutableLiveData<List<MedicineRepository.SearchResult>>()
    val emergencyDrugs: LiveData<List<MedicineRepository.SearchResult>> = _emergencyDrugs

    private val _summary = MutableLiveData<String>()
    val summary: LiveData<String> = _summary

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadEmergencyDrugs()
    }

    private fun loadEmergencyDrugs() {
        viewModelScope.launch {
            _isLoading.value = true
            val drugs = repository.getLifeSavingDrugs()
            _emergencyDrugs.value = drugs

            val inStock = drugs.count { it.medicine.stock > 0 }
            val shopCount = drugs.map { it.shop.id }.distinct().size
            _summary.value = "$inStock life-saving drugs available across $shopCount shops"
            _isLoading.value = false
        }
    }
}
