package com.clerami.bottoooom.ui.dashboard

import androidx.lifecycle.*
import com.clerami.bottoooom.data.remote.response.ListEventsItem
import com.clerami.bottoooom.data.remote.retrofit.ApiConfig

import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> get() = _finishedEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchFinishedEvents() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getEvents(0)
                _finishedEvents.postValue(response.listEvents)
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue("Failed to load finished events")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
