package com.haisenhong.flicker.ui.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haisenhong.flicker.R;
import com.haisenhong.flicker.common.Constants;
import com.haisenhong.flicker.data.models.responses.Result;
import com.haisenhong.flicker.ui.mvp.main.DetailsDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


/**
 * Created by hison7463 on 10/12/16.
 */

public class MovieListAdapter extends ArrayAdapter {

    private final String TAG = MovieListAdapter.class.getSimpleName();

    private Context context;

    static class ViewHolder {
        @BindView(R.id.list_item_poster)
        ImageView imageView;
        @BindView(R.id.list_item_title)
        TextView title;
        @BindView(R.id.list_item_desc)
        TextView desc;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    public MovieListAdapter(List<Result> movieList, Context context) {
        super(context, android.R.layout.simple_list_item_1, movieList);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        Result data = (Result) getItem(position);
        return data.getVote_average() > 5.0? Constants.TYPE_POPULAR : Constants.TYPE_NORMAL;
    }

    @Override
    public int getViewTypeCount() {
        return Constants.MOVIE_TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Result data = (Result) getItem(position);
        ViewHolder vh;
        if(convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.main_activity_list_item, parent, false);
            convertView = getTypeOfView(getItemViewType(position));
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }
        if(getItemViewType(position) == Constants.TYPE_NORMAL) {
            vh.title.setText(data.getTitle());
            vh.desc.setText(data.getOverview());
        }

        String imgUrl = "";
        if(getItemViewType(position) == Constants.TYPE_NORMAL) {
            int orientation = context.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                imgUrl = String.format("https://image.tmdb.org/t/p/w342%s", data.getPoster_path());
            } else {
                imgUrl = String.format("https://image.tmdb.org/t/p/w500%s", data.getBackdrop_path());
            }
        }
        else {
            imgUrl = String.format("https://image.tmdb.org/t/p/w500%s", data.getBackdrop_path());
        }
        Picasso.with(context).load(imgUrl).fit().centerCrop()
                .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                .transform(new RoundedCornersTransformation(10, 0)).into(vh.imageView);
        return convertView;
    }

    private View getTypeOfView(int type) {
        if(type == Constants.TYPE_NORMAL) {
            return LayoutInflater.from(context).inflate(R.layout.main_activity_list_item, null);
        }
        if(type == Constants.TYPE_POPULAR) {
            return LayoutInflater.from(context).inflate(R.layout.main_activity_popular_list_item, null);
        }
        return null;
    }

}
