package com.kborid.smart.di;

import com.kborid.library.di.ActivityScope;
import com.kborid.library.di.module.CommonModule;
import com.kborid.smart.ui.main.MainFragmentActivity;
import com.kborid.smart.ui.mainTab.news.newslist.NewsFragment;
import com.kborid.smart.ui.mainTab.news.NewsTabFragment;
import com.kborid.smart.ui.texture.TextureViewActivity;

import dagger.Component;

@ActivityScope
@Component(modules = CommonModule.class)
public interface CommonComponent {
    void inject(TextureViewActivity activity);

    void inject(MainFragmentActivity activity);

    void inject(NewsFragment fragment);

    void inject(NewsTabFragment fragment);
}
