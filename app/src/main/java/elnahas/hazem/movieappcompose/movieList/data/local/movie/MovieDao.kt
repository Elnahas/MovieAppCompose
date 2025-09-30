package elnahas.hazem.movieappcompose.movieList.data.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieListById(id:Int): MovieEntity

    @Query("SELECT * FROM movies WHERE category = :category")
    suspend fun getMovieListByCategory(category:String): List<MovieEntity>
}