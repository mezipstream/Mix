package me.zipstream.mix.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.zipstream.mix.base.Constants;

public class ZhihuNews implements Serializable, Constants {

    private String mId;
    private String mTitle;
    private String mImageUrl;

    public static String getZhihuNewsUrl() {
        return ZHIHU_URL_BASE + "latest";
    }

    public static String getZhihuNewsDetailUrl(String id) {
        return ZHIHU_URL_BASE + id;
    }

    public static List<ZhihuNews> parse(JSONArray storiesArray) {
        List<ZhihuNews> zhihuNewses = new ArrayList<>();

        for (int i = 0; i < storiesArray.length(); i++) {
            ZhihuNews news = new ZhihuNews();
            JSONObject storyObject = storiesArray.optJSONObject(i);
            JSONArray imageArray = storyObject.optJSONArray("images");

            news.setTitle(storyObject.optString("title"));
            news.setId(storyObject.optString("id"));
            news.setImageUrl(imageArray.optString(0));

            zhihuNewses.add(news);
        }

        return zhihuNewses;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
