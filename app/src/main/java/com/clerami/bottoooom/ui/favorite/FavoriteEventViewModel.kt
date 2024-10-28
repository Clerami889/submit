package com.clerami.bottoooom.ui.favorite

import androidx.lifecycle.*
import com.clerami.bottoooom.data.local.FavoriteRepository
import com.clerami.bottoooom.data.local.entity.FavoriteEvent
import kotlinx.coroutines.launch


class FavoriteEventViewModel(private val repository: FavoriteRepository) : ViewModel() {
    fun addFavorite(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            repository.insert(favoriteEvent)
        }
    }
}

class FavoriteEventViewModelFactory(private val repository: FavoriteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteEventViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
