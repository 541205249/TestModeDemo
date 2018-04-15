package testmode.eebbk.com.testmodedemo;

import android.app.Application;

import testmode.eebbk.com.testmodedemo.model.DataRepository;

/**
 * @author LiXiaoFeng
 * @date 2018/4/11
 * <p>
 * 全局Application
 */
public class TestModeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataRepository.getInstance().init(this.getApplicationContext());
    }
}
