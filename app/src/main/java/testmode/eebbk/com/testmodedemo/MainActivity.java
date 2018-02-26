package testmode.eebbk.com.testmodedemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.eebbk.testmodetool.floatwindow.FloatPermissionManager;
import com.eebbk.testmodetool.floatwindow.FloatWindow;
import com.eebbk.testmodetool.floatwindow.FloatWindowController;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.open_float).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isPermission = FloatPermissionManager.getInstance().applyFloatWindow(MainActivity.this);
                //有对应权限或者系统版本小于7.0
                if (isPermission || Build.VERSION.SDK_INT < 24) {
                    //开启悬浮窗
                    FloatWindowController.getInstance().open(getApplicationContext());
                }
            }
        });

        findViewById(R.id.hide_float).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatWindowController.getInstance().hide();
            }
        });

        findViewById(R.id.close_float).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatWindowController.getInstance().close();
            }
        });

    }
}