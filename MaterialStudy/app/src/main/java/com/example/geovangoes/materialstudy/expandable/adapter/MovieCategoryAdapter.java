package com.example.geovangoes.materialstudy.expandable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geovangoes.materialstudy.R;
import com.example.geovangoes.materialstudy.expandable.model.MovieCategory;
import com.example.geovangoes.materialstudy.expandable.model.Movies;
import com.example.geovangoes.materialstudy.expandable.holder.MoviesViewHolder;
import com.example.geovangoes.materialstudy.expandable.ParentListItem;
import com.example.geovangoes.materialstudy.expandable.holder.MovieCategoryViewHolder;

import java.util.List;

public class MovieCategoryAdapter extends ExpandableRecyclerAdapter<MovieCategoryViewHolder, MoviesViewHolder> {

    private LayoutInflater mInflator;

    public MovieCategoryAdapter(Context context, List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public MovieCategoryViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View movieCategoryView = mInflator.inflate(R.layout.movie_category_view, parentViewGroup, false);
        return new MovieCategoryViewHolder(movieCategoryView);
    }

    @Override
    public MoviesViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View moviesView = mInflator.inflate(R.layout.movies_view, childViewGroup, false);
        return new MoviesViewHolder(moviesView);
    }

    @Override
    public void onBindParentViewHolder(MovieCategoryViewHolder movieCategoryViewHolder, int position, ParentListItem parentListItem) {
        MovieCategory movieCategory = (MovieCategory) parentListItem;
        movieCategoryViewHolder.bind(movieCategory);
    }

    @Override
    public void onBindChildViewHolder(MoviesViewHolder moviesViewHolder, int position, Object childListItem) {
        Movies movies = (Movies) childListItem;
        moviesViewHolder.bind(movies);
    }
}
