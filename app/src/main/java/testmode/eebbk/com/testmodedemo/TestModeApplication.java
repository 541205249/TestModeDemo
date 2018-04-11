package testmode.eebbk.com.testmodedemo;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import testmode.eebbk.com.testmodedemo.model.DataRepository;
import testmode.eebbk.com.testmodedemo.window.FloatLayout2;
import testmode.eebbk.com.testmodedemo.window.FloatWindow;
import testmode.eebbk.com.testmodedemo.window.FloatWindowController;

/**
 * @author LiXiaoFeng
 * @date 2018/4/11
 */
public class TestModeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataRepository.getInstance().init(this.getApplicationContext());
    }
}
