package com.kborid.smart.di;

import com.kborid.library.di.ActivityScope;
import com.kborid.library.di.module.CommonModule;
import com.kborid.smart.ui.main.MainFragmentActivity;
import com.kborid.smart.ui.news.newslist.NewsFragment;
import com.kborid.smart.ui.news.tab.NewsTabFragment;
import com.kborid.smart.ui.picture.tab.PicTabFragment;
import com.kborid.smart.ui.texture.TextureViewActivity;

import dagger.Component;

@ActivityScope
@Component(modules = CommonModule.class)
public interface CommonComponent {
    void inject(TextureViewActivity activity);

    void inject(MainFragmentActivity activity);

    void inject(NewsTabFragment fragment);

    void inject(NewsFragment fragment);

    void inject(PicTabFragment fragment);
}
