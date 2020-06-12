package com.kborid.setting.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kborid.setting.R;
import com.thunisoft.common.base.BaseFragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestAFragment extends BaseFragment {

    private static final Logger logger = LoggerFactory.getLogger(TestAFragment.class);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        logger.info("onAttach");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        logger.info("onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logger.info("onActivityCreated");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.info("onCreate");
        setRetainInstance(true);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_testa;
    }

    @Override
    protected void initEventAndData(Bundle bundle) {

    }

    @Override
    public void onStart() {
        super.onStart();
        logger.info("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        logger.info("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        logger.info("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        logger.info("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logger.info("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.info("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logger.info("onDetach");
    }
}
