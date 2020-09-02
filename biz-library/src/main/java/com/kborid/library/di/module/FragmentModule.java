package com.kborid.library.di.module;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.kborid.library.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule extends CommonModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    public FragmentModule(Fragment fragment, String name) {
        this.fragment = fragment;
        this.name = name;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
