package elnahas.hazem.movieappcompose.movieList.domin.repository

import elnahas.hazem.movieappcompose.movieList.data.remote.respnod.MovieListDto
import elnahas.hazem.movieappcompose.movieList.domin.model.Movie
import elnahas.hazem.movieappcompose.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    suspend fun getMoviesList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int): Flow<Resource<List<Movie>>>


    suspend fun getMovieById(id: Int): Flow<Resource<Movie>>

}