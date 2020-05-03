package yunji.cleanarchitecturestudy02.adapter

import yunji.cleanarchitecturestudy02.R
import yunji.cleanarchitecturestudy02.base.BaseDiffUtilCallback
import yunji.cleanarchitecturestudy02.base.BasePagedRecyclerView
import yunji.cleanarchitecturestudy02.databinding.ItemMovieBinding
import yunji.cleanarchitecturestudy02.ui.MovieListUiModel

/*
 * Created by yunji on 10/03/2020
 */
class MoviePagedRecyclerAdapter : BasePagedRecyclerView<ItemMovieBinding, MovieListUiModel>(
    movieDiffUtilCallback, R.layout.item_movie
) {

    companion object {
        private val movieDiffUtilCallback = object : BaseDiffUtilCallback<MovieListUiModel>() {

            override fun areItemsTheSame(oldItem: MovieListUiModel, newItem: MovieListUiModel): Boolean = oldItem.id == newItem.id
        }
    }
}