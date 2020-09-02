package com.kborid.library.di.module;

import com.kborid.library.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonModule {

    protected String name;

    @ActivityScope
    @Provides
    String providerString() {
        return name;
    }
}
