package yunji.cleanarchitecturestudy02.adapter

import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import yunji.cleanarchitecturestudy02.ui.MovieListUiModel

/*
 * Created by yunji on 13/03/2020
 */
@BindingAdapter("bindMovieItem")
fun RecyclerView.bindMovieItem(data: PagedList<MovieListUiModel>?) {
    (adapter as MoviePagedRecyclerAdapter).submitList(data)
}