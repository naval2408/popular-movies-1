package com.example.android.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.data.FavouriteContract;
import com.example.android.popularmovies.moviesDb.Movies;
import com.example.android.popularmovies.moviesDb.Reviews;
import com.example.android.popularmovies.moviesDb.Trailers;
import com.example.android.popularmovies.utilities.MoviesDbJsonUtlis;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.example.android.popularmovies.utilities.ReviewsDbJsonUtils;
import com.example.android.popularmovies.utilities.TrailersDbJsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetail extends AppCompatActivity implements TrailersAdapter.TralersAdapterOnClickHandler,LoaderManager.LoaderCallbacks<ArrayList<Trailers>>{
    Movies inputMovieObject = null;
    @BindView(R.id.iv_movie_image)
    ImageView mImageView;
    @BindView(R.id.tv_movie_title)
    TextView mMovieTitle;
    @BindView(R.id.tv_movie_user_rating)
    TextView mMovieRating;
    @BindView(R.id.tv_movie_release_date)
    TextView mMovieReleaseDate;
    @BindView(R.id.tv_movie_synopsis)
    TextView mMoviePlot;
    @BindView(R.id.recyclerview_trailers_display)
    RecyclerView trailersRecyclerView;
    @BindView(R.id.pb_loading_indicator_trailer)
    ProgressBar trailerProgressBar;
    @BindView(R.id.tv_error_message_display_trailers)
    TextView mTrailerError;
    @BindView(R.id.tv_error_message_no_trailers)
    TextView mNoTrailer;
    @BindView(R.id.recyclerview_reviews_display)
    RecyclerView reviewsRecyclerView;
    @BindView(R.id.pb_loading_indicator_reviews)
    ProgressBar reviewsProgressBar;
     @BindView(R.id.tv_error_message_display_reviews)
     TextView mReviewsError;
    @BindView(R.id.tv_error_message_no_reviews)
    TextView mNoReview;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    private final int TRAILER_LOADER_ID=20;
    private final int REVIEWS_LOADER_ID=30;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @BindView(R.id.button_add_favourite)
    Button mAddfavourite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("INPUT_MOVIES_OBJECT")) {
                inputMovieObject = (Movies)intentThatStartedThisActivity.getSerializableExtra("INPUT_MOVIES_OBJECT");

            }
        }
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        editor=pref.edit();
        if(pref.getBoolean(getString(R.string.favorites_key)+inputMovieObject.getIdValue(),false))
        {
            setButtonText();
        }

        Picasso.with(mImageView.getContext()).load("http://image.tmdb.org/t/p/w500/"+inputMovieObject.getPosterPathValue()).into(mImageView);
        mMovieTitle.setText(inputMovieObject.getOriginalTitleValue());
        mMovieRating.setText("User Rating : "+inputMovieObject.getVoteAverageValue()+"/10");
        mMovieReleaseDate.setText("Release Date : "+inputMovieObject.getReleaseDateValue());
        mMoviePlot.setText(inputMovieObject.getOverviewValue());
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MovieDetail.this, LinearLayoutManager.HORIZONTAL, false);
        trailersRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        trailersRecyclerView.setHasFixedSize(true);
        trailersAdapter= new TrailersAdapter(this);
        trailersRecyclerView.setAdapter(trailersAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MovieDetail.this,LinearLayoutManager.VERTICAL,false);
        reviewsRecyclerView.setLayoutManager(linearLayoutManager);
        reviewsRecyclerView.setHasFixedSize(true);
        reviewsAdapter= new ReviewsAdapter();
        reviewsRecyclerView.setAdapter(reviewsAdapter);
        int loaderIdForTrailers = TRAILER_LOADER_ID;
        int loaderIdForReviews= REVIEWS_LOADER_ID;
        android.support.v4.app.LoaderManager.LoaderCallbacks callback = MovieDetail.this;
        getSupportLoaderManager().initLoader(loaderIdForTrailers, null, callback);
        getSupportLoaderManager().initLoader(loaderIdForReviews, null, callback);


    }

    public android.support.v4.content.Loader onCreateLoader(int id, final Bundle loaderArgs)
    {
        if(id==TRAILER_LOADER_ID) {
            return new AsyncTaskLoader<ArrayList<Trailers>>(this) {
                ArrayList<Trailers> mTrailerslist = null;

                @Override
                protected void onStartLoading() {
                    if (mTrailerslist != null) {
                        deliverResult(mTrailerslist);
                    } else {
                        trailerProgressBar.setVisibility(View.VISIBLE);
                        forceLoad();
                    }
                }

                @Override
                public ArrayList<Trailers> loadInBackground() {
                    URL trailerRequestUrl = NetworkUtils.buildTrailerURL(Integer.toString(inputMovieObject.getIdValue()));
                    try {
                        String jsonTrailerResponse = NetworkUtils
                                .getResponseFromHttpUrl(trailerRequestUrl);

                        ArrayList<Trailers> trailersList = TrailersDbJsonUtils.getTrailersObjectFromJson(MovieDetail.this, new JSONObject(jsonTrailerResponse));
                        return trailersList;

                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                public void deliverResult(ArrayList<Trailers> data) {
                    mTrailerslist = data;
                    super.deliverResult(data);
                }
            };
        }

        else if(id==REVIEWS_LOADER_ID)
        {
            return new AsyncTaskLoader<ArrayList<Reviews>>(this) {
                ArrayList<Reviews> mReviewslist = null;

                @Override
                protected void onStartLoading() {
                    if (mReviewslist != null) {
                        deliverResult(mReviewslist);
                    } else {
                        reviewsProgressBar.setVisibility(View.VISIBLE);
                        forceLoad();
                    }
                }

                @Override
                public ArrayList<Reviews> loadInBackground() {
                    URL reviewRequestUrl = NetworkUtils.buildReviewsURL(Integer.toString(inputMovieObject.getIdValue()));
                    try {
                        String jsonReviewResponse = NetworkUtils
                                .getResponseFromHttpUrl(reviewRequestUrl);

                        ArrayList<Reviews> reviewsList = ReviewsDbJsonUtils.getReviewsObjectFromJson(MovieDetail.this, new JSONObject(jsonReviewResponse));
                        return reviewsList;

                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                public void deliverResult(ArrayList<Reviews> data) {
                    mReviewslist = data;
                    super.deliverResult(data);
                }
            };
        }

        return null;

        }







    @Override
    public void onLoadFinished(Loader loader, ArrayList data) {
        if(loader.getId()==TRAILER_LOADER_ID) {
            trailerProgressBar.setVisibility(View.INVISIBLE);
            trailersAdapter.setTrailersData(data);
            if (null == data) {
                showErrorMessage();
            } else if (data.size() == 0) {
                showNoTrailersView();

            } else {
                showTrailersDataView();
            }
        }
        else  if(loader.getId()==REVIEWS_LOADER_ID) {
            reviewsProgressBar.setVisibility(View.INVISIBLE);
            reviewsAdapter.setReviewsData(data);
            if (null == data) {
                showReviewsErrorMessage();
            } else if (data.size() == 0) {
                showNoReviewsView();

            } else {
                showReviewsDataView();
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Trailers>> loader) {

    }

    @Override
    public void onClick(Trailers trailerSelected) {
        Context context = this;
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerSelected.getKeyValue()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + trailerSelected.getKeyValue()));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }


    }
    private void showTrailersDataView() {
        mTrailerError.setVisibility(View.INVISIBLE);
        trailersRecyclerView.setVisibility(View.VISIBLE);

    }
    private void showErrorMessage() {
        trailersRecyclerView.setVisibility(View.INVISIBLE);
        mTrailerError.setVisibility(View.VISIBLE);
    }
    private void showNoTrailersView() {
        mNoTrailer.setVisibility(View.VISIBLE);
    }

    private void showReviewsDataView() {
        mReviewsError.setVisibility(View.INVISIBLE);
        reviewsRecyclerView.setVisibility(View.VISIBLE);

    }
    private void showReviewsErrorMessage() {
        reviewsRecyclerView.setVisibility(View.INVISIBLE);
        mReviewsError.setVisibility(View.VISIBLE);
    }
    private void showNoReviewsView() {
        mNoReview.setVisibility(View.VISIBLE);
    }

 @OnClick(R.id.button_add_favourite)
 void onFavouritesClicked() {
        boolean isFavorite = pref.getBoolean(getString(R.string.favorites_key)+inputMovieObject.getIdValue(),false);
     if(!isFavorite)
     {
         editor.putBoolean(getString(R.string.favorites_key)+inputMovieObject.getIdValue(),true);
         editor.apply();
         setButtonText();
         ContentValues contentValues = new ContentValues();
         contentValues.put(FavouriteContract.FavouriteEntry.COLUMN_ID, Integer.toString(inputMovieObject.getIdValue()));
         contentValues.put(FavouriteContract.FavouriteEntry.COLUMN_TITLE, inputMovieObject.getTitleValue());
         Uri uri = getContentResolver().insert(FavouriteContract.FavouriteEntry.FAVOURITES_URI, contentValues);
         if(uri != null) {
             Toast.makeText(getBaseContext(), "Successfully added as favourites !", Toast.LENGTH_LONG).show();
         }
     }
     else
     {
         Toast.makeText(this,"Already added as favourite !",Toast.LENGTH_SHORT).show();
     }


 }
    private void setButtonText() {
        mAddfavourite.setText("Added as Favourite !");
    }



}

