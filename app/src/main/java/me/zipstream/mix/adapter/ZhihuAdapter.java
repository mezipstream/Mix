package me.zipstream.mix.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zipstream.mix.R;
import me.zipstream.mix.base.Constants;
import me.zipstream.mix.callback.LoadFinishCallback;
import me.zipstream.mix.callback.LoadResultCallback;
import me.zipstream.mix.model.ZhihuNews;
import me.zipstream.mix.net.RequestManager;
import me.zipstream.mix.net.ZhihuRequest;
import me.zipstream.mix.ui.activity.ZhihuDetailActivity;
import me.zipstream.mix.util.ImageLoaderProxy;
import me.zipstream.mix.util.NetworkUtil;

public class ZhihuAdapter extends RecyclerView.Adapter<ZhihuAdapter.ZhihuViewHolder> {

    private int mLastPosition = -1;

    private Activity mActivity;
    private DisplayImageOptions mOptions;

    private List<ZhihuNews> mZhihuNewses;

    private LoadFinishCallback mLoadFinishCallback;
    private LoadResultCallback mLoadResultCallback;

    public ZhihuAdapter(Activity activity, LoadFinishCallback loadFinishCallback, LoadResultCallback loadResultCallback) {
        mActivity = activity;
        mLoadFinishCallback = loadFinishCallback;
        mLoadResultCallback = loadResultCallback;

        mZhihuNewses = new ArrayList<>();
        int loadingResource = R.drawable.lks_zhihu;
        mOptions = ImageLoaderProxy.getOptions4PictureList(loadingResource);
    }

    @Override
    public ZhihuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_zhihu, parent, false);

        return new ZhihuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ZhihuViewHolder holder, final int position) {
        ZhihuNews zhihuNews = mZhihuNewses.get(position);

        ImageLoaderProxy.getImageLoader().displayImage(zhihuNews.getImageUrl(),
                holder.mImageView, mOptions);
        holder.mTitleTextView.setText(zhihuNews.getTitle());
        holder.mItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toZhihuDetail(position);
            }
        });

        setAnimation(holder.mItemLayout, position);
    }

    @Override
    public int getItemCount() {
        return mZhihuNewses.size();
    }

    @Override
    public void onViewDetachedFromWindow(ZhihuViewHolder holder) {
        holder.mItemLayout.clearAnimation();
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils
                    .loadAnimation(viewToAnimate.getContext(), R.anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            mLastPosition = position;
        }
    }

    private void toZhihuDetail(int position) {
        Intent intent = new Intent(mActivity, ZhihuDetailActivity.class);
        intent.putExtra(Constants.ZHIHU_NEWS, (Serializable) mZhihuNewses);
        intent.putExtra(Constants.ZHIHU_POSITION, position);
        mActivity.startActivity(intent);
    }

    public void loadZhihuData() {
        loadDataByNetworkType();
    }

    private void loadDataByNetworkType() {
        if (NetworkUtil.isNetWorkConnected(mActivity)) {
            RequestManager.addRequest(new ZhihuRequest(ZhihuNews.getZhihuNewsUrl(),
                    new Response.Listener<List<ZhihuNews>>() {
                        @Override
                        public void onResponse(List<ZhihuNews> response) {
                            mLoadResultCallback.onSuccess(LoadResultCallback.SUCCESS_OK, null);
                            mLoadFinishCallback.loadFinish(null);

                            mZhihuNewses.clear();
                            mZhihuNewses.addAll(response);

                            notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mLoadResultCallback.onError(LoadResultCallback.ERROR_NET, error.getMessage());
                            mLoadFinishCallback.loadFinish(null);
                        }
                    }), mActivity);
        } else {
            // ...
        }
    }

    public class ZhihuViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.zhihu_item_ll_layout)
        LinearLayout mItemLayout;
        @Bind(R.id.zhihu_item_img)
        ImageView mImageView;
        @Bind(R.id.zhihu_item_title)
        TextView mTitleTextView;

        public ZhihuViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
