package me.zipstream.mix.base;

import android.support.v4.app.Fragment;

import com.android.volley.Request;

import me.zipstream.mix.net.RequestManager;
import me.zipstream.mix.util.ImageLoaderProxy;

public class BaseFragment extends Fragment implements Constants {

    protected void executeRequest(Request request) {
        RequestManager.addRequest(request, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        RequestManager.cancelAll(this);
        ImageLoaderProxy.getImageLoader().clearMemoryCache();
    }
}
