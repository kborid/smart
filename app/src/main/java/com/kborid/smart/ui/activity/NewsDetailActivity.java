package com.kborid.smart.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kborid.library.util.ImageLoaderUtils;
import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;
import com.kborid.smart.base.AppActivity;
import com.kborid.smart.constant.AppConstant;
import com.kborid.smart.entity.NewsDetail;
import com.kborid.smart.ui.presenter.NewsDetailPresenter;
import com.kborid.smart.ui.presenter.contract.NewsDetailContract;
import com.kborid.smart.util.URLImageGetter;
import com.thunisoft.common.util.ToastUtils;

import java.util.List;

import butterknife.BindView;

public class NewsDetailActivity extends AppActivity<NewsDetailPresenter> implements NewsDetailContract.View {

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.news_detail_photo_iv)
    ImageView photoIV;
    @BindView(R.id.news_detail_from_tv)
    TextView newsDetailFromTV;
    @BindView(R.id.news_detail_body_tv)
    TextView newsDetailBodyTV;

    private URLImageGetter mUrlImageGetter;

    @Override
    protected void initInject() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_news_detail;
    }

    public static void startAction(Context context, View view, String postId, String imgUrl) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(AppConstant.NEWS_POST_ID, postId);
        intent.putExtra(AppConstant.NEWS_IMG_RES, imgUrl);
        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation((Activity) context, view, PRJApplication.getInstance().getString(R.string.news_detail_shared_iv));
        context.startActivity(intent, options.toBundle());
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                // 滚动2/3距离时，改变arrow back的tint
                if (Math.abs(i) >= appBarLayout.getTotalScrollRange() / 3 * 2) {
                    toolbar.getNavigationIcon().setTint(getColor(R.color.text_black));
                } else {
                    toolbar.getNavigationIcon().setTint(getColor(R.color.white));
                }
            }
        });
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        String postId = getIntent().getStringExtra(AppConstant.NEWS_POST_ID);
        mPresenter.getNewsDetail(postId);
    }

    @Override
    public void refreshInfo(NewsDetail newsDetail) {
        if (null != newsDetail && !"error".equals(newsDetail.getTid())) {
            String title = newsDetail.getTitle();
            String source = newsDetail.getSource();
            String time = newsDetail.getPtime();
            String body = newsDetail.getBody();
            String image = getImgSrcs(newsDetail);
            setToolbarLayout(title);
            newsDetailFromTV.setText(String.format("%1$s %2$s", source, time));
            setNewsDetailPhoto(image);
            setNewsDetailBody(newsDetail, body);
        } else {
            ToastUtils.showToast("该文章已被删除");
        }
    }

    private void setNewsDetailBody(NewsDetail newsDetail, String body) {
        setBody(newsDetail, body);
    }

    private void setBody(NewsDetail newsDetail, String newsBody) {
        int imgTotal = newsDetail.getImg().size();
        if (isShowBody(newsBody, imgTotal)) {
            newsDetailBodyTV.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效,实测经常卡机崩溃
            mUrlImageGetter = new URLImageGetter(newsDetailBodyTV, newsBody, imgTotal);
            newsDetailBodyTV.setText(Html.fromHtml(newsBody, mUrlImageGetter, null));
        } else {
            newsDetailBodyTV.setText(Html.fromHtml(newsBody));
        }
    }

    private boolean isShowBody(String newsBody, int imgTotal) {
        return imgTotal >= 2 && newsBody != null;
    }

    private String getImgSrcs(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        String imgSrc;
//        if (imgSrcs != null && imgSrcs.size() > 0) {
//            imgSrc = imgSrcs.get(0).getSrc();
//        } else {
        imgSrc = getIntent().getStringExtra(AppConstant.NEWS_IMG_RES);
//        }
        return imgSrc;
    }

    private void setNewsDetailPhoto(String imgSrc) {
        ImageLoaderUtils.displayBigPhoto(this, photoIV, imgSrc);
    }

    private void setToolbarLayout(String title) {
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.text_black));
    }
}
