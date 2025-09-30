package elnahas.hazem.movieappcompose.movieList.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    data object Home : Screen("home", "Home", Icons.Default.Home)
    data object Movies : Screen("movies", "Movies", Icons.Default.Movie)
    data object Favorites : Screen("favorites", "Favorites", Icons.Default.Favorite)
    data object Profile : Screen("profile", "Profile", Icons.Default.Person)

    data object Details : Screen("details/{movieId}") {
        const val ARG_MOVIE_ID = "movieId"
        fun create(movieId: Int) = "details/$movieId"
    }
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Movies,
    Screen.Favorites,
    Screen.Profile
)