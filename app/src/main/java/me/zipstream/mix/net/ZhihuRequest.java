package me.zipstream.mix.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import me.zipstream.mix.model.ZhihuNews;

public class ZhihuRequest extends Request<List<ZhihuNews>> {

    private Response.Listener<List<ZhihuNews>> mListener;

    public ZhihuRequest(String url, Response.Listener<List<ZhihuNews>> listener,
                        Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<List<ZhihuNews>> parseNetworkResponse(NetworkResponse response) {
        try {
            String resultString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject resultObj = new JSONObject(resultString);
            JSONArray storiesArray = resultObj.optJSONArray("stories");

            return Response.success(ZhihuNews.parse(storiesArray),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(List<ZhihuNews> response) {
        mListener.onResponse(response);
    }
}
