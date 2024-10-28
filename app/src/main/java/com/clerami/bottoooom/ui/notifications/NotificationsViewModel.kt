package com.clerami.bottoooom.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clerami.bottoooom.data.remote.response.ListEventsItem
import com.clerami.bottoooom.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch

class NotificationsViewModel : ViewModel() {
    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> get() = _upcomingEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchUpcomingEvents() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getEvents(1)
                _upcomingEvents.postValue(response.listEvents)
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue("Failed to load upcoming events")
            } finally {
                _isLoading.value = false
            }
        }
    }
}