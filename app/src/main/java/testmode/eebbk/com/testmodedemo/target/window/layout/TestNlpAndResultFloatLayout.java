package testmode.eebbk.com.testmodedemo.target.window.layout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.nlp.NlpAndResultActivity;
import testmode.eebbk.com.testmodedemo.nlp.NlpAndResultExcelUtil;
import testmode.eebbk.com.testmodedemo.target.window.FloatWindowController;
import testmode.eebbk.com.testmodedemo.util.BroadCastUtils;

import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.ACTION_TEST_NLP;
import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.RECEIVE_ACTION_NLP_RESULT;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public class TestNlpAndResultFloatLayout extends FloatLayout {
    private final WindowManager mWindowManager;
    private float mTouchStartX;
    private float mTouchStartY;
    private ImageButton mMoveIbtn;
    private ImageButton mShowIbtn;
    private TextView mLogTv;
    private boolean isVisible, isStarted;
    private String mNlpResult, mTarget;


    public TestNlpAndResultFloatLayout(Context context) {
        this(context, null);
    }

    public TestNlpAndResultFloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        initView(context);
        registerTestReceiver(context);
    }

    private void registerTestReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.eebbk.askhomework.send.nlp_result.action");
        context.getApplicationContext().registerReceiver(new NlpResultReceiver(), filter);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.test_nlp_result_float_window_layout, this);
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

        view.findViewById(R.id.btn_reset).setOnClickListener(v -> NlpAndResultExcelUtil.updateCurrentIndex(1));

        view.findViewById(R.id.btn_start).setOnClickListener(v -> {
            if (!isStarted) {
                BroadCastUtils.sendTestNlpBroadcast(context, ACTION_TEST_NLP);
                isStarted = true;
            }
            int currentIndex = Integer.parseInt(NlpAndResultActivity.mQuestionIndexEt.getText().toString().trim());
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
            if (RECEIVE_ACTION_NLP_RESULT.equals(intent.getAction())) {
                mNlpResult = intent.getStringExtra("msg");
                mTarget = intent.getStringExtra("target");
                mLogTv.setText("当前为第" + NlpAndResultExcelUtil.getCurrentIndex() + "题," + mTarget);
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
