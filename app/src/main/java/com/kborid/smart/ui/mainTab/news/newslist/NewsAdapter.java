package com.kborid.smart.ui.mainTab.news.newslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kborid.smart.R;
import com.kborid.smart.entity.NewsSummary;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context mContext;
    private List<NewsSummary> mNewsSummaries = new ArrayList<>();

    public NewsAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsSummary newsSummary = mNewsSummaries.get(position);
        holder.titleTV.setText(newsSummary.getTitle());
        holder.summaryTV.setText(newsSummary.getDigest());
        holder.timeStampTV.setText(newsSummary.getPtime());
    }

    @Override
    public int getItemCount() {
        return mNewsSummaries.size();
    }

    public void setNewsData(List<NewsSummary> newsSummaryList) {
        this.mNewsSummaries = newsSummaryList;
        notifyDataSetChanged();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_summary_title_tv)
        TextView titleTV;
        @BindView(R.id.news_summary_digest_tv)
        TextView summaryTV;
        @BindView(R.id.news_summary_ptime_tv)
        TextView timeStampTV;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
