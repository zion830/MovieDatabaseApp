package yunji.cleanarchitecturestudy02.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/*
 * Created by yunji on 03/05/2020
 */
open class BaseViewModel : ViewModel() {
    // 모든 화면에 들어가는 ui control data 모음
    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    protected val _toastTextId = MutableLiveData<Int>()
    val toastTextId: LiveData<Int> get() = _toastTextId
}