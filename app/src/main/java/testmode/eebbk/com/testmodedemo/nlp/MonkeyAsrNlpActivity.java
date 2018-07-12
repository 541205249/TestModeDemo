package testmode.eebbk.com.testmodedemo.nlp;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.target.window.FloatPermissionManager;
import testmode.eebbk.com.testmodedemo.target.window.FloatWindowController;
import testmode.eebbk.com.testmodedemo.util.BroadCastUtils;

import static testmode.eebbk.com.testmodedemo.target.window.FloatWindow.TYPE_TEST_NLP;
import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.ACTION_MONKEY_NLP;
import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.ACTION_TEST_ASR;

public class MonkeyAsrNlpActivity extends Activity {
    private static final String TAG = "Log_MonkeyAsrNlp";
    private TextView mAsrTxtTv;
    private TextView mNlpTxtTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monkey_asr_nlp);

        BroadCastUtils.registerBroadcast(this, ACTION_MONKEY_NLP);

        EventBus.getDefault().register(this);

        mAsrTxtTv = findViewById(R.id.tv_asr);
        mNlpTxtTv = findViewById(R.id.tv_nlp);

        findViewById(R.id.btn_start_asr).setOnClickListener(v -> startMonkeyAsr());
        findViewById(R.id.btn_start_nlp).setOnClickListener(v -> {
            startMonkeyNlp();
            boolean isPermission = FloatPermissionManager.getInstance().applyFloatWindow(MonkeyAsrNlpActivity.this);
            //有对应权限或者系统版本小于7.0
            if (isPermission || Build.VERSION.SDK_INT < 24) {
                //开启悬浮窗
                FloatWindowController.getInstance().open(getApplicationContext(), TYPE_TEST_NLP);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    private void startMonkeyNlp() {
        try {
            MonkeyNlpExcelUtil.getUnderstandingData(getApplicationContext(), "语义理解monkey测试.xls");
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    private void startMonkeyAsr() {
        BroadCastUtils.registerBroadcast(this, ACTION_TEST_ASR);

        Intent intent = new Intent(ACTION_TEST_ASR);
        intent.putExtra("pcm_path", Environment.getExternalStorageDirectory().getAbsolutePath() + "/001.pcm");
        sendBroadcast(intent);

        /*String pcm = AsrFileUtils.getPcmPath();
        if (TextUtils.isEmpty(pcm)) {
            Log.i(TAG, "pcm为空");
        } else {
            Intent intent = new Intent(ACTION_MONKEY_ASR);
            intent.putExtra("path", pcm);
            sendBroadcast(intent);

            mAsrTxtTv.setText(pcm);
        }

        new Handler().postDelayed(this::startMonkeyAsr, 10 * 1000);*/
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

        mNlpTxtTv.setText(understandingInfo.getData());

        new Handler().postDelayed(this::startMonkeyNlp, 10 * 1000);
    }
}
