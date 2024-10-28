package com.clerami.bottoooom.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.clerami.bottoooom.data.local.entity.FavoriteEvent

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteEvent: FavoriteEvent)
}
