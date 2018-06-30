package testmode.eebbk.com.testmodedemo.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreference保存获取的工具类
 * Created by Simon on 2016/9/29.
 */
public class SharedPreferenceUtils {
    private static final String TAG = "BfcCommon_SPUtils";
    public static final String DEFAULT_PREFERENCES_FILE_NAME = "bfc_share_preference";
    private final SharedPreferences mPreferences;

    private SharedPreferenceUtils(Context ctx, String filePath, int mode) {
        mPreferences = ctx.getApplicationContext().getSharedPreferences(filePath, mode);
    }

    private volatile static SharedPreferenceUtils sInstance;

    /**
     * 获取实例, 使用默认文件保存sharePreferences数据的
     *
     * @return 返回单例对象
     */
    public static SharedPreferenceUtils getInstance(Context ctx) {
        if (sInstance == null) {
            synchronized (SharedPreferenceUtils.class) {
                if (sInstance == null) {
                    sInstance = new SharedPreferenceUtils(ctx, DEFAULT_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
                }
            }
        }

        return sInstance;
    }

    /**
     * 获取实例, 使用指定的文件保存sharePreferences数据的
     *
     * @param fileName sharePreference文件名
     */
    public static SharedPreferenceUtils getInstance(Context ctx, String fileName) {
        return new SharedPreferenceUtils(ctx, fileName, Context.MODE_PRIVATE);
    }

    /**
     * 获取实例, 使用指定的文件,和模式保存sharePreferences数据的
     *
     * 用处不大 暂时不开放api
     *
     * @param fileName sharePreference文件名
     * @param mode     保存文件的模式, {@link Context#MODE_PRIVATE }, @{@link Context#MODE_APPEND}, @{@link Context#MODE_WORLD_READABLE }, @{@link Context#MODE_WORLD_WRITEABLE }
     */
    private static SharedPreferenceUtils getInstance(Context ctx, String fileName, int mode) {
        return new SharedPreferenceUtils(ctx, fileName, mode);
    }

    /***
     * 获取对应SharedPreferences对象;
     * <p>
     * 用于获取具体的SharedPreferences对象, 用于特殊需求;  eg. 监听SharedPreferences的变化
     */
    public SharedPreferences getSharedPreference() {
        return mPreferences;
    }

    /**
     * 保存数据到SharePreferences
     */
    public void put(String key, int value) {
        this.putAuto(key, value);
    }

    public void put(String key, float value) {
        this.putAuto(key, value);
    }

    public void put(String key, long value) {
        this.putAuto(key, value);
    }

    public void put(String key, boolean value) {
        this.putAuto(key, value);
    }

    public void put(String key, String value) {
        this.putAuto(key, value);
    }

    private void putAuto(String key, Object value) {
        if (key == null){
            return;
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else {
            editor.putString(key, value.toString());
        }

        editor.apply();
    }

    /**
     * 从SharedPreference获取数据
     */
    public int get(String key, int defaultValue) {
        try {
            return mPreferences.getInt(key, defaultValue);
        } catch (ClassCastException e) {
            throw e;
        }
    }

    public float get(String key, float defaultValue) {
        return mPreferences.getFloat(key, defaultValue);
    }

    /**
     * 从SharedPreference获取long型数据
     *
     * @param defaultValue 默认defaultValue一定要是long型, 不然返回值可能不对, 必要时强转类型; eg, 100L
     */
    public long get(String key, long defaultValue) {
        return mPreferences.getLong(key, defaultValue);
    }

    public boolean get(String key, boolean defaultValue) {
        return mPreferences.getBoolean(key, defaultValue);
    }

    public String get(String key, String defaultValue) {
        return mPreferences.getString(key, defaultValue);
    }


    /**
     * 清楚保存的指定数据
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     *  慎用, 清除保存的所有数据
     */
    public void clear(){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
