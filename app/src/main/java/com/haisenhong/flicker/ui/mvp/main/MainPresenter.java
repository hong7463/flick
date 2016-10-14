package com.haisenhong.flicker.ui.mvp.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.haisenhong.flicker.R;
import com.haisenhong.flicker.common.Constants;
import com.haisenhong.flicker.data.handlers.MovieListHandler;
import com.haisenhong.flicker.data.handlers.TrailersHandler;
import com.haisenhong.flicker.data.models.responses.GetMoviesResponse;
import com.haisenhong.flicker.data.models.responses.Youtube;
import com.haisenhong.flicker.data.models.responses.YoutubeResponse;
import com.haisenhong.flicker.ui.mvp.youtube.YoutubePlayerActivity;

/**
 * Created by hison7463 on 10/12/16.
 */

public class MainPresenter {

    private final String TAG = MainPresenter.class.getSimpleName();
    private MainActivity mainActivity;
    private MovieListHandler handler;
    private TrailersHandler trailersHandler;

    public void retrieveData(int pageNum) {
        String url = mainActivity.getResources().getString(R.string.retriveMoviesInfo);
        handler.retrieveData(Request.Method.GET, url, pageNum, GetMoviesResponse.class, this);
    }

    public void retrieveTrailers(int id) {
        String url = String.format(mainActivity.getResources().getString(R.string.retrieveTrailers)
                + "%d/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", id);
        trailersHandler.getTrailers(Request.Method.GET, url, YoutubeResponse.class, this);
    }

    public void launchYoutubePlayer(YoutubeResponse response) {
        mainActivity.launchYoutube(response);
    }

    public MainPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.handler = MovieListHandler.getInstance();
        this.trailersHandler = TrailersHandler.getInstance();
    }

    public void showDialog() {
        mainActivity.showDialog();
    }

    public void hideDialog() {
        mainActivity.hideDialog();
    }

    public void displayResult(GetMoviesResponse response) {
        mainActivity.showResults(response);
    }

    public void showErrorMessage() {
        mainActivity.showErrorMessage();
    }

}
