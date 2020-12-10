package com.kborid.library.di.component;

import android.app.Activity;

import com.kborid.library.di.ActivityScope;
import com.kborid.library.di.module.ActivityModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();
}
