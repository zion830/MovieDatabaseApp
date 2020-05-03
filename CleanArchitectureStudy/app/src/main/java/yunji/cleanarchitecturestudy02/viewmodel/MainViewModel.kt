package yunji.cleanarchitecturestudy02.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import yunji.cleanarchitecturestudy02.R
import yunji.cleanarchitecturestudy02.base.BaseViewModel
import yunji.cleanarchitecturestudy02.model.repository.MovieRepository
import yunji.cleanarchitecturestudy02.ui.MovieListUiModel

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

    lateinit var pagingMovieList: LiveData<PagedList<MovieListUiModel>>

    fun initMovieData() {
        _isLoading.value = true

        repository.getMoviePagedList(
            pagingSuccess = {
                _isLoading.value = false
                _isExist.value = pagingMovieList.value?.size!! > 0
            },
            pagingFailed = {
                _toastTextId.value = (R.string.err_search_fail)
                _isLoading.value = false
                _isExist.value = false
            }
        )
    }

    fun searchMovie(query: String) {
        repository.searchMovieByTitle(
            query,
            0,
            success = {
                _isLoading.value = false
                _isExist.value = pagingMovieList.value?.size!! > 0
            },
            failed = {
                _toastTextId.value = (R.string.err_search_fail)
                _isLoading.value = false
                _isExist.value = false
            }
        )
    }
}