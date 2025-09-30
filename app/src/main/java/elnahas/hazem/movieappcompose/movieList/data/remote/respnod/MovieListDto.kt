package elnahas.hazem.movieappcompose.movieList.data.remote.respnod

import com.google.gson.annotations.SerializedName

data class MovieListDto(
    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("results")
    val results: List<MovieDto> = emptyList(),

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("total_results")
    val totalResults: Int? = null
)
