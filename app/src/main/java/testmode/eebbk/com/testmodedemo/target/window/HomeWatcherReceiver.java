package testmode.eebbk.com.testmodedemo.target.window;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class HomeWatcherReceiver extends BroadcastReceiver {
    private static final String TAG = "HomeWatcherReceiver";
    private static final String SYSTEM_DIALOG_FROM_KEY = "reason";
    private static final String SYSTEM_DIALOG_FROM_RECENT_APPS = "recentapps";
    private static final String SYSTEM_DIALOG_FROM_HOME_KEY = "homekey";
    private static final String SYSTEM_DIALOG_FROM_LOCK = "lock";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
            String from = intent.getStringExtra(SYSTEM_DIALOG_FROM_KEY);
            if (SYSTEM_DIALOG_FROM_HOME_KEY.equals(from)) { //短按Home键
                Log.i(TAG, "Home Key");
                //按home键会直接关闭悬浮窗
//                FloatWindowController.getInstance().close();
            } else if (SYSTEM_DIALOG_FROM_RECENT_APPS.equals(from)) { //长按Home键或是Activity切换键
                Log.i(TAG, "long press home key or activity switch");
            } else if (SYSTEM_DIALOG_FROM_LOCK.equals(from)) { //锁屏操作
                Log.i(TAG, "lock");
            }
        }
    }

}
