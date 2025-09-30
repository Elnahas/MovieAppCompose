package elnahas.hazem.movieappcompose.movieList.data.mappers

import elnahas.hazem.movieappcompose.movieList.data.local.movie.MovieEntity
import elnahas.hazem.movieappcompose.movieList.data.remote.respnod.MovieDto
import elnahas.hazem.movieappcompose.movieList.domin.model.Movie

fun MovieDto.toMovieEntity(
    category: String
): MovieEntity {
    return MovieEntity(
        adult = adult ?: false,
        backdropPath = backdropPath ?: "",
        originalLanguage = originalLanguage ?: "",
        overview = overview ?: "",
        posterPath = posterPath ?: "",
        releaseDate = releaseDate ?: "",
        title = title ?: "",
        voteAverage = voteAverage ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = voteCount ?: 0,
        id = id ?: -1,
        originalTitle = originalTitle ?: "",
        video = video ?: false,

        category = category,

        genreIds = genreIds
    )
}

fun MovieEntity.toMovie(
    category: String
): Movie {
    return Movie(
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        video = video,
        id = id,
        adult = adult,
        originalTitle = originalTitle,
        category = category,
        genreIds = genreIds
    )
}







