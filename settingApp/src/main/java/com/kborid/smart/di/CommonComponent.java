package com.kborid.smart.di;

import com.kborid.library.di.ActivityScope;
import com.kborid.library.di.module.CommonModule;
import com.kborid.smart.ui.test.TestActivity;
import com.kborid.smart.ui.texture.TextureViewActivity;

import dagger.Component;

@ActivityScope
@Component(modules = CommonModule.class)
public interface CommonComponent {
    void inject(TestActivity activity);
    void inject(TextureViewActivity activity);
}