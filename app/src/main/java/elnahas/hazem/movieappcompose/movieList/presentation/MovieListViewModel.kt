package elnahas.hazem.movieappcompose.movieList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import elnahas.hazem.movieappcompose.movieList.domin.repository.MovieListRepository
import elnahas.hazem.movieappcompose.movieList.util.Category
import elnahas.hazem.movieappcompose.movieList.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {

    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        getPopularMovies(false)
        getUpcomingMovies(false)
    }

    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            MovieListUiEvent.Navigate -> {
                _movieListState.update {
                    it.copy(
                        isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen
                    )
                }
            }

            is MovieListUiEvent.Paginate -> {

                if (event.category == Category.POPULAR) {
                    getPopularMovies(true)
                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovies(true)
                }


            }
        }

    }

    private fun getPopularMovies(forceFetchFromRemote: Boolean) {

        viewModelScope.launch {

            _movieListState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMoviesList(
                forceFetchFromRemote,
                Category.POPULAR,
                movieListState.value.popularMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {

                        result.data?.let { poplarList->
                            _movieListState.update {
                                it.copy(
                                    popularMovieList = movieListState.value.popularMovieList + poplarList.shuffled(),
                                    isLoading = false,
                                    popularMovieListPage = movieListState.value.popularMovieListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                }
            }


        }
    }

    private fun getUpcomingMovies(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {

            _movieListState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMoviesList(
                forceFetchFromRemote,
                Category.UPCOMING,
                movieListState.value.upcomingMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {

                        result.data?.let { upcomingList->
                            _movieListState.update {
                                it.copy(
                                    upcomingMovieList = movieListState.value.upcomingMovieList + upcomingList.shuffled(),
                                    isLoading = false,
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                }
            }


        }

    }
}