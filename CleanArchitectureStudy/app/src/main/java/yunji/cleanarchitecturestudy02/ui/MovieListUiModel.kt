package yunji.cleanarchitecturestudy02.ui

import yunji.cleanarchitecturestudy02.POSTER_BASE_URL
import yunji.cleanarchitecturestudy02.model.response.Movie

/*
 * Created by yunji on 04/05/2020
 */
data class MovieListUiModel(
    val id: Int = -1,
    val title: String = "",
    val releaseDate: String = "",
    val overview: String = "",
    val posterPath: String = ""
) {

    fun getPosterFullPath() = POSTER_BASE_URL + posterPath
}

fun Movie.toUiModel() = MovieListUiModel(id, title, releaseDate, overview, posterPath)