package me.zipstream.mix.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zipstream.mix.R;
import me.zipstream.mix.base.BaseFragment;
import me.zipstream.mix.model.MenuItem;
import me.zipstream.mix.ui.activity.MainActivity;
import me.zipstream.mix.ui.activity.SettingActivity;

public class MainMenuFragment extends BaseFragment {

    @Bind(R.id.drawer_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.setting_rl)
    RelativeLayout mSettingRelativeLayout;

    private LinearLayoutManager mLayoutManager;
    private MainActivity mMainActivity;
    private MenuAdapter mMenuAdapter;
    private MenuItem.FragmentType mCurrentFragment = MenuItem.FragmentType.Zhihu;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof MainActivity) {
            mMainActivity = (MainActivity) activity;
        } else {
            throw new IllegalArgumentException("the activity must be MainActivity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.bind(this, view);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSettingRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
                mMainActivity.closeDrawer();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMenuAdapter = new MenuAdapter();
        addMenuItems(mMenuAdapter);
        mRecyclerView.setAdapter(mMenuAdapter);
    }

    private void addMenuItems(MenuAdapter menuAdapter) {
        menuAdapter.mItems.clear();

        menuAdapter.mItems.add(new MenuItem("知乎精选", R.drawable.ic_explore_white_24dp,
                MenuItem.FragmentType.Zhihu, ZhihuFragment.class));
        menuAdapter.mItems.add(new MenuItem("好玩段子", R.drawable.ic_joke_white_24dp,
                MenuItem.FragmentType.Joke, JokeFragment.class));
        menuAdapter.mItems.add(new MenuItem("启动应用", R.drawable.ic_action_launch,
                MenuItem.FragmentType.AppLauncher, AppLauncherFragment.class));
        menuAdapter.mItems.add(new MenuItem("天气如何", R.drawable.ic_weather_white_24dp,
                MenuItem.FragmentType.Weather, WeatherFragment.class));
    }

    private class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

        private List<MenuItem> mItems;

        public MenuAdapter() {
            mItems = new ArrayList<>();
        }

        @Override
        public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_drawer, parent, false);

            return new MenuViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MenuViewHolder holder, int position) {
            final MenuItem menuItem = mItems.get(position);

            holder.mItemImage.setImageResource(menuItem.getResourceId());
            holder.mItemTitle.setText(menuItem.getTitle());
            holder.mItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCurrentFragment != menuItem.getType()) {
                        try {
                            Fragment fragment = (Fragment)
                                    Class.forName(menuItem.getFragment().getName()).newInstance();
                            mMainActivity.replaceFragment(R.id.content_container, fragment);
                            mCurrentFragment = menuItem.getType();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    mMainActivity.closeDrawer();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    private static class MenuViewHolder extends RecyclerView.ViewHolder {

        private ImageView mItemImage;
        private TextView mItemTitle;
        private RelativeLayout mItemLayout;

        public MenuViewHolder(View itemView) {
            super(itemView);

            mItemImage = (ImageView) itemView.findViewById(R.id.img_menu);
            mItemTitle = (TextView) itemView.findViewById(R.id.drawer_item_title);
            mItemLayout = (RelativeLayout) itemView.findViewById(R.id.drawer_item_rl);
        }
    }
}
