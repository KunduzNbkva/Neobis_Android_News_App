package kg.kunduznbkva.newsapp.utils

import androidx.room.TypeConverter
import kg.kunduznbkva.newsapp.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source) : String {
         return source.name ?: "null"
    }

    @TypeConverter
    fun toSource(name: String) : Source {
        return Source(name,name)
    }
}