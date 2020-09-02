package com.kborid.smart.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.kborid.library.adapter.CommRVAdapter;
import com.kborid.library.adapter.RViewHolder;
import com.kborid.library.listener.OnItemClickListener;
import com.kborid.smart.R;
import com.kborid.smart.base.AppFragment;
import com.kborid.smart.entity.PhotoGirl;
import com.kborid.smart.ui.activity.PhotoDetailActivity;
import com.kborid.smart.ui.presenter.PhotoTabPresenter;
import com.kborid.smart.ui.presenter.contract.PhotoTabContract;
import com.thunisoft.common.tool.UIHandler;

import java.util.List;

import butterknife.BindView;

public class PhotoTabFragment extends AppFragment<PhotoTabPresenter> implements PhotoTabContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private static int SIZE = 500;
    private int mStartPage = 1;

    private CommRVAdapter<PhotoGirl> adapter;

    @Override
    protected void initInject() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frag_tab_pic;
    }

    public static Fragment newInstance(String type) {
        Fragment fragment = new PhotoTabFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        toolbar.setTitle(getArguments().getString("type"));
        mPresenter.getPhotoList(SIZE, mStartPage);
        adapter = new CommRVAdapter<PhotoGirl>(getContext(), R.layout.item_photo) {
            @Override
            protected void convert(RViewHolder helper, PhotoGirl photoGirl) {
                helper.setImageUrl(R.id.iv_pic, photoGirl.getUrl(), 1024, 1024 * 4 / 3);
            }
        };
        adapter.setHasStableIds(true);
        recycleView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener<PhotoGirl>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, PhotoGirl entity, int position) {
                PhotoDetailActivity.startPictureDetailActivity(getContext(), entity.getUrl());
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, PhotoGirl entity, int position) {
                return false;
            }
        });
    }

    @Override
    public void refreshPhotoList(List<PhotoGirl> girls) {
        if (null != adapter) {
            adapter.getDataIO().set(girls);
        }
    }
}
