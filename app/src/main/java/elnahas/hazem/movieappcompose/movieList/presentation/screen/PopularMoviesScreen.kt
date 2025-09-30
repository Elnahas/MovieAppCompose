package elnahas.hazem.movieappcompose.movieList.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import elnahas.hazem.movieappcompose.movieList.presentation.MovieListState
import elnahas.hazem.movieappcompose.movieList.presentation.MovieListUiEvent
import elnahas.hazem.movieappcompose.movieList.util.Category

@Composable
fun PopularMoviesScreen(
    movieListState: MovieListState,
    navController: NavController,
    onEvent: (MovieListUiEvent) -> Unit,
    modifier: Modifier = Modifier) {

    if (movieListState.popularMovieList.isEmpty()){
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

            items ( movieListState.popularMovieList.size ){index ->

                MovieItem(navHostController = navController,movie = movieListState.popularMovieList[index])

                Spacer(modifier = Modifier.height(16.dp))


                if(index >= movieListState.popularMovieList.size - 1  && !movieListState.isLoading){

                    onEvent(MovieListUiEvent.Paginate(Category.POPULAR))
                }

            }
        }


    }





}


