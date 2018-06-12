package testmode.eebbk.com.testmodedemo.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;

/**
 * 作者： jiazy
 * 日期： 2018/6/1.
 * 公司： 步步高教育电子有限公司
 * 描述：
 */
public class ServiceUtils {

    /**
     * 判断服务是否开启
     */
    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }
}
