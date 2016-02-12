package me.zipstream.mix.net;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import me.zipstream.mix.base.MyApplication;

public class RequestManager {

    private static final int OUT_TIME = 10000;
    private static final int TIMES_OF_RETRY = 1;

    private static RequestQueue sRequestQueue =
            Volley.newRequestQueue(MyApplication.getContext());

    private RequestManager() {}

    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }

        request.setRetryPolicy(new DefaultRetryPolicy(
                OUT_TIME, TIMES_OF_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        sRequestQueue.add(request);
    }

    public static void cancelAll(Object tag) {
        sRequestQueue.cancelAll(tag);
    }
}
