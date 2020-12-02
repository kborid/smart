package com.kborid.smart.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;
import com.kborid.smart.constant.AppConstant;
import com.kborid.smart.constant.GlobalThirdManager;
import com.kborid.smart.entity.UserInfo;
import com.kborid.smart.event.UpdateUserInfoEvent;
import com.thunisoft.common.base.BaseSimpleFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class UserTabFragment extends BaseSimpleFragment {

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarLayout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_logout)
    Button btn_logon;
    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;

    @Override
    protected int getLayoutResId() {
        return R.layout.frag_tab_user;
    }

    public static Fragment newInstance(String type) {
        Fragment fragment = new UserTabFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (Math.abs(i) >= appBarLayout.getTotalScrollRange() / 3 * 2) {
                    toolbarLayout.setTitle(getArguments().getString("type"));
                } else {
                    toolbarLayout.setTitle("");
                }
            }
        });
        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(getContext(), R.color.text_black));
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUserInfo(new UpdateUserInfoEvent(GlobalThirdManager.getUser()));
    }

    @Subscribe
    public void updateUserInfo(UpdateUserInfoEvent event) {
        UserInfo userInfo = event.getUserInfo();
        if (Objects.nonNull(userInfo)) {
            tv_nickname.setVisibility(View.VISIBLE);
            tv_nickname.setText(userInfo.getNickname());
            btn_logon.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.GONE);
        } else {
            tv_nickname.setVisibility(View.GONE);
            btn_login.setVisibility(View.VISIBLE);
            btn_logon.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_login)
    public void login() {
        LocalBroadcastManager.getInstance(PRJApplication.getInstance()).sendBroadcast(new Intent(AppConstant.UNLOGIN_ACTION));
    }

    @OnClick(R.id.btn_logout)
    public void logout() {
        GlobalThirdManager.logout();
        EventBus.getDefault().post(new UpdateUserInfoEvent(null));
    }
}
