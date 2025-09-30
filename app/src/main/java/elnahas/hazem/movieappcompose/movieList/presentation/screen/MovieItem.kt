package elnahas.hazem.movieappcompose.movieList.presentation.screen

import NetworkImage
import android.icu.text.CaseMap.Title
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.size.Size
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.crossfade
import elnahas.hazem.movieappcompose.R
import elnahas.hazem.movieappcompose.movieList.data.remote.MovieApi
import elnahas.hazem.movieappcompose.movieList.domin.model.Movie
import elnahas.hazem.movieappcompose.movieList.util.RatingBar
import elnahas.hazem.movieappcompose.movieList.util.Screen

@Composable
fun MovieItem(    navHostController: NavController
                  , movie : Movie ) {

    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    val dominantColor by remember {
        mutableStateOf(defaultColor)
    }

    Column(modifier = Modifier
        .wrapContentHeight()
        .width(200.dp)
        .padding(8.dp)
        .clip(RoundedCornerShape(28.dp))
        .background(Brush.verticalGradient(
            colors = listOf(
                dominantColor,
                defaultColor)

        )).clickable {
            //Navigated to details screen
            val id = movie.id ?: return@clickable
            navHostController.navigate(Screen.Details.create(id))
        }) {



        NetworkImage(
            imageUrl = MovieApi.IMAGE_BASE_URL + movie.backdropPath,
            shape = ImageShape.Rounded(20.dp),   // Circle | Oval | Rounded(…)
            size = 180.dp,                      // أو width/height منفصلين
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            placeholder = null,                 // تقدر تبعت Painter لو عايز
            errorPainter = null,                // لو null هيظهر أيقونة حمراء افتراضياً
            fallbackPainter = null,             // عند null/blank URL
            showShimmer = true,
            crossfade = true,
            loadOriginalSize = false,           // خليها true لو عايز الدقة الأصلية
            onSuccess = { /* tracking */ },
            onError = { throwable -> /* logging */ }
        )



        Spacer(modifier = Modifier.height(6.dp))

        Text(modifier = Modifier.padding(start = 16.dp , end = 8.dp)
            , text = movie.title ?: "Empty",
            color = Color.White,
            fontSize = 15.sp,
            maxLines = 1)

        Row (modifier = Modifier.fillMaxSize()
            .padding(start = 16.dp , bottom = 12.dp , top = 4.dp)){

            RatingBar(
                starsModifier = Modifier.size(18.dp),
                rating = movie.voteAverage!! / 2
            )


            Text(modifier = Modifier.padding(start = 4.dp )
                , text = movie.voteAverage.toString().take(3) ?: "Empty",
                color = Color.LightGray,
                fontSize = 15.sp,
                maxLines = 1)

        }
    }




}
