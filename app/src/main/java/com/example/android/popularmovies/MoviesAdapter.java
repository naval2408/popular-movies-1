package com.example.android.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.moviesDb.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by navalkishore on 29/03/18.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder>{
    private ArrayList<Movies> mMoviesImages;
    private final String baseURL = "http://image.tmdb.org/t/p/w500/";
    private final MoviesAdapterOnClickHandler mClickHandler;
    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForGridItem = R.layout.movies_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForGridItem, viewGroup, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);


    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {
        String urlForTheImage = baseURL+mMoviesImages.get(position).getPosterPathValue();
        Picasso.with(holder.itemView.getContext()).load(urlForTheImage).into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        if (null == mMoviesImages) return 0;
        return mMoviesImages.size();
    }
    public void setMoviesData(ArrayList<Movies> moviesData) {
        mMoviesImages = moviesData;
        notifyDataSetChanged();
    }
    public interface MoviesAdapterOnClickHandler {
        void onClick(Movies movieSelected);
    }

    public MoviesAdapter(MoviesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final ImageView mImageView;

        public MoviesAdapterViewHolder(View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.iv_movie_data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movies movieSelected = mMoviesImages.get(adapterPosition);
            mClickHandler.onClick(movieSelected);
        }
    }
}
