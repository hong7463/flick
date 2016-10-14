package com.haisenhong.flicker.utils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.haisenhong.flicker.data.network.GsonRequest;
import com.haisenhong.flicker.ui.MyApplication;

/**
 * Created by hison7463 on 10/12/16.
 */

public class VolleyUtils {

    private static RequestQueue queue;

    public static RequestQueue getInstance() {
        if(queue == null) {
            queue = Volley.newRequestQueue(MyApplication.getInstance().getApplicationContext());
        }
        return queue;
    }

}
