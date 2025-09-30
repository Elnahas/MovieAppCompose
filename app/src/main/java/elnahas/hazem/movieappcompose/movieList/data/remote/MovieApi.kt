package elnahas.hazem.movieappcompose.movieList.data.remote

import elnahas.hazem.movieappcompose.movieList.data.remote.respnod.MovieDto
import elnahas.hazem.movieappcompose.movieList.data.remote.respnod.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page")  page: Int,
        @Query(API_KEY) apiKey: String = API_KEY_VALUE
    ) : MovieListDto



    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "api_key"
        const val API_KEY_VALUE = "a36b83ab5660fbf06fa2f787a44a7311"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}