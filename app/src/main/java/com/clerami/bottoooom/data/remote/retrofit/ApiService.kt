package com.clerami.bot.data.retrofit


import com.clerami.bottoooom.data.remote.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("events")
    suspend fun getEvents(@Query("active") active: Int): EventResponse

    @GET("events")
    suspend fun searchEvents(@Query("q") keyword: String): EventResponse
}
