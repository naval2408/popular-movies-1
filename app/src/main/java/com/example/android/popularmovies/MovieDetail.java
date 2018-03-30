package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.moviesDb.Movies;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetail extends AppCompatActivity {
    Movies inputMovieObject = null;
    ImageView mImageView;
    TextView mMovieTitle;
    TextView mMovieRating;
    TextView mMovieReleaseDate;
    TextView mMoviePlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mImageView=(ImageView)findViewById(R.id.iv_movie_image);
        mMovieTitle=(TextView)findViewById(R.id.tv_movie_title);
        mMovieRating=(TextView)findViewById(R.id.tv_movie_user_rating);
        mMovieReleaseDate=(TextView)findViewById(R.id.tv_movie_release_date);
        mMoviePlot=(TextView)findViewById(R.id.tv_movie_synopsis);
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("INPUT_MOVIES_OBJECT")) {
                inputMovieObject = (Movies)intentThatStartedThisActivity.getSerializableExtra("INPUT_MOVIES_OBJECT");

            }
        }
        Picasso.with(mImageView.getContext()).load("http://image.tmdb.org/t/p/w500/"+inputMovieObject.getPosterPathValue()).into(mImageView);
        mMovieTitle.setText(inputMovieObject.getOriginalTitleValue());
        mMovieRating.setText("User Rating : "+inputMovieObject.getVoteAverageValue()+"/10");
        mMovieReleaseDate.setText("Release Date : "+inputMovieObject.getReleaseDateValue());
        mMoviePlot.setText(inputMovieObject.getOverviewValue());
        mMoviePlot.setMovementMethod(new ScrollingMovementMethod());


    }
}
