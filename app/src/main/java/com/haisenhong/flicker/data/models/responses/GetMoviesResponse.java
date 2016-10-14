package com.haisenhong.flicker.data.models.responses;

import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by hison7463 on 10/12/16.
 */

public class GetMoviesResponse {

    private int page;
    private Result[] results;
    private Date dates;
    private int total_pages;
    private int total_results;

    public GetMoviesResponse() {
    }

    public GetMoviesResponse(int page, Result[] results, Date dates, int total_pages, int total_results) {
        this.page = page;
        this.results = results;
        this.dates = dates;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Result[] getResults() {
        return results;
    }

    public void setResults(Result[] results) {
        this.results = results;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @Override
    public String toString() {
        return "GetMoviesResponse{" +
                "page=" + page +
                ", results=" + Arrays.toString(results) +
                ", dates=" + dates +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}
