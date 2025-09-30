package elnahas.hazem.movieappcompose.core.presentation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import elnahas.hazem.movieappcompose.movieList.util.AppNavGraph
import elnahas.hazem.movieappcompose.movieList.util.BottomNavigationBar
import elnahas.hazem.movieappcompose.movieList.util.Screen
import elnahas.hazem.movieappcompose.ui.theme.MovieAppComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppComposeTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val topLevelRoutes = listOf(
        Screen.Home.route,
        Screen.Movies.route,
        Screen.Profile.route
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentDestination in topLevelRoutes) {
                BottomNavigationBar(navController)
            }
        }
    ) { padding ->
        SetBarColor(color = MaterialTheme.colorScheme.inverseSurface)

        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun SetBarColor(color: Color) {
    val view = LocalView.current
    val window = (view.context as Activity).window

    LaunchedEffect(color) {
        window.statusBarColor = color.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
            color.luminance() > 0.5f
    }
}
