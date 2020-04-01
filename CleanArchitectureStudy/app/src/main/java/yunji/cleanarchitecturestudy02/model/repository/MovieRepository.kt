package yunji.cleanarchitecturestudy02.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import yunji.cleanarchitecturestudy02.model.response.Movie
import yunji.cleanarchitecturestudy02.model.response.MovieListResponse
import yunji.cleanarchitecturestudy02.model.source.MovieDataSource
import yunji.cleanarchitecturestudy02.model.source.MoviePageDataSource
import yunji.cleanarchitecturestudy02.model.source.MovieRemoteDataSource
import yunji.cleanarchitecturestudy02.network.RetrofitBuilder
import java.util.concurrent.Executors

/*
 * Created by yunji on 10/03/2020
 */
class MovieRepository private constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieDataSource {

    companion object {
        private val executor = Executors.newSingleThreadExecutor()
        val instance: MovieRepository by lazy {
            MovieRepository(MovieRemoteDataSource(RetrofitBuilder.service))
        }
    }

    fun getMoviePagedList(
        pagingStart: () -> Unit,
        pagingSuccess: (response: MovieListResponse) -> Unit,
        pagingFailed: (errMsg: String) -> Unit
    ): LiveData<PagedList<Movie>> {
        val pageDataSourceFactory = MoviePageDataSource.Factory(this, pagingStart, pagingSuccess, pagingFailed)
        return LivePagedListBuilder(pageDataSourceFactory, MoviePageDataSource.moviePageConfig)
            .setFetchExecutor(executor)
            .build()
    }

    override fun getPopularMovieList(
        page: Int,
        success: (movieListResponse: MovieListResponse) -> Unit,
        failed: (errMsg: String) -> Unit
    ) {
        movieRemoteDataSource.getPopularMovieList(page, success, failed)
    }
}