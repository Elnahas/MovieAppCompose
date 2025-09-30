package elnahas.hazem.movieappcompose.details.presentation

import NetworkImage
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import elnahas.hazem.movieappcompose.movieList.data.remote.MovieApi
import elnahas.hazem.movieappcompose.movieList.util.RatingBar

@Composable
fun DetailsScreen(onBack: () -> Unit) {


    val viewModel = hiltViewModel<DetailsViewModel>()
    val detailsState = viewModel.detailsState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        NetworkImage(
            imageUrl = MovieApi.IMAGE_BASE_URL + detailsState.movie?.backdropPath,
            modifier = Modifier.fillMaxWidth(),
            shape = ImageShape.Rounded(0.dp),   // Circle | Oval | Rounded(…)
            size = 200.dp,                      // أو width/height منفصلين
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

        Spacer(Modifier.height(16.dp))


        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            NetworkImage(
                imageUrl = MovieApi.IMAGE_BASE_URL + detailsState.movie?.posterPath,
                shape = ImageShape.Rounded(12.dp),   //  | Oval | Rounded(…)
                modifier = Modifier.width(100.dp).height(220.dp),                      // أو width/height منفصلين
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


                detailsState.movie?.let { movie ->
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = movie.title!!,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp)
                        ) {

                            RatingBar(
                                starsModifier = Modifier.size(18.dp),
                                rating = movie.voteAverage!! / 2
                            )


                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = movie.voteAverage.toString().take(3) ?: "Empty",
                                color = Color.LightGray,
                                fontSize = 14.sp,
                                maxLines = 1
                            )



                        }



                        Spacer(modifier = Modifier.height(10.dp))


                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp),
                            text ="Language:  ${movie.originalLanguage}" ?: "Empty",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp),
                            text ="Release Date:  ${movie.releaseDate}" ?: "Empty",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp),
                            text ="votes :  ${movie.voteCount}" ?: "Empty",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                    }



                }



        }


        Text(
            modifier = Modifier
                .padding(start = 16.dp),
            text ="Overview:" ?: "Empty",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))


        Text(
            modifier = Modifier
                .padding(start = 16.dp),
            text =detailsState.movie?.overview ?: "Empty",
            fontSize = 16.sp,

        )

        Spacer(modifier = Modifier.height(32.dp))

    }

}