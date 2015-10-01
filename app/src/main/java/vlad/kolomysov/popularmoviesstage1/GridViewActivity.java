package vlad.kolomysov.popularmoviesstage1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GridViewActivity extends AppCompatActivity
{

    private GridView mGridView;
    private ProgressBar mProgressBar;
    private GridViewAdapter mGridAdapter;



    List<Film> listFilm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);


        mGridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Initialize with empty data
        //  mGridData = new ArrayList<>();
        //Start download

        mProgressBar.setVisibility(View.VISIBLE);

        /*//обработка клика
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //Get item at position
                GridItem item = (GridItem) parent.getItemAtPosition(position);

                //Pass the image title and url to DetailsActivity
                Intent intent = new Intent(GridViewActivity.this, DetailsActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("image", item.getImage());

                //Start details activity
                startActivity(intent);
            }
        });*/

        // 3
        // настраеваем адаптер, присваиваем URL
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVICE_ENDPOINT) // API base URL
                .addConverterFactory(GsonConverterFactory.create()) // A converter which uses Gson for JSON.
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build(); // Create the Retrofit instances.

        // 4
        //Create an implementation of the API defined by the service interface.
        TheMovieDBService theMovieDBService = retrofit.create(TheMovieDBService.class);

        theMovieDBService.getListFilm(Constants.API_KEY_TMDB,"1")
                .subscribeOn(Schedulers.newThread()) // работаем в не главном потоке
                .observeOn(AndroidSchedulers.mainThread()) // результат передать главному потоку
                .subscribe(new Subscriber<ListMoviesModel>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(GridViewActivity.this, "закончили работу!", Toast.LENGTH_LONG).show();
                        mProgressBar.setVisibility(View.GONE);
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
                    }
                });

    }





}
