package com.manshal_khatri.pikadex.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manshal_khatri.pikadex.model.EvolutionChainObj

@Dao
interface EvoChainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeChain(chain : EvolutionChainObj)

    @Query("select * from evo_chain where id like('%/'||:id)")
    fun getEvoChain(id:String): LiveData<List<EvolutionChainObj>>

}