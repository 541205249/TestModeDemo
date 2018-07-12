package testmode.eebbk.com.testmodedemo.whole;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
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

import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.ACTION_TEST_ASR;
import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.ACTION_TEST_NLP;
import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.RECEIVE_ACTION_ASR_RESULT;
import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.RECEIVE_ACTION_NLP;

public class TestWholeFloatLayout extends FloatLayout {
    private Context mContext;
    private String mOriginalTxt, mAsrTxt, mAsrResult, mNlpTxt, mNlpMsg;
    private TextView mCurrentIndexTv, mOriginalTv, mAsrTxtTv, mAsrResultTv, mNlpResultTv;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mOriginalTv.setText("标注文本:" + mOriginalTxt);
        }
    };

    public TestWholeFloatLayout(Context context) {
        this(context, null);
    }

    public TestWholeFloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initView(context);
        registerTestReceiver(context);
        new ControlBarManager(this);
    }

    private void registerTestReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(RECEIVE_ACTION_ASR_RESULT);
        context.getApplicationContext().registerReceiver(new ResultReceiver(), filter);

        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(RECEIVE_ACTION_NLP);
        context.getApplicationContext().registerReceiver(new ResultReceiver(), filter2);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.test_whole_window_layout, this);

        mCurrentIndexTv = view.findViewById(R.id.tv_index);
        mOriginalTv = view.findViewById(R.id.tv_original_txt);
        mAsrTxtTv = view.findViewById(R.id.tv_asr_txt);
        mAsrResultTv = view.findViewById(R.id.tv_asr_result);
        mNlpResultTv = view.findViewById(R.id.tv_nlp_result);

        view.findViewById(R.id.btn_start).setOnClickListener(v -> {
            BroadCastUtils.registerBroadcast(context, ACTION_TEST_ASR);
            BroadCastUtils.registerBroadcast(context, ACTION_TEST_NLP);

            int currentIndex = Integer.parseInt(WholeActivity.mQuestionIndexEt.getText().toString().trim());
            WholeExcelUtil.updateCurrentIndex(currentIndex);
            sendOneFile();
        });

        view.findViewById(R.id.btn_stop).setOnClickListener(v -> {
            //TODO
        });

        String[] resultArr = new String[]{"正确", "ASR", "ASR+NLP+搜索", "NLP+搜索", "NLP+Monkey"};
        GridView gridView = findViewById(R.id.gv_whole_result);
        ListAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.txt_item, R.id.tv, resultArr);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            recordOne(resultArr[position]);
        });
    }

    private void sendOneFile() {
        mCurrentIndexTv.setText("当前序号:" + WholeExcelUtil.getCurrentIndex());
        mAsrResultTv.setText("识别中");

        WholeExcelUtil.getWholeTestData(getContext(), info -> {
            mOriginalTxt = info.txt;
            mHandler.sendEmptyMessage(0);

            Intent intent = new Intent(ACTION_TEST_ASR);
            intent.putExtra("pcm_path", WholeExcelUtil.FILE_DIR + "/" + info.pcmName);
            mContext.getApplicationContext().sendBroadcast(intent);
        });
    }

    private void recordOne(String result) {
        WholeExcelUtil.updateResultData(result, mNlpTxt, mNlpMsg, mAsrTxt, mAsrResult);

        sendOneFile();
    }

    class ResultReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (RECEIVE_ACTION_ASR_RESULT.equals(intent.getAction())) {
                mAsrTxt = intent.getStringExtra("asr_result");
                mAsrResult = getAsrResult();

                mAsrTxtTv.setText("识别文本:" + mAsrTxt);
                mAsrResultTv.setText(mAsrResult);
            }

            if (RECEIVE_ACTION_NLP.equals(intent.getAction())) {
                mNlpTxt = intent.getStringExtra("target");
                mNlpMsg = intent.getStringExtra("msg");

                mNlpResultTv.setText("语义理解:" + mNlpTxt);
            }
        }
    }

    private String getAsrResult() {
        return mOriginalTxt.equals(mAsrTxt) ? "正确" : "错误";
    }
}
