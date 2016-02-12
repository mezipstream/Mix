package me.zipstream.mix.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import me.zipstream.mix.model.ZhihuNews;
import me.zipstream.mix.ui.fragment.ZhihuDetailFragment;

public class ZhihuDetailAdapter extends FragmentPagerAdapter {

    private List<ZhihuNews> mZhihuNewses;

    public ZhihuDetailAdapter(FragmentManager fm, List<ZhihuNews> zhihuNewses) {
        super(fm);

        mZhihuNewses = zhihuNewses;
    }

    @Override
    public Fragment getItem(int position) {
        return ZhihuDetailFragment.getInstance(mZhihuNewses.get(position));
    }

    @Override
    public int getCount() {
        return mZhihuNewses.size();
    }
}
