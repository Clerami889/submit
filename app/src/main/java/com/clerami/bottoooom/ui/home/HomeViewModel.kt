package com.clerami.bot.ui.home

import androidx.lifecycle.*

import com.clerami.bottoooom.data.remote.response.ListEventsItem
import com.clerami.bottoooom.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> get() = _upcomingEvents

    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> get() = _finishedEvents

    private val _isUpcomingLoading = MutableLiveData<Boolean>()
    val isUpcomingLoading: LiveData<Boolean> get() = _isUpcomingLoading

    private val _isFinishedLoading = MutableLiveData<Boolean>()
    val isFinishedLoading: LiveData<Boolean> get() = _isFinishedLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchUpcomingEvents() {
        _isUpcomingLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getEvents(1)
                _upcomingEvents.postValue(response.listEvents)
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue("Failed to load upcoming events")
            } finally {
                _isUpcomingLoading.value = false
            }
        }
    }

    fun fetchFinishedEvents() {
        _isFinishedLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getEvents(0)
                _finishedEvents.postValue(response.listEvents)
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue("Failed to load finished events")
            } finally {
                _isFinishedLoading.value = false
            }
        }
    }
}
