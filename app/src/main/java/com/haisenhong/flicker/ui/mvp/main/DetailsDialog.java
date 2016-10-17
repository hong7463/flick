package com.haisenhong.flicker.ui.mvp.main;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.haisenhong.flicker.R;
import com.haisenhong.flicker.common.Constants;
import com.haisenhong.flicker.data.models.responses.Result;
import com.haisenhong.flicker.data.models.responses.Youtube;
import com.haisenhong.flicker.data.models.responses.YoutubeResponse;
import com.haisenhong.flicker.utils.AppUtils;
import com.haisenhong.flicker.utils.ObjectHolder;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

//    @BindView(R.id.detail_poster)
//    ImageView poster;
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
//    @BindView(R.id.detail_play)
//    ImageView playBtn;

    private YouTubePlayerSupportFragment fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_dialog, container);
        ButterKnife.bind(this, view);
        setView();
        setUpYoutube();
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
//        String url = String.format("https://image.tmdb.org/t/p/w500%s", data.getBackdrop_path());
//        Picasso.with(getActivity()).load(url).fit().centerCrop().
//                placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
//                .into(poster);
        rating.setRating(data.getVote_average());
        title.setText(data.getTitle());
        desc.setText(data.getOverview());
        date.setText(getResources().getString(R.string.movie_date) + data.getRelease_date());
        if(data.getBackdrop_path() == null) {
            closeBtn.setImageResource(R.drawable.close_circle_outline_blk);
//            playBtn.setImageResource(R.drawable.play_circle_outline_blk);
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

    //    @OnClick(R.id.detail_play)
//    public void playTrailers() {
//        ((MainActivity)getActivity()).update(data.getId());
//    }

    private void setUpYoutube() {
//        DetailsPlayerFragment fragment = new DetailsPlayerFragment();
//        AppUtils.displayFragment(getActivity(), getChildFragmentManager(), fragment, R.id.player_container);
        fragment = (YouTubePlayerSupportFragment) getFragmentManager().findFragmentById(R.id.detail_player);
        fragment.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                YoutubeResponse youtubeResponse = ObjectHolder.getInstance().retrieve(Constants.TRAILER_INFO);
                Youtube[] youtubes = youtubeResponse.getYoutube();
                if(youtubes == null || youtubes.length == 0) {
                    return;
                }
                String[] sources = new String[youtubes.length];
                for(int i = 0; i < youtubes.length; i++) {
                    sources[i] = youtubes[i].getSource();
                }
                youTubePlayer.cueVideos(Arrays.asList(sources));
                youTubePlayer.setShowFullscreenButton(false);
                youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                    @Override
                    public void onPlaying() {
                        closeBtn.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPaused() {
                        closeBtn.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onStopped() {
                        closeBtn.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onBuffering(boolean b) {

                    }

                    @Override
                    public void onSeekTo(int i) {

                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        YouTubePlayerSupportFragment fragment = (YouTubePlayerSupportFragment) getFragmentManager().findFragmentById(R.id.detail_player);
        if(fragment != null) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }
}
