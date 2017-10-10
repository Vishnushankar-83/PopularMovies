package com.app.vanajainfotech.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by vishnushankar on 10/07/17
 *
 *
 */

public class MovieDetailActivity extends AppCompatActivity {

    Movie movie;

    TextView movieTitle;
    TextView movieOverView;
    ImageView thumbnail;
    TextView movieDate;
    TextView movieAverage;

    /**
     * The method is used to create and populate the movie details screen.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movie = getIntent().getExtras().getParcelable("movie");

        movieTitle = (TextView) findViewById(R.id.movie_title);
        movieOverView = (TextView) findViewById(R.id.movie_overview);
        thumbnail = (ImageView) findViewById(R.id.movie_thumbnail);
        movieDate = (TextView) findViewById(R.id.movie_date);
        movieAverage = (TextView) findViewById(R.id.movie_average);

        movieTitle.setText(movie.getTitle());
        movieOverView.setText(movie.getOverView());

        Picasso.with(this)
                .load(movie.getPosterUrl())
                .into(thumbnail);

        movieDate.setText(movie.getRelease_date());
        movieAverage.setText(movie.getVote_Average().toString());

    }
}
