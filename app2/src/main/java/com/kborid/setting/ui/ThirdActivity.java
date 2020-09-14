package com.kborid.setting.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.view.View;

import com.kborid.setting.R;
import com.kborid.setting.widget.CustomThread;
import com.thunisoft.common.base.BaseSimpleActivity;

public class ThirdActivity extends BaseSimpleActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.act_third;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
    }

    /**
     * 隐式Intent调用
     *
     * @param view
     */
    public void onIntent(View view) {
        Intent intent = new Intent("com.thunisoft.cocall.intent.action.ChooseContacts");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        Bundle bundle = new Bundle();
        bundle.putString("type", "type_invite_meeting");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * scheme调用
     *
     * @param view
     */
    public void onScheme(View view) {
        String path = "cc-app://com.thunisoft.cocallmobile/chooseContacts?type=type_invite_meeting?mid=42675400&type=type_invite_meeting";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }

    public void onMeetingScheme(View view) {
        String path = "ccm://pull.ccm.join/ccjm?roomNo=62911483&inviteAble=false&avatar=https://172.16.11.126:8443/avatar/718276&displayName=测试1";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }
    public void onAnr(View view) {
//        String uri = "ccm://pull.ccm.join/ccjm?inviteAble=true&roomNo=oooo";
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//        startActivity(intent);
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        CustomThread thread = new CustomThread();
        thread.start();
        System.out.println("任务线程启动了");
        Handler handler = thread.getInnerHandler();
        Message msg1 = Message.obtain(handler, new Runnable() {
            @Override
            public void run() {
                System.out.println("我是Message的callback，我在处理消息");
            }
        });
        msg1.what = 1;
        handler.sendMessage(msg1);


        Message msg2 = Message.obtain(handler, 2);
        handler.sendMessage(msg2);

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                System.out.println("我在IdleHandle中处理消息");
                return false;
            }
        });

        System.out.println("消息发送完毕");
    }
}
