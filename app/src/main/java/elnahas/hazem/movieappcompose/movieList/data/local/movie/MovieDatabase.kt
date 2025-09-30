package elnahas.hazem.movieappcompose.movieList.data.local.movie

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [MovieEntity::class], version = 1 , exportSchema = false)
@TypeConverters(Converters::class) // Add this line
abstract class MovieDatabase :RoomDatabase() {
abstract val movieDao : MovieDao

}