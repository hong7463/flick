package com.haisenhong.flicker.ui.mvp.youtube;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.haisenhong.flicker.R;
import com.haisenhong.flicker.common.Constants;
import com.haisenhong.flicker.data.models.responses.Youtube;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hison7463 on 10/13/16.
 */

public class YoutubePlayerActivity extends YouTubeBaseActivity {

    private final String TAG = YoutubePlayerActivity.class.getSimpleName();

    @BindView(R.id.youtube_player)
    YouTubePlayerView youTubePlayer;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.youtube_player_activity);

        Log.d(TAG, "in Youtube player");
        ButterKnife.bind(this);
        initYoutubeView();
    }

    private void initYoutubeView() {
        youTubePlayer.initialize(getResources().getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                String[] sources = getIntent().getExtras().getStringArray(Constants.VIDEO_SOURCE);
                Log.d(TAG, sources.toString());
                youTubePlayer.cueVideos(Arrays.asList(sources));
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}
