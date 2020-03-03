package com.kborid.smart.ui.news.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kborid.library.base.BaseActivity;
import com.kborid.smart.R;
import com.kborid.smart.contant.AppConstant;
import com.kborid.smart.di.DaggerCommonComponent;
import com.kborid.smart.entity.NewsDetail;
import com.kborid.smart.ui.news.detail.presenter.NewsDetailPresenter;
import com.kborid.smart.ui.news.detail.presenter.contract.NewsDetailContract;

public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter> implements NewsDetailContract.View {
    @Override
    protected void initInject() {
        DaggerCommonComponent.builder()
                .commonModule(getCommonModule("newsDetail"))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_news_detail;
    }

    public static void startAction(Context context, String postId, String imgUrl) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(AppConstant.NEWS_POST_ID, postId);
        intent.putExtra(AppConstant.NEWS_IMG_RES, imgUrl);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ActivityOptions options = ActivityOptions
//                    .makeSceneTransitionAnimation((Activity) mContext,view, AppConstant.TRANSITION_ANIMATION_NEWS_PHOTOS);
//            mContext.startActivity(intent, options.toBundle());
//        } else {
//
//            //让新的Activity从一个小的范围扩大到全屏
//            ActivityOptionsCompat options = ActivityOptionsCompat
//                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
//            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
//        }
        context.startActivity(intent);
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        String postId = getIntent().getStringExtra(AppConstant.NEWS_POST_ID);
        mPresenter.getNewsDetail(postId);
    }

    @Override
    public void refreshInfo(NewsDetail newsDetail) {

    }
}
