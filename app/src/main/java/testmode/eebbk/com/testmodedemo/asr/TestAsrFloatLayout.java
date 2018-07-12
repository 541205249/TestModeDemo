package testmode.eebbk.com.testmodedemo.asr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.asr.AsrExcelUtil;
import testmode.eebbk.com.testmodedemo.asr.AsrFileUtils;
import testmode.eebbk.com.testmodedemo.nlp.NlpAndResultExcelUtil;
import testmode.eebbk.com.testmodedemo.target.window.FloatWindowController;
import testmode.eebbk.com.testmodedemo.target.window.layout.FloatLayout;
import testmode.eebbk.com.testmodedemo.util.BroadCastUtils;

import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.ACTION_TEST_ASR;
import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.ACTION_TEST_NLP;
import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.RECEIVE_ACTION_ASR_RESULT;

public class TestAsrFloatLayout extends FloatLayout {
    private final WindowManager mWindowManager;
    private float mTouchStartX;
    private float mTouchStartY;
    private ImageButton mMoveIbtn;
    private ImageButton mShowIbtn;
    private TextView mLogTv;
    private boolean isVisible, isStarted;
    private String mAsrResult, mResult;
    private int mCurrentIndex = 1;
    private Context mContext;

    public TestAsrFloatLayout(Context context) {
        this(context, null);
    }

    public TestAsrFloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        initView(context);
        registerTestReceiver(context);
    }

    private void registerTestReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(RECEIVE_ACTION_ASR_RESULT);
        context.getApplicationContext().registerReceiver(new AsrResultReceiver(), filter);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.test_asr_float_window_layout, this);
        mMoveIbtn = view.findViewById(R.id.float_move_ibtn);
        mShowIbtn = view.findViewById(R.id.float_show_ibtn);
        ImageButton mCloseIbtn = view.findViewById(R.id.float_close_ibtn);
        mLogTv = view.findViewById(R.id.float_log_tv);

        isVisible = true;
        todoMethod();
        mShowIbtn.setImageResource(isVisible ? R.drawable.icon_visible : R.drawable.icon_gone);
        mShowIbtn.setOnClickListener(v -> {
            isVisible = !isVisible;
            mShowIbtn.setImageResource(isVisible ? R.drawable.icon_visible : R.drawable.icon_gone);
        });
        mCloseIbtn.setOnClickListener(v -> {
            FloatWindowController.getInstance().close();
        });

        view.findViewById(R.id.btn_start).setOnClickListener(v -> {
            if (!isStarted) {
                BroadCastUtils.registerBroadcast(context, ACTION_TEST_NLP);
                isStarted = true;
            }
            sendOneFile();
        });

    }

    private void sendOneFile() {
        String pcmPath = AsrFileUtils.getPcmPath(mCurrentIndex);

        Intent intent = new Intent(ACTION_TEST_ASR);
        intent.putExtra("pcm_path", pcmPath);
        mContext.getApplicationContext().sendBroadcast(intent);
    }

    private void recordOne(String result) {
        AsrExcelUtil.updateNlpAndResultData(result, "", mAsrResult);
        mLogTv.setText("当前为第" + NlpAndResultExcelUtil.getCurrentIndex() + "题");

        mCurrentIndex++;
        sendOneFile();
    }

    class AsrResultReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (RECEIVE_ACTION_ASR_RESULT.equals(intent.getAction())) {
                mAsrResult = intent.getStringExtra("result");
                mLogTv.setText("当前为第" + NlpAndResultExcelUtil.getCurrentIndex() + "个," + mAsrResult);
            }
        }
    }

    private void todoMethod() {
        mMoveIbtn.setOnTouchListener((v, event) -> {
            int rx = (int) event.getRawX();
            int ry = (int) event.getRawY();
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    mTouchStartX = event.getX();
                    mTouchStartY = event.getY();
                }
                case MotionEvent.ACTION_MOVE: {
                    if (Math.abs(mTouchStartX - event.getX()) > 3
                            && Math.abs(mTouchStartY - event.getY()) > 3) {
                        // 更新浮动窗口位置参数
                        mWmParams.x = (int) (rx - mTouchStartX);
                        mWmParams.y = (int) (ry - mTouchStartY);
                        mWindowManager.updateViewLayout(this, mWmParams);
                    }
                }
                default: {
                    break;
                }
            }
            return true;
        });
    }
}
