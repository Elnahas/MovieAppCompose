package elnahas.hazem.movieappcompose.movieList.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import elnahas.hazem.movieappcompose.details.presentation.DetailsScreen
import elnahas.hazem.movieappcompose.movieList.presentation.MovieListViewModel
import elnahas.hazem.movieappcompose.movieList.presentation.screen.PopularMoviesScreen
import elnahas.hazem.movieappcompose.movieList.presentation.screen.UpComingMoviesScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: MovieListViewModel = hiltViewModel()
    val movieState = viewModel.movieListState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            PopularMoviesScreen(
                navController = navController,
                movieListState = movieState,
                onEvent = viewModel::onEvent
            )
        }
        composable(Screen.Movies.route) {
            UpComingMoviesScreen(
                navController = navController,
                movieListState = movieState,
                onEvent = viewModel::onEvent
            )
        }
        composable(Screen.Favorites.route) { SimpleScreen("Favorites Screen") }
        composable(Screen.Profile.route) { SimpleScreen("Profile Screen") }

        composable(
            route = Screen.Details.route, // "details/{movieId}"
            arguments = listOf(
                navArgument(Screen.Details.ARG_MOVIE_ID) { type = NavType.IntType }
            )
        ) {
            DetailsScreen(onBack = { navController.popBackStack() })
        }
    }
}

@Composable
fun SimpleScreen(name: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = name, style = MaterialTheme.typography.headlineMedium)
    }
}