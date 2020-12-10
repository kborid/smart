package com.kborid.library.di.component;

import android.app.Activity;

import com.kborid.library.di.FragmentScope;
import com.kborid.library.di.module.FragmentModule;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();
}
