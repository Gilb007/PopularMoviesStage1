package vlad.kolomysov.popularmoviesstage1;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Copyright (C) Created by Vlad Kolomysov on 28.09.15.
 */

// Main activity
public class GridViewActivity extends AppCompatActivity
{


    //condition that there must be equal to or more than 50 votes (to eliminate movies with only 1 vote)
    public static final String VOTE_COUNT_GTE = "500";

    private GridView mGridView;
    private ProgressBar mProgressBar;
    private GridViewAdapter mGridAdapter;

    List<Film> mListFilm;

    TheMovieDBService mTheMovieDBService;

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);


        mGridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        // costomize adapter, set end points
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVICE_ENDPOINT) // API base URL
                .addConverterFactory(GsonConverterFactory.create()) // A converter which uses Gson for JSON.
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // for RxJava
                .build(); // Create the Retrofit instances.

        //Create an implementation of the API defined by the service interface.
        mTheMovieDBService = retrofit.create(TheMovieDBService.class);


        mIntent = new Intent(GridViewActivity.this, DetailsViewActivity.class);

        // listener for clicking in item from grid
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // customize intent for passing data to details activity
                mIntent.putExtra( "original_title",mListFilm.get(position).original_title);
                mIntent.putExtra( "poster_path",mListFilm.get(position).poster_path);
                mIntent.putExtra( "overview",mListFilm.get(position).overview);
                mIntent.putExtra( "vote_average",mListFilm.get(position).vote_average);
                mIntent.putExtra( "release_date",mListFilm.get(position).release_date);
//                original title
//                movie poster image thumbnail
//                A plot synopsis (called overview in the api)
//                user rating (called vote_average in the api)
//                release date

                // open new activity through intent
                startActivity(mIntent);


            }
        });

        // load page == 2
        loadData("2");

    }


    // load data defined by number of pages
    // I've used modern library Retrofit and RxJAVA for ASYNChronous
    // safethreading, handling error, fast and reliable
    public void loadData(String page)
    {

        mTheMovieDBService.getListFilm(Constants.API_KEY_TMDB, page)
                .subscribeOn(Schedulers.newThread()) // downloading in background thread
                .observeOn(AndroidSchedulers.mainThread()) // results was passed to main thred for updating gridview
                .subscribe(new Subscriber<ListMoviesModel>() {
                    @Override
                    public void onCompleted() {
                        // after complete loading data GONE progress bar
                        mProgressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError(Throwable e) {
                        // if error occur - handle it
                        // if error GONE progress bar
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(GridViewActivity.this, "Error occur, please load again!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(ListMoviesModel listMoviesModel) {
                        // listMoviesModel contain result from IMDB server
                        mListFilm = listMoviesModel.results; // save list of movies
                        // initilaze adapter
                        mGridAdapter = new GridViewAdapter(GridViewActivity.this, R.layout.row_grid, mListFilm);
                        // set adapter
                        mGridView.setAdapter(mGridAdapter);
                    }
                });
    }

    // load data defined by sort criterion - Most Popular
    // I've used modern library Retrofit and RxJAVA for ASYNChronous
    // safe threading, handling error, fast and reliable
    public  void loadDataSortByMostPopular(String sorting){

        mTheMovieDBService.getListFilmSortedByMostPopular(sorting, Constants.API_KEY_TMDB)
                .subscribeOn(Schedulers.newThread()) // downloading in background thread
                .observeOn(AndroidSchedulers.mainThread()) // results was passed to main thred for updating gridview
                .subscribe(new Subscriber<ListMoviesModel>() {
                    @Override
                    public void onCompleted() {
                        // after complete loading data GONE progress bar
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // if error occur - handle it
                        // if error GONE progress bar
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(GridViewActivity.this, "Error occur, please load again!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ListMoviesModel listMoviesModel) {

                        // clear adapter filled in previous step
                        mGridAdapter.clear();
                        // listMoviesModel contain result from IMDB server
                        mListFilm = listMoviesModel.results; // save list of movies
                        // initilaze adapter
                        mGridAdapter = new GridViewAdapter(GridViewActivity.this, R.layout.row_grid, mListFilm);
                        // set adapter
                        mGridView.setAdapter(mGridAdapter);

                    }
                });
    }


    // load data defined by sort criterion - Highest Rated
    // I've used modern library Retrofit and RxJAVA for ASYNChronous
    // safe threading, handling error, fast and reliable
    public  void loadDataSortByHighestRated(String sorting){

        mTheMovieDBService.getListFilmSortedByHighestRated(sorting, VOTE_COUNT_GTE, Constants.API_KEY_TMDB)
                .subscribeOn(Schedulers.newThread()) // downloading in background thread
                .observeOn(AndroidSchedulers.mainThread()) // results was passed to main thred for updating gridview
                .subscribe(new Subscriber<ListMoviesModel>() {
                    @Override
                    public void onCompleted() {
                        // after complete loading data GONE progress bar
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // if error occur - handle it
                        // if error GONE progress bar
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(GridViewActivity.this, "Error occur, please load again!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ListMoviesModel listMoviesModel) {

                        // clear adapter filled in previous step
                        mGridAdapter.clear();
                        // listMoviesModel contain result from IMDB server
                        mListFilm = listMoviesModel.results; // save list of movies
                        // initilaze adapter
                        mGridAdapter = new GridViewAdapter(GridViewActivity.this, R.layout.row_grid, mListFilm);
                        // set adapter
                        mGridView.setAdapter(mGridAdapter);

                    }
                });
    }



    // create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    // for selected item menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the most popular and highest rated button
        int id = item.getItemId();
        // perform operation when click on most popular or hoghest rated item
        switch (id)
        {

            case R.id.action_most_popular:

                // Visible progress bar
                mProgressBar.setVisibility(View.VISIBLE);
                loadDataSortByMostPopular(Constants.most_popular);
                return true;

            case R.id.action_highest_rated:

                // Visible progress bar
                mProgressBar.setVisibility(View.VISIBLE);
                mGridAdapter.clear();
                loadDataSortByHighestRated(Constants.highest_rated);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
