package com.manshal_khatri.pikadex.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manshal_khatri.pikadex.model.MoveData
import com.manshal_khatri.pikadex.model.Pokemons
import com.manshal_khatri.pikadex.model.TypesData
import com.manshal_khatri.pikadex.util.Convertors

@Database(entities = [Pokemons::class,MoveData::class,TypesData::class], version = 3)
@TypeConverters(Convertors::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun pkmnDao() : PokemonDao
    abstract fun moveDao() : MoveDao
    abstract fun typeDao() : TypeDao

    companion object{
        @Volatile
        private var instance : RoomDB? = null
        fun getDatabase(context: Context) = instance
            ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "pokemon_db"
                ).build().also { instance = it }
            }
    }
}
