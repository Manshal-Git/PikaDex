package com.manshal_khatri.pikadex.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manshal_khatri.pikadex.model.MoveData
import com.manshal_khatri.pikadex.model.Pokemons

@Dao
interface MoveDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun storeMove(move : MoveData)

    @Query("select * from move")
    fun getAllMoves(): LiveData<List<MoveData>>

    @Query("select count(mid) from move")
   suspend fun isUpToDate():Int
}