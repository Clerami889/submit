package com.clerami.bottoooom.di

import android.content.Context
import com.clerami.bottoooom.data.local.FavoriteRepository
import com.clerami.bottoooom.data.local.room.FavoriteDatabase

object Injection {
    fun provideFavoriteRepository(context: Context): FavoriteRepository {
        val database = FavoriteDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        return FavoriteRepository(dao)
    }
}
