package com.kborid.library.di.module;

import android.app.Activity;

import com.kborid.library.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule extends CommonModule {
    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    public ActivityModule(Activity activity, String name) {
        this.mActivity = activity;
        this.name = name;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
