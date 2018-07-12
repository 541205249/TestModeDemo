package testmode.eebbk.com.testmodedemo.nlp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.target.window.layout.ControlBarManager;
import testmode.eebbk.com.testmodedemo.target.window.layout.FloatLayout;
import testmode.eebbk.com.testmodedemo.util.BroadCastUtils;

import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.ACTION_TEST_NLP;
import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.RECEIVE_ACTION_NLP;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public class TestNlpFloatLayout extends FloatLayout {
    private TextView mLogTv;
    private boolean isVisible, isStarted;
    private String mNlpResult, mTarget;

    public TestNlpFloatLayout(Context context) {
        this(context, null);
    }

    public TestNlpFloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
        registerTestReceiver(context);
        new ControlBarManager(this);
    }

    private void registerTestReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(RECEIVE_ACTION_NLP);
        context.getApplicationContext().registerReceiver(new NlpResultReceiver(), filter);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.test_nlp_result_float_window_layout, this);
        mLogTv = view.findViewById(R.id.float_log_tv);

        isVisible = true;

        view.findViewById(R.id.btn_reset).setOnClickListener(v -> NlpAndResultExcelUtil.updateCurrentIndex(1));

        view.findViewById(R.id.btn_start).setOnClickListener(v -> {
            if (!isStarted) {
                BroadCastUtils.registerBroadcast(context, ACTION_TEST_NLP);
                isStarted = true;
            }
            int currentIndex = Integer.parseInt(NlpActivity.mQuestionIndexEt.getText().toString().trim());
            NlpAndResultExcelUtil.updateCurrentIndex(currentIndex);
            next();
        });

        String[] reasonArr = new String[]{"正确", "错误", "无结果", "未知"};
        GridView listView = view.findViewById(R.id.lv_reason);
        ListAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, reasonArr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            recordOne(reasonArr[position]);
        });
    }

    private void next() {
        try {
            NlpAndResultExcelUtil.getNlpAndResultData(getContext().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recordOne(String result) {
        NlpAndResultExcelUtil.updateNlpAndResultData(result, mTarget, mNlpResult);
        next();
        mLogTv.setText("当前为第" + NlpAndResultExcelUtil.getCurrentIndex() + "题");
    }

    class NlpResultReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (RECEIVE_ACTION_NLP.equals(intent.getAction())) {
                mNlpResult = intent.getStringExtra("msg");
                mTarget = intent.getStringExtra("target");
                mLogTv.setText("当前为第" + NlpAndResultExcelUtil.getCurrentIndex() + "题," + mTarget);
            }
        }
    }

}
