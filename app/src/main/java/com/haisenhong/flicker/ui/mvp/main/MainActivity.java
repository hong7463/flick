package com.haisenhong.flicker.ui.mvp.main;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.haisenhong.flicker.R;
import com.haisenhong.flicker.common.Constants;
import com.haisenhong.flicker.data.models.responses.GetMoviesResponse;
import com.haisenhong.flicker.data.models.responses.Result;
import com.haisenhong.flicker.data.models.responses.Youtube;
import com.haisenhong.flicker.data.models.responses.YoutubeResponse;
import com.haisenhong.flicker.ui.adapters.MovieListAdapter;
import com.haisenhong.flicker.ui.mvp.youtube.YoutubePlayerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


/**
 * Created by hison7463 on 10/12/16.
 */

public class MainActivity extends AppCompatActivity implements DetailsDialog.DataBridge{

    private final String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog dialog;
    private MovieListAdapter adapter;
    private GetMoviesResponse response;

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.main_activity_swipe)
    SwipeRefreshLayout swipeLayout;

    private MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);
        checkPermission();

        presenter = new MainPresenter(this);
        presenter.retrieveData(1);

        initListener();
    }

    public void showDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
    }

    public void showResults(GetMoviesResponse response) {
        this.response = response;
        if (adapter == null) {
            adapter = new MovieListAdapter(getResults(response), this);
        }
        adapter.clear();
        adapter.addAll(getResults(response));
        if(listView.getAdapter() == null) {
            Log.d(TAG, "set adapter");
            listView.setAdapter(adapter);
        }
    }

    public void showErrorMessage() {
        Toast.makeText(this, "retrieve data error", Toast.LENGTH_SHORT).show();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                String[] perms = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE};
                requestPermissions(perms, Constants.REQUEST_PERMISSIONS);
            }
        }
    }

    private void initListener() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(response.getPage() == response.getTotal_pages()) {
                    presenter.retrieveData(1);
                    return;
                }
                presenter.retrieveData(response.getPage() + 1);
                Log.d(TAG, "on refresh");
            }
        });
    }

    private List<Result> getResults(GetMoviesResponse response) {
        List<Result> res = new ArrayList<Result>();
        Result[] results = response.getResults();
        for(Result result : results) {
            res.add(result);
        }
        return res;
    }

    @OnItemClick(R.id.list_view)
    public void itemClick(int position) {
        Result data = (Result) adapter.getItem(position);
        if(data.getVote_average() <= 5) {
            DetailsDialog dialog = new DetailsDialog();
            dialog.setData(data);
            dialog.show(getSupportFragmentManager(), Constants.DETAILS_DIALOG);
        }
        else {
            presenter.retrieveTrailers(data.getId());
        }
    }

    public void launchYoutube(YoutubeResponse response) {
        Youtube[] youtubes = response.getYoutube();
        if(youtubes == null || youtubes.length == 0) {
            Toast.makeText(this, "No trailer for this movie", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] sources = new String[youtubes.length];
        for(int i = 0; i < youtubes.length; i++) {
            sources[i] = youtubes[i].getSource();
        }
        Intent intent = new Intent(this, YoutubePlayerActivity.class);
        intent.putExtra(Constants.VIDEO_SOURCE, sources);
        Log.d(TAG, "before launch");
        startActivity(intent);
    }

    @Override
    public void update(int id) {
        presenter.retrieveTrailers(id);
    }
}
