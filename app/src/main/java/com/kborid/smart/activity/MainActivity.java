package com.kborid.smart.activity;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.kborid.library.hand2eventbus.EventBusss;
import com.kborid.library.sample.TestSettings;
import com.kborid.library.util.LogUtils;
import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;
import com.kborid.smart.codescan.control.CaptureActivity;
import com.kborid.smart.helper.MainActionHelper;
import com.kborid.setting.java.tt.CustomThread;
import com.kborid.smart.ui.activity.SnapHelpActivity;
import com.thunisoft.common.util.ToastUtils;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class MainActivity extends SimpleActivity {

    private static Drawable mDrawable;

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
        return R.layout.act_test;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        printTest();

        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.view_action_item, MainActionHelper.actions));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            MainActionHelper.ActionType actionType = MainActionHelper.ActionType.indexOf(position);
            switch (actionType) {
                case ACTION_OPEN_BD:
                    onBaidu();
                    break;
                case ACTION_OPEN_JS:
                    onJS();
                    break;
                case ACTION_OPEN_DOC:
                    onOpenReaderView();
                    break;
                case ACTION_HANDLER:
                    onHandler();
                    break;
                case ACTION_SNAP:
                    onSnap();
                    break;
                case ACTION_SHARE:
                    onTextShare();
                    break;
                case ACTION_CODE:
//                    onQRCode();
                    download();
                    break;
            }
        });
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            MainActionHelper.ActionType actionType = MainActionHelper.ActionType.indexOf(position);
            if (actionType == MainActionHelper.ActionType.ACTION_SHARE) {
                onIntentOpenReader();
            }
            return true;
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

    private void onSnap() {
        startActivity(new Intent(this, SnapHelpActivity.class));
    }

    private void onTextShare() {
        Intent targetIntent = new Intent(Intent.ACTION_SEND);
        targetIntent.setType("text/plain");
        targetIntent.putExtra(Intent.EXTRA_TEXT, "Hello World!!!");
        targetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        targetedShareIntents.add(targetIntent);
        Intent resultIntent = null;
        if (targetedShareIntents.size() > 0) {
            resultIntent = Intent.createChooser(targetedShareIntents.remove(0), null);
            LabeledIntent[] li = targetedShareIntents.toArray(new LabeledIntent[targetedShareIntents.size()]);
            resultIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, li);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        try {
            startActivity(resultIntent);
        } catch (ActivityNotFoundException ex) {
            ToastUtils.showToast("Can't find share component to share");
        }
    }

    private void onHandler() {
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

    private void onJS() {
        Intent intent = new Intent(this, X5WebViewActivity.class);
        intent.putExtra("from", "main");
        intent.putExtra("path", "file:///android_asset/ExampleApp.html");
        startActivity(intent);
    }

    private void onBaidu() {
        Intent intent = new Intent(this, X5WebViewActivity.class);
        intent.putExtra("from", "main");
        intent.putExtra("path", "https://www.baidu.com");
        startActivity(intent);
    }

    private void onOpenReaderView() {
        Intent intent = new Intent(this, ReaderViewActivity.class);
        intent.putExtra("from", "main");
        intent.putExtra("path", "file:///android_asset/test.docx");
        startActivity(intent);
    }

    private void onIntentOpenReader() {
//        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.docx");
        File file = new File("file:///android_asset/test.docx");
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

    private void onQRCode() {
        startActivity(new Intent(this, CaptureActivity.class));
    }

    private void printTest() {
        TestSettings.instance.reset();
        TestSettings.instance.setSettingOne(true);
        TestSettings.instance.setSettingTwo(true);
        TestSettings.instance.setSettingThr(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy()");
        EventBusss.getDefault().unRegister(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] arg) {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-DDD");
        System.out.println(sdf.format(LocalDate.of(2020, 5, 10)));
    }

    public void download() {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//        Uri uri = Uri.parse(CommonUtil.getHttpServerUrl() + "/update/download");
//        String secretUrl = "https://cocall.thunisoft.com:8443/update/download";
        String secretUrl = "http://cocall.thunisoft.com:8888/update/download";
        Uri uri = Uri.parse(secretUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CoCall";
        String flieName = "Cocall123.apk";
        File file = new File(folder, flieName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        request.setDestinationUri(Uri.fromFile(file));
        String appName = getResources().getString(R.string.app_name);
        request.setTitle(appName);
        request.setDescription("正在下载升级程序");
            request.setAllowedOverMetered(false); // 只在wifi下下载
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        final long loadId = downloadManager.enqueue(request);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                long[] bytesAndStatus = getBytesAndStatus(loadId);
                if (bytesAndStatus[2] == DownloadManager.STATUS_FAILED) {
                } else if (bytesAndStatus[2] == DownloadManager.STATUS_SUCCESSFUL) {
                } else {
                }
            }
        };
        timer.schedule(task, 0, 100);
    }

    public static long[] getBytesAndStatus(long downloadId) {
        long[] bytesAndStatus = new long[]{-1, -1, 0};
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = null;
        try {
            DownloadManager manager = (DownloadManager) PRJApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE);
            c = manager.query(query);
            if (c != null && c.moveToFirst()) {
                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                bytesAndStatus[2] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return bytesAndStatus;
    }
}
