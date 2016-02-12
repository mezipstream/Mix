package me.zipstream.mix.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.victor.loading.rotate.RotateLoading;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zipstream.mix.R;
import me.zipstream.mix.base.BaseFragment;
import me.zipstream.mix.model.ZhihuNews;
import me.zipstream.mix.net.ZhihuDetailRequest;

public class ZhihuDetailFragment extends BaseFragment {

    @Bind(R.id.zhihu_web_view)
    WebView mWebView;
    @Bind(R.id.common_loading)
    RotateLoading mRotateLoading;

    private ZhihuNews mZhihuNews;

    public static ZhihuDetailFragment getInstance(ZhihuNews zhihuNews) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ZHIHU_NEWS, zhihuNews);
        ZhihuDetailFragment fragment = new ZhihuDetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu_detail, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        mZhihuNews = (ZhihuNews) getArguments().getSerializable(ZHIHU_NEWS);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress > 80) {
                    mRotateLoading.stop();
                }
            }
        });

        executeRequest(new ZhihuDetailRequest(ZhihuNews.getZhihuNewsDetailUrl(mZhihuNews.getId()),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mWebView.loadDataWithBaseURL("", getHtml(mZhihuNews, response),
                                "text/html", "utf-8", "");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));

        mRotateLoading.start();
        mRotateLoading.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRotateLoading.isShown()) {
                    mRotateLoading.stop();
                }
            }
        }, 10 * 1000);
    }

    private static String getHtml(ZhihuNews zhihuNews, String content) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html dir=\"ltr\" lang=\"zh\">");
        sb.append("<head>");
        sb.append("<meta name=\"viewport\" content=\"width=100%; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" />");
        sb.append("<link rel=\"stylesheet\" href='file:///android_asset/style.css' type=\"text/css\" media=\"screen\" />");
        sb.append("</head>");
        sb.append("<body style=\"padding:0px 8px 8px 8px;\">");
        sb.append("<div id=\"pagewrapper\">");
        sb.append("<div id=\"mainwrapper\" class=\"clearfix\">");
        sb.append("<div id=\"maincontent\">");
        sb.append("<div class=\"post\">");
        sb.append("<div class=\"posthit\">");
        sb.append("<div class=\"postinfo\">");
        sb.append("<h2 class=\"thetitle\">");
        sb.append("<a>");
        sb.append("</a>");
        sb.append("</h2>");
        sb.append("</div>");
        sb.append("<div class=\"entry\">");
        sb.append(content);
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mWebView != null) {
            mWebView.onPause();
        }
    }
}
