package com.example.healthwealth.ui.expiry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.healthwealth.data.model.ExpiryAlert
import com.example.healthwealth.data.repository.MedicineRepository

class ExpiryViewModel : ViewModel() {

    private val repository = MedicineRepository()

    private val _expiryAlerts = MutableLiveData<List<ExpiryAlert>>()
    val expiryAlerts: LiveData<List<ExpiryAlert>> = _expiryAlerts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadExpiryAlerts()
    }

    private fun loadExpiryAlerts() {
        viewModelScope.launch {
            _isLoading.value = true
            _expiryAlerts.value = repository.getExpiryAlerts(90)
            _isLoading.value = false
        }
    }
}
