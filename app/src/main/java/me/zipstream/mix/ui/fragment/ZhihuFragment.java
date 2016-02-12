package me.zipstream.mix.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.victor.loading.rotate.RotateLoading;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zipstream.mix.R;
import me.zipstream.mix.adapter.ZhihuAdapter;
import me.zipstream.mix.base.BaseFragment;
import me.zipstream.mix.callback.LoadResultCallback;
import me.zipstream.mix.ui.widget.AutoLoadRecyclerView;

public class ZhihuFragment extends BaseFragment implements LoadResultCallback {

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.auto_load_recycler_view)
    AutoLoadRecyclerView mAutoLoadRecyclerView;
    @Bind(R.id.common_loading)
    RotateLoading mRotateLoading;

    private ZhihuAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auto_load, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAutoLoadRecyclerView.setHasFixedSize(false);

        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.loadZhihuData();
            }
        });

        mAutoLoadRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAutoLoadRecyclerView.setOnPauseListenerPrams(false, true);

        mAdapter = new ZhihuAdapter(getActivity(), mAutoLoadRecyclerView, this);
        mAutoLoadRecyclerView.setAdapter(mAdapter);

        mAdapter.loadZhihuData();
        mRotateLoading.start();
    }

    @Override
    public void onSuccess(int resultCode, Object object) {
        mRotateLoading.stop();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError(int resultCode, String msg) {
        mRotateLoading.stop();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            mSwipeRefreshLayout.setRefreshing(true);
            mAdapter.loadZhihuData();

            return true;
        }

        return false;
    }
}
