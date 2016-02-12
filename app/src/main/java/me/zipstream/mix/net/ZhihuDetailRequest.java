package me.zipstream.mix.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONObject;

public class ZhihuDetailRequest extends Request<String> {

    private Response.Listener<String> mListener;

    public ZhihuDetailRequest(String url, Response.Listener<String> listener,
                              Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String resultString = new String(response.data, HttpHeaderParser.parseCharset(response
                    .headers));
            JSONObject jsonObject = new JSONObject(resultString);
            String contentString = jsonObject.getString("body");
            return Response.success(contentString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}