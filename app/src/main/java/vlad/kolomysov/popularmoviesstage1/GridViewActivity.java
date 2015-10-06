package vlad.kolomysov.popularmoviesstage1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
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

    List<Film> listFilm;
    TheMovieDBService theMovieDBService;
    //____________________

    private final static int ITEMS_PPAGE = 20;

    private int mVisibleThreshold = 5;
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

        loadData("1");


  /*      for (int i = mCurrentPage * ITEMS_PPAGE; i < (mCurrentPage + 1) * ITEMS_PPAGE; i++)
        {
            Log.v("film","1");

            loadData();
            //GridViewActivity.this.mGridAdapter.add(String.valueOf(Math.random() * 5000));
        }
//        GridViewActivity.this.mGridAdapter.notifyDataSetChanged();
        loadData("4");*/
    }



    public void loadData(String page)
    {
        theMovieDBService.getListFilm(Constants.API_KEY_TMDB,page)
                .subscribeOn(Schedulers.newThread()) // работаем в не главном потоке
                .observeOn(AndroidSchedulers.mainThread()) // результат передать главному потоку
                .subscribe(new Subscriber<ListMoviesModel>() {
                    @Override
                    public void onCompleted() {
                      //  Toast.makeText(GridViewActivity.this, "закончили работу!", Toast.LENGTH_LONG).show();
                        mProgressBar.setVisibility(View.GONE);
                        mLoading = false;
                        Log.v("film","2 coml");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("moviestage", "error - ошибка " + e.toString());

                    }

                    @Override
                    public void onNext(ListMoviesModel listMoviesModel) {
                        // что делаем с результатом listMoviesModel
                        listFilm = listMoviesModel.results;
                        mGridAdapter = new GridViewAdapter(GridViewActivity.this, R.layout.row_grid, listFilm);
                        mGridView.setAdapter(mGridAdapter);
                        mGridAdapter.setGridData(listFilm);
                        Log.v("film", "2 onNext");
                    }
                });

    }
//**********************************
@Override
public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
{

    if (mLoading)
    {
        if (totalItemCount > mPreviousTotal)
        {

            Log.v("film","3 if");
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
    if (!mLastPage && !mLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + mVisibleThreshold))
    {
        Log.v("film","3 if 2222");
        //new AddItemsAsyncTask().execute();
        loadData("2");
        mLoading = true;
    }
}

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {

    }






}
