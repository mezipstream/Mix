package me.zipstream.mix.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import me.zipstream.mix.model.Joke;

public class JokeRequest extends Request<List<Joke>> {

    private Response.Listener<List<Joke>> mListener;

    public JokeRequest(String url, Response.Listener<List<Joke>> listener,
                       Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<List<Joke>> parseNetworkResponse(NetworkResponse response) {
        try {
            String resultString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject resultObj = new JSONObject(resultString);
            JSONArray contentArray = resultObj.optJSONArray("comments");

            return Response.success(Joke.parse(contentArray),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(List<Joke> response) {
        mListener.onResponse(response);
    }
}