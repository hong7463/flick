package com.haisenhong.flicker.data.handlers;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.haisenhong.flicker.data.models.responses.YoutubeResponse;
import com.haisenhong.flicker.data.network.GsonRequest;
import com.haisenhong.flicker.ui.mvp.main.MainPresenter;
import com.haisenhong.flicker.utils.VolleyUtils;

/**
 * Created by hison7463 on 10/13/16.
 */

public class TrailersHandler {

    private final String TAG = TrailersHandler.class.getSimpleName();

    private MainPresenter presenter;

    private static class TrailersHandlerInner {
        private static TrailersHandler INSTANCE = new TrailersHandler();
    }

    public static TrailersHandler getInstance() {
        return TrailersHandlerInner.INSTANCE;
    }

    public void getTrailers(int method, String url, Class<YoutubeResponse> responseType, MainPresenter presenter) {

        this.presenter = presenter;

        GsonRequest<YoutubeResponse> gsonRequest = new GsonRequest<>(method, url, null, responseType,
                getSuccessListener(), getErrorListener());
        VolleyUtils.getInstance().add(gsonRequest);

    }

    private Response.Listener<YoutubeResponse> getSuccessListener() {
        return new Response.Listener<YoutubeResponse>() {
            @Override
            public void onResponse(YoutubeResponse response) {
                Log.d(TAG, response.toString());
                presenter.launchYoutubePlayer(response);
            }
        };
    }

    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.showErrorMessage();
            }
        };
    }

}
