package com.manshal_khatri.pikadex.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.manshal_khatri.pikadex.model.EvolutionChainObj
import com.manshal_khatri.pikadex.model.MoveData
import com.manshal_khatri.pikadex.model.Pokemons
import com.manshal_khatri.pikadex.model.TypesData
import com.manshal_khatri.pikadex.util.Convertors

@Database(entities = [Pokemons::class,MoveData::class,TypesData::class,EvolutionChainObj::class], version = 4)
@TypeConverters(Convertors::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun pkmnDao() : PokemonDao
    abstract fun moveDao() : MoveDao
    abstract fun typeDao() : TypeDao
    abstract fun evoChainDao() : EvoChainDao

    companion object{
        val migration_3_4 = object : Migration(3,4){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("create table evo_chain(id TEXT primary key not null,pokeNames TEXT not null)")
            }

        }
        @Volatile
        private var instance : RoomDB? = null
        fun getDatabase(context: Context) = instance
            ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "pokemon_db"
                ).addMigrations(migration_3_4).build().also { instance = it }
            }
    }
}
