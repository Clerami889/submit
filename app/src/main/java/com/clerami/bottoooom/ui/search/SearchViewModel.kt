package com.clerami.bottoooom.ui.search

import androidx.lifecycle.*
import com.clerami.bottoooom.data.remote.retrofit.ApiConfig
import com.clerami.bottoooom.data.remote.response.ListEventsItem
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _searchResults = MutableLiveData<List<ListEventsItem>>()
    val searchResults: LiveData<List<ListEventsItem>> get() = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchSearchResults(keyword: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().searchEvents(keyword)
                _searchResults.postValue(response.listEvents)
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue("Failed to load search results")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
