package testmode.eebbk.com.testmodedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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

        findViewById(R.id.btn1).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TargetCheckActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MonkeyNlpActivity.class);
            startActivity(intent);
        });
    }
}
