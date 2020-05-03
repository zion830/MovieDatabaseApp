package yunji.cleanarchitecturestudy02.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import yunji.cleanarchitecturestudy02.R
import yunji.cleanarchitecturestudy02.base.BaseViewModel
import yunji.cleanarchitecturestudy02.model.repository.MovieRepository
import yunji.cleanarchitecturestudy02.model.response.Movie

/*
 * Created by yunji on 10/03/2020
 */
class MainViewModel(
    private val repository: MovieRepository
) : BaseViewModel() {
    private val _isExist = MutableLiveData<Boolean>()

    lateinit var pagingMovieList: LiveData<PagedList<Movie>>

    val isExist: LiveData<Boolean> get() = _isExist

    fun initMovieData() {
        pagingMovieList = repository.getMoviePagedList(
            onPagingStart = { _isLoading.postValue(true) },
            onPagingSuccess = {
                _isLoading.postValue(false)
                _isExist.postValue(pagingMovieList.value?.size!! > 0)
            },
            onPagingFailed = {
                _toastTextId.postValue(R.string.err_search_fail)
                _isLoading.postValue(false)
                _isExist.postValue(false)
            }
        )
    }
}