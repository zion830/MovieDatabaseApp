package yunji.cleanarchitecturestudy02.model.source

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.awaitResponse
import yunji.cleanarchitecturestudy02.model.response.MovieListResponse
import yunji.cleanarchitecturestudy02.network.MovieApi

/*
 * Created by yunji on 10/03/2020
 */
class MovieRemoteDataSource(
    private val movieApi: MovieApi
) : MovieDataSource {
    private val tag = MovieRemoteDataSource::class.simpleName

    override fun getAllMovieList(
        page: Int,
        success: (movieListResponse: MovieListResponse?) -> Unit,
        failed: (errMsg: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val response = movieApi.getPopularMovies(page).awaitResponse()
                handleResponse(response, success, failed)
            } catch (e: Exception) {
                handleResponse(null, success, failed)
                e.printStackTrace()
            }
        }
    }

    override fun searchMovieByTitle(
        query: String,
        page: Int,
        success: (movieListResponse: MovieListResponse?) -> Unit,
        failed: (errMsg: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val response = movieApi.searchMovieByTitle(query, page).awaitResponse()
                handleResponse(response, success, failed)
            } catch (e: Exception) {
                handleResponse(null, success, failed)
                e.printStackTrace()
            }
        }
    }

    private suspend fun <T> handleResponse(
        response: Response<T>?,
        success: (response: T) -> Unit,
        failed: (errMsg: String) -> Unit
    ) = withContext(Dispatchers.Main) {
        if (response?.body() == null) {
            failed("$tag : response is null")
            return@withContext
        }

        if (response.isSuccessful) {
            success(response.body()!!)
        } else {
            failed(response.message())
            Log.e(tag, response.body().toString())
        }
    }
}