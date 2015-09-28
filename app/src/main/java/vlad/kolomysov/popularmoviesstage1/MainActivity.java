package vlad.kolomysov.popularmoviesstage1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    GridViewAdapter customGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //set grid view item
        //Bitmap homeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.home);
        Bitmap userIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon);
        //  gridArray.add(new Item(homeIcon,"Home"));
        gridArray.add(new Item(userIcon, "User"));
        //  gridArray.add(new Item(homeIcon,"House"));
        gridArray.add(new Item(userIcon, "Friend"));
        //    gridArray.add(new Item(homeIcon,"Home"));
        gridArray.add(new Item(userIcon, "Personal"));
        //     gridArray.add(new Item(homeIcon,"Home"));
        gridArray.add(new Item(userIcon, "User"));
        //      gridArray.add(new Item(homeIcon,"Building"));
        gridArray.add(new Item(userIcon, "User"));
        //      gridArray.add(new Item(homeIcon,"Home"));
        gridArray.add(new Item(userIcon, "xyz"));
        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, gridArray);
        gridView.setAdapter(customGridAdapter);


    }
}
