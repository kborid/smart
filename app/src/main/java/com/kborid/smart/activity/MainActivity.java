package com.kborid.smart.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kborid.library.hand2eventbus.EventBusss;
import com.kborid.library.sample.TestSettings;
import com.kborid.library.util.LogUtils;
import com.kborid.smart.R;
import com.kborid.smart.codescan.control.CaptureActivity;
import com.kborid.smart.helper.MainActionHelper;
import com.kborid.smart.service.SmartCounterServiceConnection;
import com.kborid.smart.tt.CustomThread;
import com.kborid.smart.tt.SingletonTest;
import com.kborid.smart.ui.snaphelper.SnapHelpActivity;
import com.thunisoft.common.tool.UIHandler;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import butterknife.BindView;

public class MainActivity extends SimpleActivity {

    private SmartCounterServiceConnection counterConn = null;
    private /*static*/ Drawable mDrawable;

    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("onCreate()");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        EventBusss.getDefault().register(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_main2;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        if (null != titleView) {
            titleView.setCanBack(false);
            titleView.setTitle("Home");
        }
        printTest();
        bindServiceInner();

        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.view_action_item, getResources().getStringArray(R.array.actions)));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            MainActionHelper.ActionType actionType = MainActionHelper.ActionType.indexOf(position);
            switch (actionType) {
                case ACTION_OPEN_BD:
                    onBaidu();
                    break;
                case ACTION_OPEN_JS:
                    onReaderView();
                    break;
                case ACTION_REFLECT:
                    onReflect();
                    break;
                case ACTION_SHARE:
                    onShare();
                    break;
                case ACTION_SCAN:
                    startActivity(new Intent(MainActivity.this, CaptureActivity.class));
                    break;
                case ACTION_SECRET:
                    onJump();
                    break;
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MainActionHelper.ActionType actionType = MainActionHelper.ActionType.indexOf(position);
                switch (actionType) {
                    case ACTION_SCAN:
                        startActivity(new Intent(MainActivity.this, SnapHelpActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected boolean needStatusBarImmersive() {
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d("onStart()");
        /*
         *  内存泄漏
         *  原因：mDrawable是一个静态对象，常驻内存，通过ImageView方法setImageDrawable时，会导致mDrawable引用ImageView（Drawable.setCallBack），
         *  同时，ImageView又对activity有引用，所以导致mDrawable间接引用activity，使activity无法被回收。
         */
        ImageView iv = new ImageView(this);
        mDrawable = getResources().getDrawable(R.mipmap.logo);
        iv.setImageDrawable(mDrawable);
    }

    private void onShare() {
//        Intent targetIntent = new Intent(Intent.ACTION_SEND);
//        targetIntent.setType("text/plain");
//        targetIntent.putExtra(Intent.EXTRA_TEXT, "Hello World!!!");
//        targetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        List<Intent> targetedShareIntents = new ArrayList<Intent>();
//        targetedShareIntents.add(targetIntent);
//        Intent resultIntent = null;
//        if (targetedShareIntents.size() > 0) {
//            resultIntent = Intent.createChooser(targetedShareIntents.remove(0), null);
//            LabeledIntent[] li = targetedShareIntents.toArray(new LabeledIntent[targetedShareIntents.size()]);
//            resultIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, li);
//            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        try {
//            startActivity(resultIntent);
//        } catch (ActivityNotFoundException ex) {
//            ToastUtils.showToast("Can't find share component to share");
//        }
        counterConn.stopCount();

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.docx");
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, "com.kborid.smart.fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
//        if(!file.exists()){
//            return;
//        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra("filePath", file.getPath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("application/*");
        try {
//            startActivity(intent);
            startActivity(Intent.createChooser(intent, "选择浏览工具"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void onJump() {
        counterConn.pauseCount();
    }

    private void onReflect() {
        counterConn.startCount();
        CustomThread customThread = new CustomThread();
        customThread.start();
//        customThread.getInnerHandler().sendEmptyMessage(1);
//        customThread.getInnerHandler().sendEmptyMessageDelayed(2, 1000);
        Message message = Message.obtain(/*customThread.getInnerHandler(), new Runnable() {
            @Override
            public void run() {
                System.out.println("Message's callback");
            }
        }*/);
        message.what = 3;
        customThread.getInnerHandler().sendMessageDelayed(message, 2000);
    }

    //Context
    //getBaseContext()
    //ContextWrapper
    //getApplicationContext()
    //ContextThemeWrapper
    //this

    private void onBaidu() {
        Intent intent = new Intent(this, X5WebViewActivity.class);
        intent.putExtra("from", "main");
        intent.putExtra("path", "https://www.baidu.com");
        startActivity(intent);
    }

    private void onReaderView() {
        Intent intent = new Intent(this, ReaderViewActivity.class);
        intent.putExtra("from", "main");
        intent.putExtra("path", "file:///android_asset/test.doc");
        startActivity(intent);
    }

    private void printTest() {
        TestSettings.instance.reset();
        TestSettings.instance.setSettingOne(true);
        TestSettings.instance.setSettingTwo(true);
        TestSettings.instance.setSettingThr(true);
    }

    private void bindServiceInner() {
        if (null == counterConn) {
            counterConn = new SmartCounterServiceConnection();
        }
        counterConn.bindService();
        counterConn.setCountChangedListener(countChangedListener);
    }

    private void unBindServiceInner() {
        if (null != counterConn) {
            counterConn.unBindService();
            counterConn = null;
        }
    }

    private SmartCounterServiceConnection.CountChangedListener countChangedListener = (value -> {
        ((TextView) findViewById(R.id.tv_counter)).setText(String.valueOf(value));
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy()");
        unBindServiceInner();
        EventBusss.getDefault().unRegister(this);
    }

    public static void scroll2Bottom(final ScrollView scroll, final View inner) {
        UIHandler.post(() -> {
                    if (scroll == null || inner == null) {
                        return;
                    }
                    // 内层高度超过外层
                    int offset = inner.getMeasuredHeight() - scroll.getMeasuredHeight();
                    if (offset < 0) {
                        offset = 0;
                    }
                    Thread.yield();
                    String name = SingletonTest.Singleton5.instance.getTestName();
                    SingletonTest.Singleton4.getInstance();
                    scroll.smoothScrollTo(0, offset);
                }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] arg) {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-DDD");
        System.out.println(sdf.format(LocalDate.of(2020, 5, 10)));
    }
}
