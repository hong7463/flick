package com.haisenhong.flicker.ui.mvp.youtube;

import android.content.Intent;

import com.android.volley.Request;
import com.haisenhong.flicker.R;
import com.haisenhong.flicker.data.handlers.TrailersHandler;
import com.haisenhong.flicker.data.models.responses.YoutubeResponse;

/**
 * Created by hison7463 on 10/13/16.
 */

public class YoutubePlayerPresenter {

    private YoutubePlayerActivity activity;

    public YoutubePlayerPresenter(YoutubePlayerActivity activity) {
        this.activity = activity;
    }

}
