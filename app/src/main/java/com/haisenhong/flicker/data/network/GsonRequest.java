package com.haisenhong.flicker.data.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.haisenhong.flicker.common.Constants;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hison7463 on 10/12/16.
 */

public class GsonRequest<T> extends Request<T> {

    private Response.Listener listener;
    private Class<T> responseType;
    private Map<String, String> header;
    private Gson gson;

    public GsonRequest(int method, String url, Map<String, String> header, Class<T> responseType, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.header = header;
        this.responseType = responseType;
        this.listener = listener;
        this.gson = new Gson();
    }

    private final String TAG = GsonRequest.class.getSimpleName();

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return header == null ? super.getHeaders() : header;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String data = "";
        JSONObject jsonObject;
        try {

            data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            jsonObject = new JSONObject(data);
            Log.d(TAG, jsonObject.toString());
            JSONObject headers = new JSONObject(response.headers);
            jsonObject.put(Constants.RESPONSE_HEADERS, headers);

            return Response.success(gson.fromJson(jsonObject.toString(), responseType), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(response));
        } catch (JSONException e) {
            return Response.error(new ParseError(response));
        }

    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
