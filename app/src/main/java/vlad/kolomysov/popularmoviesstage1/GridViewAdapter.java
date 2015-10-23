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
 * Copyright (C) Created by Vlad Kolomysov on 28.09.15.
 */

/**
 * Adapter for our GridViewActivity
 */

public class GridViewAdapter extends ArrayAdapter<Film> {

    private Context mContext;
    private int mLayoutResourceId;
    private List<Film> mListFilm = new ArrayList<Film>();

    // Adapter's constructor
    public GridViewAdapter(Context mContext, int layoutResourceId, List<Film> listFilm) {
        super(mContext, layoutResourceId, listFilm);
        this.mLayoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mListFilm = listFilm;
    }


    // for each item in grid set image poster and returt row's view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;

        // set holder
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Film item = mListFilm.get(position);

// Use Picasso for loading image
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/"+item.getPosterpath()).into(holder.imageView);

        return row;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}