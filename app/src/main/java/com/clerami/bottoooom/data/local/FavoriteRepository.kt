package com.clerami.bottoooom.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.clerami.bottoooom.data.local.entity.FavoriteEvent
import com.clerami.bottoooom.data.local.room.FavoriteDao
import com.clerami.bottoooom.data.local.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors




class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun insert(favoriteEvent: FavoriteEvent) {
        executorService.execute { favoriteDao.insert(favoriteEvent) }
    }
}
