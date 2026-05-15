package com.example.healthwealth.ui.pharmacist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.healthwealth.data.MockDataProvider
import com.example.healthwealth.data.model.Medicine
import com.example.healthwealth.data.model.Shop
import com.example.healthwealth.data.repository.MedicineRepository

class PharmacistViewModel : ViewModel() {

    private val repository = MedicineRepository()

    private val _isLoggedIn = MutableLiveData(false)
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private val _loginError = MutableLiveData(false)
    val loginError: LiveData<Boolean> = _loginError

    private val _currentShop = MutableLiveData<Shop?>()
    val currentShop: LiveData<Shop?> = _currentShop

    private val _stockList = MutableLiveData<List<Medicine>>()
    val stockList: LiveData<List<Medicine>> = _stockList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Simple hardcoded auth for demo
    fun login(username: String, password: String) {
        if (username == "pharmacist" && password == "password") {
            _isLoggedIn.value = true
            _loginError.value = false
            viewModelScope.launch {
                _isLoading.value = true
                // Default to first shop
                val shops = repository.getAllShops()
                if (shops.isNotEmpty()) {
                    val shop = shops.first()
                    _currentShop.value = shop
                    loadStock(shop.id)
                }
                _isLoading.value = false
            }
        } else {
            _loginError.value = true
        }
    }

    fun logout() {
        _isLoggedIn.value = false
        _currentShop.value = null
        _stockList.value = emptyList()
    }

    private suspend fun loadStock(shopId: String) {
        _stockList.value = repository.getMedicinesForShop(shopId)
    }

    fun updateStock(medicineId: String, newStock: Int) {
        viewModelScope.launch {
            val success = repository.updateStock(medicineId, newStock)
            if (success) {
                val current = _stockList.value?.toMutableList() ?: return@launch
                val index = current.indexOfFirst { it.id == medicineId }
                if (index != -1) {
                    current[index] = current[index].copy(stock = maxOf(0, newStock))
                    _stockList.value = current
                }
            }
        }
    }
}
