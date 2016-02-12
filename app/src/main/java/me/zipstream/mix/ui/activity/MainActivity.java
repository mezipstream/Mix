package me.zipstream.mix.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zipstream.mix.R;
import me.zipstream.mix.base.BaseActivity;
import me.zipstream.mix.ui.fragment.MainMenuFragment;
import me.zipstream.mix.ui.fragment.ZhihuFragment;

public class MainActivity extends BaseActivity {

    @Bind(R.id.common_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.syncState();

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        replaceFragment(R.id.content_container, new ZhihuFragment());
        replaceFragment(R.id.drawer_container, new MainMenuFragment());
    }

    @Override
    protected void initData() {

    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }
}
