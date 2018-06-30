package testmode.eebbk.com.testmodedemo.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * 提供线程和进程相关的信息查询
 * Created by Simon on 2016/10/12.
 */

public class ThreadUtils {

    /**
     * 判断当前是否在主线程
     */
    public static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
//        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 获取当前的进程名称
     * <p>
     * 一般默认进程名,就是包名;
     *
     * @return 当前的进程名称; 如果获取失败,返回 null
     */
    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 把runnable发送到主线程运行
     * <p>
     * 如果当前线程, 是主线程,则直接执行;  如果不是, 则通过handler发送到主线程执行
     */
    public static void runOnMainThread(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(r);
        }
    }
}
