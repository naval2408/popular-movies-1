package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.moviesDb.Movies;
import com.example.android.popularmovies.moviesDb.Trailers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailersAdapter  extends RecyclerView.Adapter<TrailersAdapter.TrailersAdapterViewHolder>{
    private ArrayList<Trailers> mTrailersImages;
    private final String baseURL = "https://img.youtube.com/vi/";
    private final TralersAdapterOnClickHandler mClickHandler;
    public TrailersAdapter(TralersAdapterOnClickHandler handler) {
        mClickHandler = handler;
    }

    @Override
    public TrailersAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.trailers_thumbnail;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForGridItem, parent, shouldAttachToParentImmediately);
        return new TrailersAdapter.TrailersAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailersAdapterViewHolder holder, int position) {
        String urlForTheImage = baseURL+mTrailersImages.get(position).getKeyValue()+"/mqdefault.jpg";
        Picasso.with(holder.itemView.getContext()).load(urlForTheImage).into(holder.mImageViewForTrailers);

    }

    @Override
    public int getItemCount() {
        if (null == mTrailersImages) return 0;
        return mTrailersImages.size();
    }

    public void setTrailersData(ArrayList<Trailers> trailersData) {
        mTrailersImages = trailersData;
        notifyDataSetChanged();
    }

    public class TrailersAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final ImageView mImageViewForTrailers;


        public TrailersAdapterViewHolder(View itemView) {
            super(itemView);
            mImageViewForTrailers=(ImageView)itemView.findViewById(R.id.iv_trailer_thumbnail);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Trailers trailersSelected = mTrailersImages.get(adapterPosition);
            mClickHandler.onClick(trailersSelected);
    }
}
    public interface TralersAdapterOnClickHandler {
        void onClick(Trailers movieSelected);
    }

}
