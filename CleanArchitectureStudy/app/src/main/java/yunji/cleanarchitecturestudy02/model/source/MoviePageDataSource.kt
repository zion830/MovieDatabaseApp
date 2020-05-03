package yunji.cleanarchitecturestudy02.model.source

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import yunji.cleanarchitecturestudy02.model.response.Movie
import yunji.cleanarchitecturestudy02.model.response.MovieListResponse

/*
 * Created by yunji on 12/03/2020
 */
class MoviePageDataSource private constructor(
    private val dataSource: MovieDataSource,
    private val pagingSuccess: (response: MovieListResponse) -> Unit,
    private val pagingFailed: (errMsg: String) -> Unit
) : PageKeyedDataSource<Int, Movie>() {
    private var totalPages = FIRST_PAGE

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGING_UNIT = 1
        private const val PAGE_SIZE = 20
        private val TAG = MoviePageDataSource::class.simpleName

        val moviePageConfig = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPrefetchDistance(PAGE_SIZE)
            .build()
    }

    class Factory(
        private val dataSource: MovieDataSource,
        private val pagingSuccess: (response: MovieListResponse) -> Unit,
        private val pagingFailed: (errMsg: String) -> Unit
    ) : DataSource.Factory<Int, Movie>() {

        override fun create(): DataSource<Int, Movie> =
            MoviePageDataSource(dataSource, pagingSuccess, pagingFailed)
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        dataSource.getAllMovieList(
            FIRST_PAGE,
            success = {
                totalPages = it.totalPages
                callback.onResult(it.movies, null, FIRST_PAGE + PAGING_UNIT)
                pagingSuccess(it)
            }, failed = {
                pagingFailed(it)
                Log.e(TAG, it)
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        if (params.key > totalPages) {
            return // 마지막 페이지일 경우 로딩하지 않음
        }

        dataSource.getAllMovieList(
            params.key,
            success = {
                totalPages = it.totalPages
                callback.onResult(it.movies, params.key + PAGE_SIZE)
                pagingSuccess(it)
            }, failed = {
                pagingFailed(it)
                Log.e(TAG, it)
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // do nothing
    }
}