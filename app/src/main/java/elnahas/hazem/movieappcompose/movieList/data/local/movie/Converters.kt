package elnahas.hazem.movieappcompose.movieList.data.local.movie

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromIntList(value: List<Int?>?): String? {
        return if (value == null) null else Gson().toJson(value)
    }

    @TypeConverter
    fun toIntList(value: String?): List<Int?>? {
        return if (value == null) null else {
            val listType = object : TypeToken<List<Int?>>() {}.type
            Gson().fromJson(value, listType)
        }
    }
}