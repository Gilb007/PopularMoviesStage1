package vlad.kolomysov.popularmoviesstage1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by admin on 29.09.15.
 */
public class DetailsActivity extends ActionBarActivity {

    private TextView mTitle;
    private ImageView mImage;
    private TextView mOverview;
    private TextView mVoteAverage;
    private TextView mReleaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);


        mTitle = (TextView) findViewById(R.id.original_title);
        mImage = (ImageView) findViewById(R.id.image_thumbnail);
        mOverview = (TextView) findViewById(R.id.overview);
        mVoteAverage = (TextView) findViewById(R.id.vote_average);
        mReleaseDate = (TextView) findViewById(R.id.release_date);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();

        String title = intent.getStringExtra("original_title");
        Log.v("detail","title = "+title);
        mTitle.setText(title);

        String image = intent.getStringExtra("poster_path");
        Log.v("detail","image = "+image);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w342/"+image).into(mImage);

        String overview = intent.getStringExtra("overview");
        mOverview.setText(overview);

        String voteAverage = intent.getStringExtra("vote_average");
        Log.v("detail","voteAverage = "+voteAverage);
        mVoteAverage.setText(voteAverage);

        String releaseDate = intent.getStringExtra("release_date");
        mReleaseDate.setText(releaseDate);


    }


}
