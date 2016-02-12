package me.zipstream.mix.model;

import android.support.v4.app.Fragment;

/**
 mItemData.add("知乎精选");
 mItemData.add("好玩段子");
 mItemData.add("启动应用");
 mItemData.add("记录感悟");
 mItemData.add("天气如何");
 */
public class MenuItem {

    public enum FragmentType {
        Zhihu, Joke, AppLauncher, Note, Weather
    }

    private String mTitle;
    private int mResourceId;
    private FragmentType mType;
    private Class<? extends Fragment> mFragment;

    public MenuItem(String title, int resourceId, FragmentType type, Class<? extends Fragment> fragment) {
        mTitle = title;
        mResourceId = resourceId;
        mType = type;
        mFragment = fragment;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getResourceId() {
        return mResourceId;
    }

    public void setResourceId(int resourceId) {
        mResourceId = resourceId;
    }

    public FragmentType getType() {
        return mType;
    }

    public void setType(FragmentType type) {
        mType = type;
    }

    public Class<? extends Fragment> getFragment() {
        return mFragment;
    }

    public void setFragment(Class<? extends Fragment> fragment) {
        mFragment = fragment;
    }
}
