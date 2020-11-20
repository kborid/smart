package com.kborid.setting.broadcast;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.kborid.setting.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * LaunchLockerBroadcastReceiver
 *
 * @description: 广播接收
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/8/26
 */
public class LaunchLockerBroadcastReceiver extends BroadcastReceiver {

    private static final Logger logger = LoggerFactory.getLogger(LaunchLockerBroadcastReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        logger.info("接收到广播{}", action);

        if (Constants.BROADCAST_LOCKED_LAUNCHER.equals(action)) {

            boolean isProcRunning = isProcRunning(context);
            boolean isRunAct = isRunActivity(context);
            logger.info("当前应用是否运行（Processes, Tasks）：{}, {}", isProcRunning, isRunAct);

            if (!isRunAct) {
                startProc(context);
            }

            intentLockAct(context);

            boolean isForeground = isForeground(context);
            logger.info("当前应用是否在前台：{}", isForeground);
            if (!isForeground) {
                moveTaskToFront(context);
            }
        }
    }

    private void startProc(Context context) {
        logger.info("启动当前应用：{}", context.getApplicationInfo().processName);
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (null != intent) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    private void intentLockAct(Context context) {
        logger.info("启动锁定页面");
        Intent intent = new Intent("android.intent.action.launchIatSpeech");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    private boolean isRunActivity(Context context) {
        ActivityManager __am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = __am.getRunningTasks(100);
        if (tasks.size() == 0)
            return false;
        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (task.baseActivity.getPackageName().equals(context.getPackageName())) {
//                Intent activityIntent = new Intent();
//                activityIntent.setComponent(task.baseActivity);
//                activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                context.startActivity(activityIntent);
                return true;
            }
        }
        return false;
    }

    /**
     * 判断应用是否在运行
     *
     * @param context
     * @return
     */
    private boolean isProcRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.processName.equals(context.getApplicationInfo().processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断应用是否在前台
     *
     * @param context
     * @return
     */
    private boolean isForeground(Context context) {
        ActivityManager _am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = _am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : runningAppProcesses) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(context.getApplicationInfo().processName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将本应用切换到前台
     *
     * @param context
     */
    private void moveTaskToFront(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> tasks = manager.getAppTasks();
        for (ActivityManager.AppTask task : tasks) {
            if (task.getTaskInfo().baseActivity == null) {
                continue;
            }
            if (task.getTaskInfo().baseActivity.getPackageName().equals(context.getPackageName())) {
                manager.moveTaskToFront(task.getTaskInfo().id, 0);
                task.moveToFront();
                break;
            }
        }
    }
}
