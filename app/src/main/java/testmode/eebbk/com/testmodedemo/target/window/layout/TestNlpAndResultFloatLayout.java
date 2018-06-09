package testmode.eebbk.com.testmodedemo.target.window.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.nlp.NlpAndResultExcelUtil;
import testmode.eebbk.com.testmodedemo.target.model.DataRepository;
import testmode.eebbk.com.testmodedemo.target.window.FloatWindowController;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public class TestNlpAndResultFloatLayout extends FloatLayout {
    private static final String TAG = TestNlpAndResultFloatLayout.class.getSimpleName();
    private final WindowManager mWindowManager;
    private float mTouchStartX;
    private float mTouchStartY;
    private ImageButton mMoveIbtn;
    private ImageButton mShowIbtn;
    private TextView mLogTv;
    private boolean isVisible;

    public TestNlpAndResultFloatLayout(Context context) {
        this(context, null);
    }

    public TestNlpAndResultFloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.test_nlp_result_float_window_layout, this);
        mMoveIbtn = view.findViewById(R.id.float_move_ibtn);
        mShowIbtn = view.findViewById(R.id.float_show_ibtn);
        ImageButton mRefreshIbtn = view.findViewById(R.id.float_refresh_ibtn);
        ImageButton mCloseIbtn = view.findViewById(R.id.float_close_ibtn);
        mLogTv = view.findViewById(R.id.float_log_tv);

        isVisible = true;
        todoMethod();
        mShowIbtn.setImageResource(isVisible ? R.drawable.icon_visible : R.drawable.icon_gone);
        mShowIbtn.setOnClickListener(v -> {
            isVisible = !isVisible;
            mShowIbtn.setImageResource(isVisible ? R.drawable.icon_visible : R.drawable.icon_gone);
        });
        mRefreshIbtn.setOnClickListener(v -> {
            DataRepository.getInstance().refreshData();
        });
        mCloseIbtn.setOnClickListener(v -> {
            FloatWindowController.getInstance().close();
        });

        view.findViewById(R.id.btn_reset).setOnClickListener(v -> NlpAndResultExcelUtil.mCurrentIndex = 1);

        view.findViewById(R.id.btn_start).setOnClickListener(v -> next());

        String[] reasonArr = new String[]{"测试", "软件", "交互", "内容", "问法", "识别", "后台"};
        ListView listView = view.findViewById(R.id.lv_reason);
        ListAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, reasonArr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            recordOne(reasonArr[position]);
        });
    }

    private void next() {
        try {
            NlpAndResultExcelUtil.getNlpAndResultData(
                    getContext().getApplicationContext(),
                    testNlpResultInfo -> {
//                                mLogTv.setText("当前：" + testNlpResultInfo.index + "," +testNlpResultInfo.data);
                        Log.i("jzy", "当前：" + testNlpResultInfo.index + "," +testNlpResultInfo.data);
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recordOne(String reason) {
        NlpAndResultExcelUtil.updateNlpAndResultData(reason);
        next();
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
