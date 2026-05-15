package com.example.healthwealth.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthwealth.data.repository.MedicineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = MedicineRepository()

    private val _searchResults = MutableStateFlow<List<MedicineRepository.SearchResult>>(emptyList())
    val searchResults: StateFlow<List<MedicineRepository.SearchResult>> = _searchResults.asStateFlow()

    private val _resultCount = MutableStateFlow(0)
    val resultCount: StateFlow<Int> = _resultCount.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private var currentQuery = ""

    fun search(query: String) {
        currentQuery = query
        executeSearch()
    }

    fun setCategory(categoryId: String?) {
        _selectedCategory.value = categoryId
        executeSearch()
    }

    private fun executeSearch() {
        viewModelScope.launch {
            _isLoading.value = true
            val results = repository.searchMedicines(currentQuery, _selectedCategory.value)
            _searchResults.value = results
            _resultCount.value = results.size
            _isLoading.value = false
        }
    }

    fun clearResults() {
        currentQuery = ""
        _selectedCategory.value = null
        _searchResults.value = emptyList()
        _resultCount.value = 0
    }
}
