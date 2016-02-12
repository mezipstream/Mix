package me.zipstream.mix.ui.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.nostra13.universalimageloader.core.ImageLoader;

import me.zipstream.mix.callback.LoadFinishCallback;
import me.zipstream.mix.callback.LoadMoreListener;
import me.zipstream.mix.util.ImageLoaderProxy;

public class AutoLoadRecyclerView extends RecyclerView implements LoadFinishCallback {

    private LoadMoreListener mLoadMoreListener;
    private boolean mIsLoadingMore;

    public AutoLoadRecyclerView(Context context) {
        this(context, null);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mIsLoadingMore = false;
        addOnScrollListener(new AutoLoadScrollListener(null, true, true));
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    public void setOnPauseListenerPrams(boolean isPauseOnScroll, boolean isPauseOnFling) {
        addOnScrollListener(new AutoLoadScrollListener(
                ImageLoaderProxy.getImageLoader(), isPauseOnScroll, isPauseOnFling));
    }

    @Override
    public void loadFinish(Object object) {
        mIsLoadingMore = false;
    }

    private class AutoLoadScrollListener extends OnScrollListener {

        private ImageLoader mImageLoader;
        private boolean mIsPauseOnScroll;
        private boolean mIsPauseOnFling;

        public AutoLoadScrollListener(ImageLoader imageLoader, boolean isPauseOnScroll, boolean isPauseOnFling) {
            super();

            mImageLoader = imageLoader;
            mIsPauseOnScroll = isPauseOnScroll;
            mIsPauseOnFling = isPauseOnFling;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (getLayoutManager() instanceof LinearLayoutManager) {
                int lastVisibleItem = ((LinearLayoutManager) getLayoutManager())
                        .findLastVisibleItemPosition();
                int totalItemCount = AutoLoadRecyclerView.this.getAdapter().getItemCount();

                // 有回调接口 && 不是加载状态 && 剩下两个item && 向下滑动
                if (mLoadMoreListener != null && !mIsLoadingMore
                        && lastVisibleItem >= totalItemCount - 2 && dy > 0) {
                    mLoadMoreListener.loadMore();
                    mIsLoadingMore = true;
                }
            }
        }
    }
}
