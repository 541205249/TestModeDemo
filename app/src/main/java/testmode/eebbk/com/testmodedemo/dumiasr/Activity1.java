package testmode.eebbk.com.testmodedemo.dumiasr;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.nlp.TestNlpResultInfo;
import testmode.eebbk.com.testmodedemo.target.window.FloatPermissionManager;
import testmode.eebbk.com.testmodedemo.target.window.FloatWindowController;

import static testmode.eebbk.com.testmodedemo.target.window.FloatWindow.TYPE_TEST_NLP_RESULT;

public class Activity1 extends Activity {
    private static final String TAG = "Log_NlpAndResult";
    private static final String ACTION_MONKEY_NLP = "com.eebbk.askhomework.monkeynlp.action";
    private TextView mNlpTxtTv;
    private EditText mQuestionEt;
    public static EditText mQuestionIndexEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nlp_and_result);

        EventBus.getDefault().register(this);

        mNlpTxtTv = findViewById(R.id.tv_nlp);
        mQuestionEt = findViewById(R.id.edit_question);
        mQuestionIndexEt = findViewById(R.id.edit_question_index);

        findViewById(R.id.btn_start_nlp).setOnClickListener(v -> {
            boolean isPermission = FloatPermissionManager.getInstance().applyFloatWindow(Activity1.this);
            //有对应权限或者系统版本小于7.0
            if (isPermission || Build.VERSION.SDK_INT < 24) {
                //开启悬浮窗
                FloatWindowController.getInstance().open(getApplicationContext(), TYPE_TEST_NLP_RESULT);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TestNlpResultInfo testNlpResultInfo) {
        Log.i(TAG, testNlpResultInfo.toString());

        if (TextUtils.isEmpty(testNlpResultInfo.data)) {
            Log.i(TAG, "data为空");
            return;
        }

        Intent intent = new Intent(ACTION_MONKEY_NLP);
        intent.putExtra("text", testNlpResultInfo.data);
        intent.putExtra("mode", 1);
        sendBroadcast(intent);

        mNlpTxtTv.setText(testNlpResultInfo.data);
    }
}
