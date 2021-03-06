package yunji.cleanarchitecturestudy02.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import yunji.cleanarchitecturestudy02.BR

/*
 * Created by yunji on 09/03/2020
 */
abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel>(
    @LayoutRes val layoutId: Int
) : AppCompatActivity() {
    protected lateinit var binding: B
        private set

    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        initBinding()
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = this@BaseActivity
            setVariable(BR.viewModel, viewModel)
        }
    }
}