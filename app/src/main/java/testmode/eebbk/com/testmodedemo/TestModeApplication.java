package testmode.eebbk.com.testmodedemo;

import android.app.Application;
import android.content.Context;

import testmode.eebbk.com.testmodedemo.model.DataRepository;

/**
 * @author LiXiaoFeng
 * @date 2018/4/11
 * <p>
 * 全局Application
 */
public class TestModeApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        DataRepository.getInstance().init(this.getApplicationContext());
    }

    public static Context getTestModeApplicationContext() {
        return mContext;
    }
}
