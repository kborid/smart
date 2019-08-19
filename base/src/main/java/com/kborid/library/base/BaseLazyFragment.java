package com.kborid.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseLazyFragment
 *
 * @description: fragment基类，懒加载机制
 * @date: 2019/8/19
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public abstract class BaseLazyFragment extends Fragment {

    protected Context mContext;
    protected View mView;
    private Unbinder mUnBinder;
    protected boolean isFirst = true;
    protected boolean isPrepared = false;
    protected boolean isLazyLoaded = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.t(getClass().getSimpleName()).i("onAttach()");
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutResId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        onVisibilityChangedToUser(isVisibleToUser, true);
        lazyLoad();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            onVisibilityChangedToUser(true, false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onVisibilityChangedToUser(false, false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.t(getClass().getSimpleName()).i("onDetach()");
        mContext = null;
    }

    /**
     * 当Fragment对用户的可见性发生了改变的时候就会回调此方法
     *
     * @param isVisibleToUser                      true：用户能看见当前Fragment；false：用户看不见当前Fragment
     * @param isHappenedInSetUserVisibleHintMethod true：本次回调发生在setUserVisibleHintMethod方法里；false：发生在onResume或onPause方法里
     */
    public void onVisibilityChangedToUser(boolean isVisibleToUser, boolean isHappenedInSetUserVisibleHintMethod) {
        Logger.t(getClass().getSimpleName()).i("onVisibilityChangedToUser() isVisibleToUser=%s, isHappenedInSetUserVisibleHintMethod=%s", isVisibleToUser, isHappenedInSetUserVisibleHintMethod);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    protected abstract int getLayoutResId();

    protected abstract void initEventAndData();

    protected abstract void onVisible();

    protected abstract void onInvisible();

    protected void lazyLoad() {
        Logger.t(getClass().getSimpleName()).i("lazyLoad() [isPrepared:%s, isLazyLoaded:%s, isFirst:%s]", isPrepared, isLazyLoaded, isFirst);
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded && isFirst) {
            initEventAndData();
            isLazyLoaded = true;
            isFirst = false;
        }
    }
}
