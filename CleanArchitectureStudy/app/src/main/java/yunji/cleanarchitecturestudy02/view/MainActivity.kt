package yunji.cleanarchitecturestudy02.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import yunji.cleanarchitecturestudy02.R
import yunji.cleanarchitecturestudy02.adapter.MoviePagedRecyclerAdapter
import yunji.cleanarchitecturestudy02.base.BaseActivity
import yunji.cleanarchitecturestudy02.databinding.ActivityMainBinding
import yunji.cleanarchitecturestudy02.listener.OnItemClickListener
import yunji.cleanarchitecturestudy02.listener.OnSingleClickListener
import yunji.cleanarchitecturestudy02.model.repository.MovieRepository
import yunji.cleanarchitecturestudy02.ui.MovieListUiModel
import yunji.cleanarchitecturestudy02.util.showToast
import yunji.cleanarchitecturestudy02.viewmodel.MainViewModel
import yunji.cleanarchitecturestudy02.viewmodel.MainViewModelFactory


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    private val movieRecyclerAdapter = MoviePagedRecyclerAdapter()

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory(MovieRepository))[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
        initView()
        handleIntent(intent)
    }

    private fun initAdapter() {
        movieRecyclerAdapter.apply {
            onItemClickListener = OnSingleClickListener.wrap(
                object : OnItemClickListener<MovieListUiModel> {
                    override fun onClick(item: MovieListUiModel) = showToast(item.toString())
                })
        }
    }

    private fun initView() {
        setSupportActionBar(binding.toolbarMain)
        with(binding) {
            rvMain.adapter = movieRecyclerAdapter
        }

        viewModel.initMovieData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.menu_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_sort -> {
            true
        }
        else -> false
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)

        }
    }
}