package com.app.vanajainfotech.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * The Adapter class for popular movie App.
 * Created by vishnushankar on 10/07/17
 *
 *
 */

class MovieAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private ArrayList<Movie> gridData = new ArrayList<>();

    /**
     *
     * @param context
     * @param layoutRessourceId
     * @param gridData
     */

    public MovieAdapter(Context context, int layoutRessourceId, ArrayList<Movie> gridData) {
        super(context, layoutRessourceId, gridData);
        this.context = context;
        this.gridData = gridData;
    }


    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img;
        if (convertView == null) {
            img = new ImageView(context);
            img.setAdjustViewBounds(true);
            convertView = img;
        } else {
            img = (ImageView) convertView;
        }

        Movie movie = gridData.get(position);

        Picasso.with(context)
                .load(movie.getPosterUrl())
                .into(img);
        return convertView;
    }
}
