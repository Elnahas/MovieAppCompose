package elnahas.hazem.movieappcompose.movieList.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import elnahas.hazem.movieappcompose.movieList.presentation.MovieListState
import elnahas.hazem.movieappcompose.movieList.presentation.MovieListUiEvent
import elnahas.hazem.movieappcompose.movieList.util.Category

@Composable
fun UpComingMoviesScreen(
    movieListState: MovieListState,
    navController: NavController,
    onEvent: (MovieListUiEvent) -> Unit,
    modifier: Modifier = Modifier) {

    if (movieListState.upcomingMovieList.isEmpty()){
        Box(modifier = modifier.fillMaxSize() , contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    else{

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp , horizontal = 4.dp),
        ) {

            items ( movieListState.upcomingMovieList.size ){index ->

                MovieItem(navHostController =  navController ,movie = movieListState.upcomingMovieList[index])

                Spacer(modifier = Modifier.height(16.dp))


                if(index >= movieListState.upcomingMovieList.size - 1  && !movieListState.isLoading){

                    onEvent(MovieListUiEvent.Paginate(Category.UPCOMING))
                }

            }
        }


    }





}
