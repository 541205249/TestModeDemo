package testmode.eebbk.com.testmodedemo.window;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class FloatWindowController {
    private HomeWatcherReceiver mHomeKeyReceiver;
    private Context mContext;

    private FloatWindowController() {
    }

    public static FloatWindowController getInstance() {
        return LittleMonkProviderHolder.sInstance;
    }

    private static class LittleMonkProviderHolder {
        private static final FloatWindowController sInstance = new FloatWindowController();
    }

    public void open(Context context) {
        mContext = context;
        if (mHomeKeyReceiver == null) {
            initFloatWindow(context);
        }

        FloatWindow.show();
    }

    public void hide() {
        FloatWindow.hide();
    }

    public void close() {
        if (mContext == null) {
            return;
        }

        FloatWindow.removeFloatWindowManager();
        if (mHomeKeyReceiver != null) {
            mContext.unregisterReceiver(mHomeKeyReceiver);
            mHomeKeyReceiver = null;
        }
    }

    private void initFloatWindow(Context context) {
        mHomeKeyReceiver = new HomeWatcherReceiver();
        IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.registerReceiver(mHomeKeyReceiver, homeFilter);
        FloatWindow.createFloatWindow(context);
    }
}
