package testmode.eebbk.com.testmodedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import testmode.eebbk.com.testmodedemo.log.LogActivity;
import testmode.eebbk.com.testmodedemo.window.FloatPermissionManager;
import testmode.eebbk.com.testmodedemo.window.FloatWindowController;

/**
 * @author LiXiaoFeng
 * @date 2018/4/15
 * <p>
 * 主页面
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.open_float).setOnClickListener(view -> {
            boolean isPermission = FloatPermissionManager.getInstance().applyFloatWindow(MainActivity.this);
            //有对应权限或者系统版本小于7.0
            if (isPermission || Build.VERSION.SDK_INT < 24) {
                //开启悬浮窗
                FloatWindowController.getInstance().open(getApplicationContext());
            }
        });

        findViewById(R.id.hide_float).setOnClickListener(view -> FloatWindowController.getInstance().hide());

        findViewById(R.id.close_float).setOnClickListener(view -> FloatWindowController.getInstance().close());

        findViewById(R.id.log_btn).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LogActivity.class);
            startActivity(intent);
        });
    }
}
