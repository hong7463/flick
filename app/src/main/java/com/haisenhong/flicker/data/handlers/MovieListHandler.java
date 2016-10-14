package com.haisenhong.flicker.data.handlers;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.haisenhong.flicker.data.models.responses.GetMoviesResponse;
import com.haisenhong.flicker.data.network.GsonRequest;
import com.haisenhong.flicker.ui.mvp.main.MainPresenter;
import com.haisenhong.flicker.utils.VolleyUtils;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by hison7463 on 10/12/16.
 */

public class MovieListHandler {

    private MainPresenter presenter;

    private MovieListHandler() {
    }

    private static class MovieLstHandlerInner {
        static MovieListHandler INSTANCE = new MovieListHandler();
    }

    public static MovieListHandler getInstance() {
        return MovieLstHandlerInner.INSTANCE;
    }

    public void retrieveData(int method, String url, int pageNum, Class<GetMoviesResponse> responseType, MainPresenter presenter) {

        this.presenter = presenter;
        presenter.showDialog();

        url = url + "&page=" + pageNum;

        Log.d(TAG, "start the call of service");

        GsonRequest<GetMoviesResponse> gsonRequest = new GsonRequest<>(Request.Method.GET, url, null,
                GetMoviesResponse.class, getSuccessListener(), getErrorListener());
        VolleyUtils.getInstance().add(gsonRequest);
    }

    private Response.Listener<GetMoviesResponse> getSuccessListener() {
        return new Response.Listener<GetMoviesResponse>() {

            @Override
            public void onResponse(GetMoviesResponse response) {
                presenter.displayResult(response);
                presenter.hideDialog();
                Log.d(TAG, "end the call of service");
                Log.d(TAG, response.toString());
            }
        };
    }

    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.hideDialog();
                presenter.showErrorMessage();
                Log.d(TAG, "error happened");
            }
        };
    }

}
