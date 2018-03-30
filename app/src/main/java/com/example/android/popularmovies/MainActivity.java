package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.moviesDb.Movies;
import com.example.android.popularmovies.utilities.MoviesDbJsonUtlis;
import com.example.android.popularmovies.utilities.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler{
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private MoviesAdapter mMoviesAdapter;
    private String placeholderForLastCall;
    private final String POPULAR_PARAMETER="/popular";
    private final String TOP_RATED_PARAMETER="/top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviesthumbnail);
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerview_movie_display);
        mErrorMessageDisplay=(TextView)findViewById(R.id.tv_error_message_display);
        GridLayoutManager layoutManager= new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        mLoadingIndicator=(ProgressBar)findViewById(R.id.pb_loading_indicator);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMoviesAdapter= new MoviesAdapter(this);
        mRecyclerView.setAdapter(mMoviesAdapter);
        loadMovieData("/popular");

    }

    private void loadMovieData(String queryParameter) {

        showMoviesDataView();
        new FetchMoviesTask().execute(queryParameter);
        placeholderForLastCall=queryParameter;
    }

    @Override
    public void onClick(Movies movieSelected) {
        Context context = this;
        Class destinationClass = MovieDetail.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("INPUT_MOVIES_OBJECT",movieSelected);
        startActivity(intentToStartDetailActivity);

    }

    public class FetchMoviesTask extends AsyncTask<String,Void,ArrayList<Movies>>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movies> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String sortOrder = params[0];
            URL moviesRequestUrl = NetworkUtils.buildURL(sortOrder);
            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(moviesRequestUrl);

                ArrayList<Movies> moviesList = MoviesDbJsonUtlis.getMoviesObjectFromJson(MainActivity.this,new JSONObject(jsonMovieResponse));
                return moviesList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        protected void onPostExecute(ArrayList<Movies> moviesData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (moviesData != null) {
                showMoviesDataView();
                mMoviesAdapter.setMoviesData(moviesData);
            } else {
                showErrorMessage();
            }
        }
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
            loadMovieData(placeholderForLastCall);
            return true;
        }


        if (id == R.id.most_popular) {
            mMoviesAdapter.setMoviesData(null);
            loadMovieData(POPULAR_PARAMETER);
            return true;
        }

        if (id == R.id.top_rated) {
            mMoviesAdapter.setMoviesData(null);
            loadMovieData(TOP_RATED_PARAMETER);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void showMoviesDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

    }
    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

}
