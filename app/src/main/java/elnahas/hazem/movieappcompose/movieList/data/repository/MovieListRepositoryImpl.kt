package elnahas.hazem.movieappcompose.movieList.data.repository

import android.util.Log
import elnahas.hazem.movieappcompose.movieList.data.local.movie.MovieDatabase
import elnahas.hazem.movieappcompose.movieList.data.mappers.toMovie
import elnahas.hazem.movieappcompose.movieList.data.mappers.toMovieEntity
import elnahas.hazem.movieappcompose.movieList.data.remote.MovieApi
import elnahas.hazem.movieappcompose.movieList.domin.model.Movie
import elnahas.hazem.movieappcompose.movieList.domin.repository.MovieListRepository
import elnahas.hazem.movieappcompose.movieList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {

    override suspend fun getMoviesList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {



            val localMovieList = movieDatabase.movieDao.getMovieListByCategory(category)
            Log.d("ZZZZZZZZZ" , "$localMovieList")

            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalMovie) {
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                movieApi.getMoviesList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            Log.d("ZZZZZZZZZ" , "API LOG $movieListFromApi")

            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            movieDatabase.movieDao.upsertMovies(movieEntities)

            emit(Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))

        }
    }

    override suspend fun getMovieById(id: Int): Flow<Resource<Movie>> {
        return flow {

            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.getMovieListById(id)

            if (movieEntity != null) {
                emit(
                    Resource.Success(movieEntity.toMovie(movieEntity.category))
                )

                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Error no such movie"))

            emit(Resource.Loading(false))


        }
    }
}