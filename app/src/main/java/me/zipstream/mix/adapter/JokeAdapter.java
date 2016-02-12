package me.zipstream.mix.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zipstream.mix.R;
import me.zipstream.mix.callback.LoadFinishCallback;
import me.zipstream.mix.callback.LoadResultCallback;
import me.zipstream.mix.model.Joke;
import me.zipstream.mix.net.JokeRequest;
import me.zipstream.mix.net.RequestManager;
import me.zipstream.mix.util.JokeUtil;
import me.zipstream.mix.util.NetworkUtil;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.JokeViewHolder> {

    private int mPage;
    private int mLastPosition;
    private List<Joke> mJokes;
    private Activity mActivity;
    private LoadResultCallback mLoadResultCallback;
    private LoadFinishCallback mLoadFinishCallback;

    public JokeAdapter(Activity activity, LoadFinishCallback loadFinishCallback, LoadResultCallback loadResultCallback) {
        mActivity = activity;
        mLoadFinishCallback = loadFinishCallback;
        mLoadResultCallback = loadResultCallback;
        mJokes = new ArrayList<>();
    }

    @Override
    public JokeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_joke, parent, false);

        return new JokeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JokeViewHolder holder, int position) {
        final Joke joke = mJokes.get(position);

        holder.mContentTextView.setText(joke.getJokeContent());
        holder.mShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(mActivity)
                        .items(R.array.joke_dialog)
                        .backgroundColor(mActivity.getResources().getColor(R.color.colorPrimary))
                        .contentColor(Color.WHITE)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView,
                                                    int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        JokeUtil.share(mActivity, joke.getJokeContent().trim());
                                        break;
                                    case 1:
                                        JokeUtil.copy(mActivity, joke.getJokeContent());
                                        break;
                                }
                            }
                        }).show();
            }
        });

        setAnimation(holder.mCardView, position);
    }

    @Override
    public int getItemCount() {
        return mJokes.size();
    }

    @Override
    public void onViewDetachedFromWindow(JokeViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mCardView.clearAnimation();
    }

    public void loadFirst() {
        mPage = 1;
        loadDataByNetworkType();
    }

    public void loadNextPage() {
        mPage++;
        loadDataByNetworkType();
    }

    private void loadDataByNetworkType() {
        if (NetworkUtil.isNetWorkConnected(mActivity)) {
            RequestManager.addRequest(new JokeRequest(Joke.getJokeUrl(mPage),
                    new Response.Listener<List<Joke>>() {
                        @Override
                        public void onResponse(List<Joke> response) {
                            mLoadResultCallback.onSuccess(LoadResultCallback.SUCCESS_OK, null);
                            mLoadFinishCallback.loadFinish(null);

                            if (mPage == 1) {
                                mJokes.clear();
                            }

                            mJokes.addAll(response);
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

    private void setAnimation(View viewToAnimate, int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils
                    .loadAnimation(viewToAnimate.getContext(), R.anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            mLastPosition = position;
        }
    }

    public class JokeViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.card_joke)
        CardView mCardView;
        @Bind(R.id.tv_content_joke)
        TextView mContentTextView;
        @Bind(R.id.img_share_joke)
        ImageView mShareImageView;

        public JokeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
