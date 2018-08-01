package com.example.geovangoes.materialstudy.expandable.holder;

import android.view.View;
import android.widget.TextView;
import com.example.geovangoes.materialstudy.R;
import com.example.geovangoes.materialstudy.expandable.model.Movies;

public class MoviesViewHolder extends ChildViewHolder {

    private TextView mMoviesTextView;

    public MoviesViewHolder(View itemView) {
        super(itemView);
        mMoviesTextView = (TextView) itemView.findViewById(R.id.tv_movies);
    }

    public void bind(Movies movies) {
        mMoviesTextView.setText(movies.getName());
    }
}
