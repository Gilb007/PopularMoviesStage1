package vlad.kolomysov.popularmoviesstage1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GridViewActivity extends AppCompatActivity implements AbsListView.OnScrollListener
{
    private GridView mGridView;
    private ProgressBar mProgressBar;
    private GridViewAdapter mGridAdapter;

    List<Film> mListFilm;
    TheMovieDBService theMovieDBService;

    Intent mIntent;
    //____________________

    private final static int ITEMS_PPAGE = 20;

    private int mVisibleThreshold = 2;
    private int mCurrentPage = 0;
    private int mPreviousTotal = 0;
    private boolean mLoading = true;
    private boolean mLastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        Log.v("film", "1");


        mGridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mGridView.setOnScrollListener(this);
        mProgressBar.setVisibility(View.VISIBLE);

        // 3
        // настраеваем адаптер, присваиваем URL
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVICE_ENDPOINT) // API base URL
                .addConverterFactory(GsonConverterFactory.create()) // A converter which uses Gson for JSON.
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build(); // Create the Retrofit instances.

        // 4
        //Create an implementation of the API defined by the service interface.
        theMovieDBService = retrofit.create(TheMovieDBService.class);

        loadData("2");

        mIntent = new Intent(GridViewActivity.this, DetailsActivity.class);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("detail","title = "+mListFilm.get(position).original_title);
                mIntent.putExtra( "original_title",mListFilm.get(position).original_title);
                mIntent.putExtra( "poster_path",mListFilm.get(position).poster_path);
                mIntent.putExtra( "overview",mListFilm.get(position).overview);
                mIntent.putExtra( "vote_average",mListFilm.get(position).vote_average);
                mIntent.putExtra( "release_date",mListFilm.get(position).release_date);

                startActivity(mIntent);


            }
        });


    }



    public void loadData(String page)
    {

        Log.v("filmload","loadData method");
        theMovieDBService.getListFilm(Constants.API_KEY_TMDB,page)
                .subscribeOn(Schedulers.newThread()) // работаем в не главном потоке
                .observeOn(AndroidSchedulers.mainThread()) // результат передать главному потоку
                .subscribe(new Subscriber<ListMoviesModel>() {
                    @Override
                    public void onCompleted()
                    {
                      //  Toast.makeText(GridViewActivity.this, "закончили работу!", Toast.LENGTH_LONG).show();
                        mProgressBar.setVisibility(View.GONE);
                        mLoading = false;
                        Log.v("film","loadData method - onCompleted()");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.v("moviestage", "error - ошибка " + e.toString());

                    }
                    @Override
                    public void onNext(ListMoviesModel listMoviesModel) {
                        // что делаем с результатом listMoviesModel
                        mListFilm = listMoviesModel.results;
                        mGridAdapter = new GridViewAdapter(GridViewActivity.this, R.layout.row_grid, mListFilm);
                        mGridView.setAdapter(mGridAdapter);
                       // mGridAdapter.setGridData(mListFilm);
                        Log.v("film","loadData method - onNext");

                    }
                });

    }

    public  void loadDataSortBy(String sorting){

        theMovieDBService.getListFilmSortedBy(sorting, Constants.API_KEY_TMDB)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ListMoviesModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ListMoviesModel listMoviesModel) {

                        mGridAdapter.clear();
                        mListFilm = listMoviesModel.results;
                        mGridAdapter = new GridViewAdapter(GridViewActivity.this, R.layout.row_grid, mListFilm);
                        mGridView.setAdapter(mGridAdapter);
                        Log.v("menu", "most pop!!!");
                       // mGr


                    }
                });
    }
//**********************************
@Override
public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
{

    Log.v("film","mLastPage = "+mLastPage);
    Log.v("film", "mLoading = " + mLoading);
    Log.v("film","totalItemCount = "+totalItemCount);
    Log.v("film","visibleItemCount = "+visibleItemCount);
    Log.v("film","firstVisibleItem = "+firstVisibleItem);
    Log.v("film","mVisibleThreshold = "+mVisibleThreshold);

    Log.v("film","onScroll method");
    if (mLoading == true)
    {
        if (totalItemCount > mPreviousTotal)
        {

            Log.v("film","mLoading = true"+mLoading);
            mLoading = false;
            mPreviousTotal = totalItemCount;
            mCurrentPage++;

            // Find your own condition in order to know when you
            // have finished displaying all items
            if (mCurrentPage + 1 > 50) {
                mLastPage = true;
            }
        }
    }
    /*if (!mLastPage && !mLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + mVisibleThreshold))
    {

        Log.v("film","(firstVisibleItem + mVisibleThreshold) = "+firstVisibleItem + mVisibleThreshold);

        loadData("2");
        mLoading = true;
    }*/

    if (firstVisibleItem == 18){
        loadData("2");
        mLoading = true;
    }
}

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.action_most_popular:
                Log.v("menu","most pop");
                mGridAdapter.clear();
                loadDataSortBy(Constants.most_popular);
                return true;
            case R.id.action_highest_rated:
              //  infoTextView.setText("Вы выбрали кошку!");
                mGridAdapter.clear();
                loadDataSortBy(Constants.highest_rated);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }






}
