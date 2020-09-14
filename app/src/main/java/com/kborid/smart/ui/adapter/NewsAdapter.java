package com.kborid.smart.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kborid.library.listener.IDataAdapter;
import com.kborid.library.util.ImageLoaderUtils;
import com.kborid.smart.R;
import com.kborid.smart.entity.NewsSummary;
import com.thunisoft.ui.util.ScreenUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.BaseViewHolder> implements IDataAdapter<NewsSummary> {

    private static final int NORMAL = 1;
    private static final int TITLE_ONLY = 2;
    private static final int THREE_IMG = 3;

    private Context mContext;
    private List<NewsSummary> mNewsSummaries = new ArrayList<>();

    public NewsAdapter(Context context) {
        this.mContext = context;
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
        NewsSummary newsSummary = mNewsSummaries.get(position);
        int width = ScreenUtils.mScreenWidth;
        if (holder instanceof NewsNormalViewHolder) {
            NewsNormalViewHolder newsNormalViewHolder = (NewsNormalViewHolder) holder;
            if (StringUtils.isNotBlank(newsSummary.getDigest())) {
                newsNormalViewHolder.summaryTV.setVisibility(View.VISIBLE);
                newsNormalViewHolder.summaryTV.setText(newsSummary.getDigest());
            } else {
                newsNormalViewHolder.summaryTV.setVisibility(View.GONE);
            }
            width = 500;
            ImageLoaderUtils.display(mContext, holder.iconIV, newsSummary.getImgsrc(), width, width / 4 * 3);
        } else if (holder instanceof NewsOnlyTitleViewHolder) {
            NewsOnlyTitleViewHolder newsOnlyTitleViewHolder = (NewsOnlyTitleViewHolder) holder;
        }
        holder.titleTV.setText(newsSummary.getTitle());
        holder.timeTV.setText(newsSummary.getPtime());
        ImageLoaderUtils.display(mContext, holder.iconIV, newsSummary.getImgsrc(), width, width / 4 * 3);
    }

    @Override
    public int getItemViewType(int position) {
        return mNewsSummaries.get(position).getHasImg() == 1 ? TITLE_ONLY : NORMAL;
    }

    @Override
    public int getItemCount() {
        return mNewsSummaries.size();
    }

    public void setNewsData(List<NewsSummary> newsSummaryList) {
        this.mNewsSummaries = newsSummaryList;
        notifyDataSetChanged();
    }

    public void addNewsData(List<NewsSummary> newsSummaryList) {
        if (null != newsSummaryList) {
            this.mNewsSummaries.addAll(newsSummaryList);
            notifyDataSetChanged();
        }
    }

    @Override
    public List<NewsSummary> getData() {
        return this.mNewsSummaries;
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
