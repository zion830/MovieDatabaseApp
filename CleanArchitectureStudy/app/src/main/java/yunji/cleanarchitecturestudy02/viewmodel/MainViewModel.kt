package yunji.cleanarchitecturestudy02.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import yunji.cleanarchitecturestudy02.R
import yunji.cleanarchitecturestudy02.base.BaseViewModel
import yunji.cleanarchitecturestudy02.model.repository.MovieRepository
import yunji.cleanarchitecturestudy02.model.source.MoviePageDataSource
import yunji.cleanarchitecturestudy02.ui.MovieListUiModel
import yunji.cleanarchitecturestudy02.ui.toUiModel

/*
 * Created by yunji on 10/03/2020
 */
class MainViewModel(
    private val repository: MovieRepository
) : BaseViewModel() {
    private val _isExist = MutableLiveData<Boolean>()
    val isExist: LiveData<Boolean> get() = _isExist

    private val _searchKeyword = MutableLiveData<String>()
    private val searchKeyword: LiveData<String> get() = _searchKeyword

    private val pagingSuccess: () -> Unit = {
        _isLoading.value = false
        _isExist.value = pagingMovieList.value?.isNotEmpty()
    }

    private val failPaging: (msg: String) -> Unit = {
        _toastTextId.value = (R.string.err_search_fail)
        _isLoading.value = false
        _isExist.value = pagingMovieList.value?.isNotEmpty()
    }

    private val pageDataSource = MoviePageDataSource.Factory(repository, pagingSuccess, failPaging).map {
        it.toUiModel()
    }

    val pagingMovieList: LiveData<PagedList<MovieListUiModel>> =
        LivePagedListBuilder(pageDataSource, MoviePageDataSource.moviePageConfig).build()

    fun initMovieData() {
        _isLoading.value = true
    }

    fun searchMovie(query: String) {

    }
}