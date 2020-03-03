package com.kborid.smart.ui.news.newslist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.kborid.smart.R;
import com.kborid.smart.entity.NewsSummary;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.BaseViewHolder> {

    private static final int NORMAL = 1;
    private static final int TITLE_ONLY = 2;

    private Context mContext;
    private List<NewsSummary> mNewsSummaries = new ArrayList<>();
    private RequestOptions mRequestOptions;

    public NewsAdapter(Context context) {
        this.mContext = context;
        mRequestOptions = new RequestOptions().placeholder(R.mipmap.ic_placeholder).override(Target.SIZE_ORIGINAL);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TITLE_ONLY) {
            return new NewsOnlyTitleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news_type_only_title, parent, false));
        }
        return new NewsNormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news_type_normal, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        int type = getItemViewType(position);
        NewsSummary newsSummary = mNewsSummaries.get(position);
        if (NORMAL == type) {
            ((NewsNormalViewHolder) holder).summaryTV.setText(newsSummary.getDigest());
        }
        holder.titleTV.setText(newsSummary.getTitle());
        holder.timeTV.setText(newsSummary.getPtime());
        Glide.with(mContext).load(newsSummary.getImgsrc()).apply(mRequestOptions).into(holder.iconIV);
    }

    @Override
    public int getItemViewType(int position) {
        return StringUtils.isBlank(mNewsSummaries.get(position).getDigest()) ? TITLE_ONLY : NORMAL;
    }

    @Override
    public int getItemCount() {
        return mNewsSummaries.size();
    }

    public void setNewsData(List<NewsSummary> newsSummaryList) {
        this.mNewsSummaries = newsSummaryList;
        notifyDataSetChanged();
    }

    public List<NewsSummary> getData() {
        return mNewsSummaries;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView titleTV;
        @BindView(R.id.tv_time)
        TextView timeTV;
        @BindView(R.id.iv_icon)
        ImageView iconIV;

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 正常布局ViewHolder
     */
    public class NewsNormalViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_summary)
        TextView summaryTV;

        public NewsNormalViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    /**
     * 单图片布局ViewHolder
     */
    public class NewsOnlyTitleViewHolder extends BaseViewHolder {
        public NewsOnlyTitleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
