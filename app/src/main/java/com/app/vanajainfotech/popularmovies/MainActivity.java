package com.app.vanajainfotech.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.app.vanajainfotech.popularmovies.util.NetworkUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by vishnushankar on 10/07/17
 *
 *
 */
public class MainActivity extends AppCompatActivity {

    private GridView gridview;
    private TextView errorTextView;
    private ProgressBar loadingIndicator;
    private ArrayList<Movie> gridData;
    private MovieAdapter mAdapter;
    private Menu mainMenu;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridview = (GridView) findViewById(R.id.gridview);

        errorTextView = (TextView) findViewById(R.id.error_text);
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        gridData = new ArrayList<>();

        mAdapter = new MovieAdapter(this, 0, gridData);
        gridview.setAdapter(mAdapter);



        if(savedInstanceState == null){
            requestMoviesBy("popular");

        } else {
            gridData = savedInstanceState.getParcelableArrayList("savedMovies");
            mAdapter = new MovieAdapter(this, 0, gridData);
            gridview.setAdapter(mAdapter);
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = gridData.get(position);
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("savedMovies", gridData);
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_by_popular:
              //  mainMenu.findItem(R.id.sort_by_average).setVisible(true);
               // mainMenu.findItem(R.id.sort_by_popular).setVisible(false);
                gridData = new ArrayList<>();
                requestMoviesBy("popular");
                return true;
            case R.id.sort_by_average:
               // mainMenu.findItem(R.id.sort_by_average).setVisible(false);
               // mainMenu.findItem(R.id.sort_by_popular).setVisible(true);
                gridData = new ArrayList<>();
                requestMoviesBy("top_rated");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void requestMoviesBy(String sortBy) {
        if (isNetworkAvailable()){
            URL movieSearchUrl = NetworkUtils.buildUrl(sortBy);
            new MovieQueryTask().execute(movieSearchUrl);
        } else {
            Toast.makeText(this, R.string.internet_connection_error, Toast.LENGTH_LONG).show();
        }

    }

    private void showJsonDataView() {
        // First, make sure the error is invisible
        errorTextView.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        gridview.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        // First, hide the currently visible data
        gridview.setVisibility(View.INVISIBLE);
        // Then, show the error
        errorTextView.setVisibility(View.VISIBLE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    public class MovieQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String movieSearchResults = null;
            try {
                movieSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieSearchResults;
        }

        @Override
        protected void onPostExecute(String movieSearchResults) {

            loadingIndicator.setVisibility(View.INVISIBLE);

            if (movieSearchResults != null && !movieSearchResults.equals("")) {
                showJsonDataView();
                parseResult(movieSearchResults);
            } else {
                showErrorMessage();
            }
        }
    }

    private void parseResult(String results){
        try {

            JSONObject forecast = new JSONObject(results);
            JSONArray response = forecast.getJSONArray("results");
            Movie movie;

            for (int i = 0; i < response.length(); i++){
                JSONObject result = response.getJSONObject(i);
                String poster = result.getString("poster_path");
                String title = result.getString("original_title");
                String overView = result.getString("overview");
                Double vote_Average = result.getDouble("vote_average");
                String release_date = result.getString("release_date");
                movie = new Movie();
                movie.setPosterUrl(poster);
                movie.setTitle(title);
                movie.setOverView(overView);
                movie.setVote_Average(vote_Average);
                movie.setRelease_date(release_date);
                gridData.add(movie);
            }

            mAdapter = new MovieAdapter(this, 0, gridData);
            gridview.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}