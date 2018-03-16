package testmode.eebbk.com.testmodedemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eebbk.testmodetool.excel.ExcelUtil;
import com.eebbk.testmodetool.excel.QualityData;
import com.eebbk.testmodetool.floatwindow.FloatPermissionManager;
import com.eebbk.testmodetool.floatwindow.FloatWindowController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<QualityData> qualityList = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    QualityData qualityData = new QualityData();
                    qualityData.setName("指标_" + i);
                    qualityData.setTime(i * 1000);
                    qualityList.add(qualityData);
                }
                try {
                    ExcelUtil.writeExcel(MainActivity.this,
                            qualityList, "excel_"+new Date().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.testmode.action");
        registerReceiver(new DynamicReceiver(), filter);
    }

    class DynamicReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String target = intent.getStringExtra("target");
            long timeDifference = intent.getLongExtra("timeDifference", 1);
            Toast.makeText(context,"动态广播：" + target + ",timeDifference=" + timeDifference, Toast.LENGTH_SHORT).show();
        }
    }
}
