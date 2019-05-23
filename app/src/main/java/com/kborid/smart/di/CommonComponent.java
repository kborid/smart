package com.kborid.smart.di;

import com.kborid.library.di.module.CommonModule;
import com.kborid.smart.ui.test.TestActivity;

import dagger.Component;

@Component(modules = CommonModule.class)
public interface CommonComponent {
    void inject(TestActivity activity);
}
