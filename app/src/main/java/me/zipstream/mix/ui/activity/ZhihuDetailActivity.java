package me.zipstream.mix.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zipstream.mix.R;
import me.zipstream.mix.adapter.ZhihuDetailAdapter;
import me.zipstream.mix.base.BaseActivity;
import me.zipstream.mix.model.ZhihuNews;

public class ZhihuDetailActivity extends BaseActivity {

    @Bind(R.id.zhihu_detail_view_pager)
    ViewPager mViewPager;
    @Bind(R.id.common_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_detail);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
    }

    @Override
    protected void initData() {
        List<ZhihuNews> zhihuNewses = (List<ZhihuNews>) getIntent().getSerializableExtra(ZHIHU_NEWS);
        int position = getIntent().getIntExtra(ZHIHU_POSITION, 0);

        mViewPager.setAdapter(new ZhihuDetailAdapter(getSupportFragmentManager(), zhihuNewses));
        mViewPager.setCurrentItem(position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
