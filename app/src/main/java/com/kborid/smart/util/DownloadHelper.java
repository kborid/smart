package com.kborid.smart.util;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;
import com.thunisoft.common.util.ToastUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DownloadHelper {

    private static final String FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TTDownload/";
    private static final String DEFAULT_NAME = "tmp";

    private DownloadManager mDownloadManager;
    private Timer mTimer;
    private long mDownloadId;

    private static DownloadHelper mInstance;

    private DownloadHelper() {
        mDownloadManager = (DownloadManager) PRJApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public static DownloadHelper getInstance() {
        if (null == mInstance) {
            mInstance = new DownloadHelper();
        }
        return mInstance;
    }

    public long download(String fileName, String url) {
        if (StringUtils.isBlank(fileName)) {
            fileName = DEFAULT_NAME;
        }
        File file = new File(FOLDER, fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationUri(Uri.fromFile(file));
        request.setTitle(PRJApplication.getInstance().getString(R.string.app_name));
        request.setDescription("正在下载");
        request.setShowRunningNotification(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        mDownloadId = mDownloadManager.enqueue(request);

        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                long[] bytesAndStatus = getBytesAndStatus(mDownloadId);
                switch ((int) bytesAndStatus[2]) {
                    case DownloadManager.STATUS_FAILED:
                        ToastUtils.showToast("下载失败，原因 " + bytesAndStatus[3]);
                        if (null != mTimer) {
                            mTimer.cancel();
                        }
                        break;
                    case DownloadManager.STATUS_PAUSED:
                        System.out.println("暂停下载");
                        break;
                    case DownloadManager.STATUS_PENDING:
                        System.out.println("等待下载");
                        break;
                    case DownloadManager.STATUS_RUNNING:
                        System.out.println("状态:" + bytesAndStatus[2] + ",总共大小:" + bytesAndStatus[1] + ",已下载:" + bytesAndStatus[0]);
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        ToastUtils.showToast("下载成功");
                        if (null != mTimer) {
                            mTimer.cancel();
                        }
                        break;
                }
            }
        }, 1000, 1000);

        return mDownloadId;
    }

    public long[] getBytesAndStatus(long downloadId) {
        long[] bytesAndStatus = new long[]{-1, -1, 0, 0};
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = null;
        try {
            c = mDownloadManager.query(query);
            if (c != null && c.moveToFirst()) {
                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                bytesAndStatus[2] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                bytesAndStatus[3] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return bytesAndStatus;
    }

    public void download2(String fileName, String url) {
        FileDownloader.getImpl().create(url)
                .setPath(FOLDER + fileName)
                .setListener(mFileDownloadListener)
                .start();
    }

    private FileDownloadListener mFileDownloadListener = new FileDownloadSampleListener() {

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            super.error(task, e);
            System.out.println("error");
            e.printStackTrace();
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.progress(task, soFarBytes, totalBytes);
            System.out.println("总共大小:" + soFarBytes + ",已下载:" + totalBytes);
        }

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.pending(task, soFarBytes, totalBytes);
            System.out.println("pending");
        }
    };

    public void download3(String fileName, String url) {
        DownloadTask task = new DownloadTask.Builder(url, FOLDER, fileName)
                .setMinIntervalMillisCallbackProcess(30) // 下载进度回调的间隔时间（毫秒）
                .setPassIfAlreadyCompleted(false)// 任务过去已完成是否要重新下载
                .setPriority(10)
                .build();
        task.enqueue(new DownloadListener4WithSpeed() {

            @Override
            public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info, boolean fromBreakpoint, @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {
                System.out.println("infoReady");
            }

            @Override
            public void progressBlock(@NonNull DownloadTask task, int blockIndex, long currentBlockOffset, @NonNull SpeedCalculator blockSpeed) {
                System.out.println("progressBlock");
            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, @NonNull SpeedCalculator taskSpeed) {
                System.out.println("progress");
            }

            @Override
            public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info, @NonNull SpeedCalculator blockSpeed) {
                System.out.println("blockEnd");
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull SpeedCalculator taskSpeed) {
                System.out.println("taskEnd");
            }

            @Override
            public void taskStart(@NonNull DownloadTask task) {
                System.out.println("taskStart");
            }

            @Override
            public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {
                System.out.println("connectStart");
            }

            @Override
            public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
                System.out.println("connectEnd");
            }
        });//异步执行任务
    }
}
