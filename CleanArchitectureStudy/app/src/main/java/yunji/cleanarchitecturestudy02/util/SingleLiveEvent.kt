package yunji.cleanarchitecturestudy02.util

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean


/*
 * Created by yunji on 30/03/2020
 */
class SingleLiveEvent<T> : MutableLiveData<T?>() {
    private val eventFlag = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        super.observe(owner, Observer { t ->
            if (eventFlag.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(value: T?) {
        eventFlag.set(true)
        super.setValue(value)
    }

    override fun postValue(value: T?) {
        eventFlag.set(true)
        super.postValue(value)
    }

    @MainThread
    fun call() {
        value = null
    }
}