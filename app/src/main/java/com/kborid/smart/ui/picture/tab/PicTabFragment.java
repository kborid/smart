package com.kborid.smart.ui.picture.tab;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.kborid.library.base.BaseFragment;
import com.kborid.smart.R;
import com.kborid.smart.di.DaggerCommonComponent;
import com.kborid.smart.entity.PhotoGirl;
import com.kborid.smart.listener.RecyclerItemClickListener;
import com.kborid.smart.ui.picture.detail.PictureDetailActivity;
import com.kborid.smart.ui.picture.tab.adapter.PicAdapter;
import com.kborid.smart.ui.picture.tab.presenter.PicTabPresenter;
import com.kborid.smart.ui.picture.tab.presenter.contract.PicTabContract;

import java.util.List;

import butterknife.BindView;

public class PicTabFragment extends BaseFragment<PicTabPresenter> implements PicTabContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private static int SIZE = 500;
    private int mStartPage = 1;

    private PicAdapter adapter;


    @Override
    protected void initInject() {
        DaggerCommonComponent.builder()
                .commonModule(getCommonModule("picTab"))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frag_tab_pic;
    }

    public static Fragment newInstance(String type) {
        Fragment fragment = new PicTabFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        toolbar.setTitle(getArguments().getString("type"));
        mPresenter.getPhotoList(SIZE, mStartPage);
        adapter = new PicAdapter(getContext());
        recycleView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycleView.setAdapter(adapter);
        recycleView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PictureDetailActivity.startPictureDetailActivity(getContext(), adapter.getData().get(position).getUrl());
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public void refreshPhotoList(List<PhotoGirl> girls) {
        adapter.setPicList(girls);
    }
}
