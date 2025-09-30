package elnahas.hazem.movieappcompose.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import elnahas.hazem.movieappcompose.movieList.presentation.MovieListUiEvent
import elnahas.hazem.movieappcompose.movieList.presentation.MovieListViewModel
import elnahas.hazem.movieappcompose.movieList.util.BottomNavigationBar
import elnahas.hazem.movieappcompose.movieList.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    val viewModel: MovieListViewModel = hiltViewModel()
    val movieState = viewModel.movieListState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(bottomBar = {
//        BottomNavigationBar(
//            bottomNavController,
//            viewModel::onEvent
//        )
    }, topBar = {
        TopAppBar(
            title = {
                Text("Movie App Compose", fontSize = 15.sp)
            },
            modifier = Modifier.shadow(2.dp),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.inversePrimary)
        )
    }) { padding ->

        Box(modifier = Modifier.fillMaxSize().padding(padding)) {

//            NavHost(navController = bottomNavController , startDestination = Screen.PopularMovieList.rout){
//
//                composable(route = Screen.PopularMovieList.rout){
//                 //   PopularMovieListScreen(modifier = Modifier.padding(padding))
//                }
//
//                composable(route = Screen.UpcomingMovieList.rout){
//                    //   PopularMovieListScreen(modifier = Modifier.padding(padding))
//                }
//            }

        }
    }






}

//@Composable
//fun BottomNavigationBar(
//    bottomNavController: NavHostController,
//    onEvent: (MovieListUiEvent) -> Unit
//) {
//
//    val items = listOf(
//        BottomItem(
//            title = "Popular",
//            icon = Icons.Rounded.Movie
//        ),
//        BottomItem(
//            title = "Upcoming",
//            icon = Icons.Rounded.Upcoming
//        ),
//    )
//
//    val selected = rememberSaveable {
//        mutableIntStateOf(0)
//    }
//
//    NavigationBar {
//        Row(modifier = Modifier.background(MaterialTheme.colorScheme.inversePrimary)) {
//            items.forEachIndexed { index, bottomItem ->
//                NavigationBarItem(
//                    selected = selected.intValue == index,
//                    onClick = {
//                        selected.intValue = index
//
//                        when (selected.intValue) {
//                            0 -> {
//                                onEvent(MovieListUiEvent.Navigate)
//                                bottomNavController.popBackStack()
//                                bottomNavController.navigate(Screen.PopularMovieList)
//                            }
//
//                            1 -> {
//                                onEvent(MovieListUiEvent.Navigate)
//                                bottomNavController.popBackStack()
//                                bottomNavController.navigate(Screen.UpcomingMovieList)
//
//                            }
//                        }
//
//                    },
//                    icon = {
//                        Icon(
//                            imageVector = bottomItem.icon,
//                            contentDescription = bottomItem.title,
//                            tint = MaterialTheme.colorScheme.onBackground
//
//                        )
//                    },
//                    label = {
//                        Text(
//                            text = bottomItem.title,
//                            color = MaterialTheme.colorScheme.onBackground
//                        )
//                    }
//                )
//            }
//        }
//    }
//
//
//}

data class BottomItem(
    val title: String,
    val icon: ImageVector
)