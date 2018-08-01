package com.example.geovangoes.materialstudy.expandable.model;

import com.example.geovangoes.materialstudy.expandable.ParentListItem;

import java.util.List;

public class MovieCategory implements ParentListItem {
    private String mName;
    private List<Movies> mMovies;

    public MovieCategory(String name, List<Movies> movies) {
        mName = name;
        mMovies = movies;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<?> getChildItemList() {
        return mMovies;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
