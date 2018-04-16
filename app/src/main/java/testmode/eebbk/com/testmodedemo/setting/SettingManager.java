package testmode.eebbk.com.testmodedemo.setting;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * @author LiXiaoFeng
 * @date 2018/4/12
 * <p>
 * 设置管理器
 */
public class SettingManager {
    private static final String SP_NAME = "setting";
    private static final String KEY_AUTO_JUMP = "auto_jump";
    private static final String KEY_AUTO_SCROLL = "auto_scroll";
    private static final String KEY_OPEN_LOG = "open_log";
    private static SettingManager instance;
    private SharedPreferences mSharedPreferences;
    private boolean isAutoJump;
    private boolean isAutoScroll;
    private boolean isOpenLog;
    private HashMap<String, Boolean> mModuleSettings;

    private SettingManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        mModuleSettings = new HashMap<>();
        init();
    }

    public static SettingManager getInstance(Context context) {
        if (instance == null) {
            instance = new SettingManager(context);
        }

        return instance;
    }

    private void init() {
        isAutoJump = mSharedPreferences.getBoolean(KEY_AUTO_JUMP, true);
        isAutoScroll = mSharedPreferences.getBoolean(KEY_AUTO_SCROLL, true);
        isOpenLog = mSharedPreferences.getBoolean(KEY_OPEN_LOG, true);
    }

    public void setAutoJump(boolean isAutoJump) {
        this.isAutoJump = isAutoJump;
        mSharedPreferences.edit().putBoolean(KEY_AUTO_JUMP, isAutoJump).apply();
    }

    public void setAutoScroll(boolean isAutoScroll) {
        this.isAutoScroll = isAutoScroll;
        mSharedPreferences.edit().putBoolean(KEY_AUTO_SCROLL, isAutoScroll).apply();
    }

    public void setOpenLog(boolean isOpenLog) {
        this.isOpenLog = isOpenLog;
        mSharedPreferences.edit().putBoolean(KEY_OPEN_LOG, isOpenLog).apply();
    }

    public void setModuleSetting(String module, boolean isOpenLog) {
        if (module == null) {
            return;
        }
        mModuleSettings.put(module, isOpenLog);
        mSharedPreferences.edit().putBoolean(module, isOpenLog).apply();
    }

    public boolean isAutoJump() {
        return isAutoJump;
    }

    public boolean isAutoScroll() {
        return isAutoScroll;
    }

    public boolean isOpenLog() {
        return isOpenLog;
    }

    public boolean getModuleSettings(String module) {
        if (module == null) {
            return false;
        }

        if (mModuleSettings.containsKey(module)) {
            return mModuleSettings.get(module);
        } else {
            boolean isOpenLog = mSharedPreferences.getBoolean(module, true);
            mModuleSettings.put(module, isOpenLog);
            return isOpenLog;
        }
    }
}
