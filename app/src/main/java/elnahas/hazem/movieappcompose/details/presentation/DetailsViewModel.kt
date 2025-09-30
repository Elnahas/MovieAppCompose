package elnahas.hazem.movieappcompose.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import elnahas.hazem.movieappcompose.movieList.domin.repository.MovieListRepository
import elnahas.hazem.movieappcompose.movieList.presentation.MovieListState
import elnahas.hazem.movieappcompose.movieList.util.Resource
import elnahas.hazem.movieappcompose.movieList.util.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor (
    private val movieListRepository : MovieListRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int? = savedStateHandle.get(Screen.Details.ARG_MOVIE_ID)


    private val _detailsState = MutableStateFlow(DetailsState())

    val detailsState = _detailsState.asStateFlow()

    init {
        val id = movieId
        if (id == null || id <= 0) {
            // لو الآي دي مش صالح، اعرض خطأ بسيط
            _detailsState.update { it.copy(isLoading = false, error("Invalid movie id") ) }
        } else {
            getMovie(id)
        }
    }

    private fun getMovie(id: Int) {

        viewModelScope.launch {
            _detailsState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMovieById(id).collectLatest { result ->


                when(result){
                    is Resource.Error -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = false,)
                        }
                    }
                    is Resource.Loading -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = result.isLoading,)
                        }
                    }
                    is Resource.Success -> {

                        result.data?.let{movie ->
                            _detailsState.update {
                                it.copy(
                                    movie = movie,
                                )
                            }
                        }


                    }
                }



            }
        }

    }

}