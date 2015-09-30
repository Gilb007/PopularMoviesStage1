package vlad.kolomysov.popularmoviesstage1;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 28.09.15.
 */
public class GridViewAdapter extends ArrayAdapter<Film> {

    private Context mContext;
    private int layoutResourceId;
    private List<Film> listFilm = new ArrayList<Film>();

    public GridViewAdapter(Context mContext, int layoutResourceId, List<Film> listFilm) {
        super(mContext, layoutResourceId, listFilm);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.listFilm = listFilm;
    }


    public void setGridData(List<Film> listFilm) {
        this.listFilm = listFilm;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_title);
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Film item = listFilm.get(position);


        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/"+item.getPosterpath()).into(holder.imageView);
        Log.v("moviestage",item.getPosterpath());
        return row;
    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
    }
}