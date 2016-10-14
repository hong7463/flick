package com.haisenhong.flicker.data.models.responses;

import java.util.Arrays;

/**
 * Created by hison7463 on 10/13/16.
 */

public class YoutubeResponse {

    private int id;
    private Youtube[] youtube;

    public YoutubeResponse() {
    }

    public YoutubeResponse(int id, Youtube[] youtube) {
        this.id = id;
        this.youtube = youtube;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Youtube[] getYoutube() {
        return youtube;
    }

    public void setYoutube(Youtube[] youtube) {
        this.youtube = youtube;
    }

    @Override
    public String toString() {
        return "YoutubeResponse{" +
                "id=" + id +
                ", youtube=" + Arrays.toString(youtube) +
                '}';
    }
}
