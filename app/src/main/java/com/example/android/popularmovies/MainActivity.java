package com.example.android.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.data.FavouriteContract;
import com.example.android.popularmovies.moviesDb.Movies;
import com.example.android.popularmovies.utilities.MoviesDbJsonUtlis;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.facebook.stetho.Stetho;

import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler, android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Movies>> {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.recyclerview_movie_display)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_error_message_display)
    TextView mErrorMessageDisplay;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.tv_no_movie_available)
    TextView mNoMovieAvailable;
    private MoviesAdapter mMoviesAdapter;
    private final String POPULAR_PARAMETER = "/popular";
    private final String TOP_RATED_PARAMETER = "/top_rated";
    private final String SORT_ORDER_KEY = "sortOrder";
    private final String FAVOURITE_PARAMETER = "favourite";
    private final int MOVIES_LOADER_ID = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviesthumbnail);
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMoviesAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mMoviesAdapter);
        int loaderId = MOVIES_LOADER_ID;
        android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Movies>> callback = MainActivity.this;
        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putString(SORT_ORDER_KEY,POPULAR_PARAMETER);
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);



    }

    @Override
    public void onClick(Movies movieSelected) {
        Context context = this;
        Class destinationClass = MovieDetail.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("INPUT_MOVIES_OBJECT", movieSelected);
        startActivity(intentToStartDetailActivity);

    }


    public android.support.v4.content.Loader<ArrayList<Movies>> onCreateLoader(int id, final Bundle loaderArgs) {
        return new AsyncTaskLoader<ArrayList<Movies>>(this) {
            ArrayList<Movies> mMovieslist = null;

            @Override
            protected void onStartLoading() {
                if (mMovieslist != null) {
                    deliverResult(mMovieslist);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public ArrayList<Movies> loadInBackground() {
                String sortOrder = loaderArgs.get(SORT_ORDER_KEY).toString();
                if (!sortOrder.equals(FAVOURITE_PARAMETER)) {
                    URL moviesRequestUrl = NetworkUtils.buildURL(sortOrder);
                    try {
                        String jsonMovieResponse = NetworkUtils
                                .getResponseFromHttpUrl(moviesRequestUrl);

                        ArrayList<Movies> moviesList = MoviesDbJsonUtlis.getMoviesObjectFromJson(MainActivity.this, new JSONObject(jsonMovieResponse));
                        return moviesList;

                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {

                    Cursor cursor = getContentResolver().query(FavouriteContract.FavouriteEntry.FAVOURITES_URI,
                            null,
                            null,
                            null,
                            FavouriteContract.FavouriteEntry.COLUMN_ID);

                    ArrayList<Movies> moviesList = MoviesDbJsonUtlis.getMoviesObjectFromCursor(MainActivity.this, cursor);
                    return moviesList;


                }
            }

            @Override
            public void deliverResult(ArrayList<Movies> data) {
                mMovieslist = data;
                super.deliverResult(data);
            }
        }
                ;


    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movies>> loader, ArrayList<Movies> moviesList) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMoviesAdapter.setMoviesData(moviesList);
        if (null == moviesList) {
            showErrorMessage();
        } else if (moviesList.size() == 0) {
            showNoMovieAvailableMessage();
        } else {
            showMoviesDataView();
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movies>> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mMoviesAdapter.setMoviesData(null);
            Bundle bundle = new Bundle();
            bundle.putString(SORT_ORDER_KEY,POPULAR_PARAMETER);
            getSupportLoaderManager().restartLoader(MOVIES_LOADER_ID, bundle, this);
            return true;
        }


        if (id == R.id.most_popular) {
            mMoviesAdapter.setMoviesData(null);
            Bundle bundle = new Bundle();
            bundle.putString(SORT_ORDER_KEY,POPULAR_PARAMETER);
            getSupportLoaderManager().restartLoader(MOVIES_LOADER_ID, bundle, this);
            return true;
        }

        if (id == R.id.top_rated) {
            mMoviesAdapter.setMoviesData(null);
            Bundle bundle = new Bundle();
            bundle.putString(SORT_ORDER_KEY,TOP_RATED_PARAMETER);
            getSupportLoaderManager().restartLoader(MOVIES_LOADER_ID, bundle, this);
            return true;
        }

        if(id==R.id.sort_by_favourites)
        {
            mMoviesAdapter.setMoviesData(null);
            Bundle bundle = new Bundle();
            bundle.putString(SORT_ORDER_KEY,FAVOURITE_PARAMETER);
            getSupportLoaderManager().restartLoader(MOVIES_LOADER_ID, bundle, this);
        return true;

        }

        return super.onOptionsItemSelected(item);

    }



    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showNoMovieAvailableMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mNoMovieAvailable.setVisibility(View.VISIBLE);
    }
    private void showMoviesDataView() {
        mNoMovieAvailable.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

    }


}
