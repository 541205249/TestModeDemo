package testmode.eebbk.com.testmodedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import testmode.eebbk.com.testmodedemo.nlp.NlpExcelUtil;
import testmode.eebbk.com.testmodedemo.nlp.UnderstandingInfo;

public class MonkeyNlpActivity extends Activity {
    private static final String TAG = "Log_MonkeyNlp";
    private static final String ACTION_MONKEY_NLP = "com.eebbk.askhomework.monkeynlp.action";
    private TextView mTxtTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monkey_nlp);

        EventBus.getDefault().register(this);

        mTxtTv = findViewById(R.id.tv_nlp);
        findViewById(R.id.btn_start).setOnClickListener(v -> {
            startMonkeyNlp();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    private void startMonkeyNlp() {
        new Handler().postDelayed(() -> {
            try {
                NlpExcelUtil.getUnderstandingData(getApplicationContext(), "语义理解monkey测试.xls");
            } catch (Exception e) {
                Log.i(TAG, e.getMessage());
                e.printStackTrace();
            }
        }, 3000);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UnderstandingInfo understandingInfo) {
        Log.i(TAG, understandingInfo.toString());

        if (TextUtils.isEmpty(understandingInfo.getData())) {
            Log.i(TAG, "data为空");
            return;
        }

        Intent intent = new Intent(ACTION_MONKEY_NLP);
        intent.putExtra("text", understandingInfo.getData());
        sendBroadcast(intent);

        mTxtTv.setText(understandingInfo.getData());
        startMonkeyNlp();
    }
}
