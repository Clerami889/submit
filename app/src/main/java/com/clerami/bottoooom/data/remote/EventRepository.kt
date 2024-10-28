package com.clerami.bottoooom.data.remote



import com.clerami.bottoooom.data.remote.response.ListEventsItem
import com.clerami.bottoooom.data.remote.retrofit.ApiConfig
import com.clerami.bottoooom.utils.Result


class RemoteRepository private constructor() {
    private val apiService = ApiConfig.getApiService()

    suspend fun getEvents(isActive: Int): Result<List<ListEventsItem>> {
        return try {
            val response = apiService.getEvents(isActive)
            Result.Success(response.listEvents)
        } catch (e: Exception) {
            Result.Error(e.toString())
        }
    }

    suspend fun searchEvents(keyword: String): Result<List<ListEventsItem>> {
        return try {
            val response = apiService.searchEvents(keyword)
            Result.Success(response.listEvents)
        } catch (e: Exception) {
            Result.Error(e.toString())
        }
    }

    companion object {
        @Volatile
        private var instance: RemoteRepository? = null

        fun getInstance(): RemoteRepository =
            instance ?: synchronized(this) {
                instance ?: RemoteRepository().also { instance = it }
            }
    }
}