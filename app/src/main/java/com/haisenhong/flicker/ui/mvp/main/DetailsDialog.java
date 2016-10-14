package com.haisenhong.flicker.ui.mvp.main;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haisenhong.flicker.R;
import com.haisenhong.flicker.data.models.responses.Result;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by hison7463 on 10/13/16.
 */

public class DetailsDialog extends DialogFragment {

    interface DataBridge {
        public void update(int id);
    }

    private Result data;

    public DetailsDialog() {
    }

    @BindView(R.id.detail_poster)
    ImageView poster;
    @BindView(R.id.detail_ratting_bar)
    RatingBar rating;
    @BindView(R.id.detail_title)
    TextView title;
    @BindView(R.id.detail_desc)
    TextView desc;
    @BindView(R.id.detail_date)
    TextView date;
    @BindView(R.id.details_close)
    ImageView closeBtn;
    @BindView(R.id.detail_play)
    ImageView playBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_dialog, container);
        ButterKnife.bind(this, view);
        setView();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(STYLE_NO_TITLE, getTheme());
    }

    @OnClick(R.id.details_close)
    public void closeDialog() {
        this.dismiss();
    }

    private void setView() {
        String url = String.format("https://image.tmdb.org/t/p/w500%s", data.getBackdrop_path());
        Picasso.with(getActivity()).load(url).fit().centerCrop().
                placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                .into(poster);
        rating.setRating(data.getVote_average());
        title.setText(data.getTitle());
        desc.setText(data.getOverview());
        date.setText(getResources().getString(R.string.movie_date) + data.getRelease_date());
        if(data.getBackdrop_path() == null) {
            closeBtn.setImageResource(R.drawable.close_circle_outline_blk);
            playBtn.setImageResource(R.drawable.play_circle_outline_blk);
        }
    }

    public void setData(Result data) {
        this.data = data;
    }

    @Override
    public boolean isCancelable() {
        return false;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @OnClick(R.id.detail_play)
    public void playTrailers() {
        ((MainActivity)getActivity()).update(data.getId());
    }
}
