package testmode.eebbk.com.testmodedemo;

import android.app.Application;
import android.content.Context;

import testmode.eebbk.com.testmodedemo.target.model.DataRepository;

/**
 * @author LiXiaoFeng
 * @date 2018/4/11
 * <p>
 * 全局Application
 */
public class TestModeApplication extends Application {
    private static Context mContext;
    private static int mRecordFilter = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        DataRepository.getInstance().init(this.getApplicationContext());
    }

    public static Context getContext() {
        return mContext;
    }

    public static int getmRecordFilter() {
        return mRecordFilter;
    }

    public static void setmRecordFilter() {
        TestModeApplication.mRecordFilter = mRecordFilter + 1;
    }
}
