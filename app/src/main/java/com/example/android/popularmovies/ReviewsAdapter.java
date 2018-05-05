package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.moviesDb.Reviews;
import com.example.android.popularmovies.moviesDb.Trailers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsAdapterViewHolder>{
    private ArrayList<Reviews> mReviewsText;
    @Override
    public ReviewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.reviews_details;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForGridItem, parent, shouldAttachToParentImmediately);
        return new ReviewsAdapter.ReviewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapterViewHolder holder, int position) {
        String reviewForMovie ="Author : " +mReviewsText.get(position).getAuthorValue()+"\n"+mReviewsText.get(position).getContentValue()+"\n\n";
        holder.mTextViewForReviews.setText(reviewForMovie);

    }

    @Override
    public int getItemCount() {
        if (null == mReviewsText) return 0;
        return mReviewsText.size();
    }

    public class ReviewsAdapterViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView mTextViewForReviews;


        public ReviewsAdapterViewHolder(View itemView) {
            super(itemView);
            mTextViewForReviews=(TextView) itemView.findViewById(R.id.tv_reviews_value);

        }

    }
    public void setReviewsData(ArrayList<Reviews> reviewsData) {
        mReviewsText = reviewsData;
        notifyDataSetChanged();
    }

}
