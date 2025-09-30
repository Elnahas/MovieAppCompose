package elnahas.hazem.movieappcompose.details.presentation

import elnahas.hazem.movieappcompose.movieList.domin.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
