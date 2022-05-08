package com.manshal_khatri.pikadex.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manshal_khatri.pikadex.model.TypesData

@Dao
interface TypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeType(item : TypesData)

    @Query("select * from type")
    fun getAllTypes(): LiveData<List<TypesData>>

    @Query("select count(id) from type")
    suspend fun isUpToDate():Int
}