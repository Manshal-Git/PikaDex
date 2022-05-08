package com.manshal_khatri.pikadex.util

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.manshal_khatri.pikadex.model.Stats
import com.manshal_khatri.pikadex.model.PokeTypes
@TypeConverters
class Convertors {
    @TypeConverter
    fun pokeTypesToString(type:PokeTypes):String{
        return type.type1+" "+type.type2
    }
    @TypeConverter
    fun stringToPokeType(s:String):PokeTypes{
        val t1 = s.substringAfter(" ")
        val t2 = s.substringBefore(" ")
        return PokeTypes(t2,t1)
    }
    @TypeConverter
    fun statsToString(stat : Stats):String{
        var stats: String
        with(stat){
            stats = "$hp $attack $defence $spAtk $spDef $speed "
        }
        return stats
    }
    @TypeConverter
    fun strToStats(s: String):Stats{
        var sb = s
        val ss = mutableListOf<String>()
        while(sb.length > 0){
            ss.add(sb.substringBefore(" "))
            sb = sb.substringAfter(" ")
        }
        return Stats(ss[0],ss[1],ss[2],ss[3],ss[4],ss[5])
    }

    @TypeConverter
    fun listToString(list: List<String>):String{
        var s = ""
        for(e in list){
            s+= "$e "
        }
        return s
    }
    @TypeConverter
    fun stringToList(s:String):List<String>{
        var sb = s
        val list = mutableListOf<String>()
        while(sb.length > 0){
            list.add(sb.substringBefore(" "))
            sb = sb.substringAfter(" ")
        }
        return list
    }
   /* fun cutout(s:String,list : MutableList<String>):MutableList<String>{
        if(s.length==0){
            return list
        }
        list.add(s.substringBefore(" "))
        cutout(s.substringAfter(" "),list)
        return list
    }*/
}