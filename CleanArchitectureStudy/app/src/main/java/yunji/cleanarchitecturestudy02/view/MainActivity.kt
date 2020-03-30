package yunji.cleanarchitecturestudy02.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import yunji.cleanarchitecturestudy02.R
import yunji.cleanarchitecturestudy02.adapter.MoviePagedRecyclerAdapter
import yunji.cleanarchitecturestudy02.base.BaseActivity
import yunji.cleanarchitecturestudy02.databinding.ActivityMainBinding
import yunji.cleanarchitecturestudy02.listener.OnItemClickListener
import yunji.cleanarchitecturestudy02.listener.OnSingleClickListener
import yunji.cleanarchitecturestudy02.model.repository.MovieRepository
import yunji.cleanarchitecturestudy02.model.response.Movie
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
        observeUiData()
        handleIntent(intent)
    }

    private fun initAdapter() {
        movieRecyclerAdapter.apply {
            onItemClickListener = OnSingleClickListener.wrap(
                object : OnItemClickListener<Movie> {
                    override fun onClick(item: Movie) = showToast(item.toString())
                })
        }
    }

    private fun initView() {
        binding.apply {
            rvMain.adapter = movieRecyclerAdapter
        }

        viewModel.initMovieData()
    }

    private fun observeUiData() {
        with(viewModel) {
            msgText.observe(this@MainActivity, Observer { showToast(it) })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.menu_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
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
            //use the query to search your data somehow
        }
    }
}