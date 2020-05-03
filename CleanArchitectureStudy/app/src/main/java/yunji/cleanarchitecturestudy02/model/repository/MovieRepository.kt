package yunji.cleanarchitecturestudy02.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import yunji.cleanarchitecturestudy02.model.response.MovieListResponse
import yunji.cleanarchitecturestudy02.model.source.MovieDataSource
import yunji.cleanarchitecturestudy02.model.source.MoviePageDataSource
import yunji.cleanarchitecturestudy02.model.source.MovieRemoteDataSource
import yunji.cleanarchitecturestudy02.network.RetrofitBuilder
import yunji.cleanarchitecturestudy02.ui.MovieListUiModel
import yunji.cleanarchitecturestudy02.ui.toUiModel
import java.util.concurrent.Executors

/*
 * Created by yunji on 10/03/2020
 */
object MovieRepository : MovieDataSource {
    private val movieRemoteDataSource = MovieRemoteDataSource(RetrofitBuilder.service)
    private val executor = Executors.newSingleThreadExecutor()

    fun getMoviePagedList(
        pagingSuccess: (response: MovieListResponse) -> Unit,
        pagingFailed: (errMsg: String) -> Unit
    ): LiveData<PagedList<MovieListUiModel>> {
        val pageDataSourceFactory = MoviePageDataSource.Factory(
            this, pagingSuccess, pagingFailed
        ).map {
            it.toUiModel()
        }

        return LivePagedListBuilder(pageDataSourceFactory, MoviePageDataSource.moviePageConfig)
            .setFetchExecutor(executor)
            .build()
    }

    override fun getAllMovieList(
        page: Int,
        success: (movieListResponse: MovieListResponse) -> Unit,
        failed: (errMsg: String) -> Unit
    ) {
        movieRemoteDataSource.getAllMovieList(page, success, failed)
    }

    override fun searchMovieByTitle(
        query: String,
        page: Int,
        success: (movieListResponse: MovieListResponse) -> Unit,
        failed: (errMsg: String) -> Unit
    ) {
        // movieRemoteDataSource.getPopularMovieList()
    }
}