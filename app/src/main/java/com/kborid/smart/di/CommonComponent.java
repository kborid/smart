package com.kborid.smart.di;

import com.kborid.library.di.ActivityScope;
import com.kborid.library.di.module.CommonModule;
import com.kborid.smart.ui.MainFragmentActivity;
import com.kborid.smart.ui.news.detail.NewsDetailActivity;
import com.kborid.smart.ui.news.newslist.NewsFragment;
import com.kborid.smart.ui.tab.NewsTabFragment;
import com.kborid.smart.ui.tab.PhotoTabFragment;
import com.kborid.smart.ui.tab.VideoTabFragment;
import com.kborid.smart.ui.texture.TextureViewActivity;
import com.kborid.smart.ui.video.list.VideoFragment;

import dagger.Component;

@ActivityScope
@Component(modules = CommonModule.class)
public interface CommonComponent {
    void inject(TextureViewActivity activity);

    void inject(MainFragmentActivity activity);

    void inject(NewsTabFragment fragment);

    void inject(NewsFragment fragment);

    void inject(NewsDetailActivity activity);

    void inject(PhotoTabFragment fragment);

    void inject(VideoTabFragment fragment);

    void inject(VideoFragment fragment);
}
