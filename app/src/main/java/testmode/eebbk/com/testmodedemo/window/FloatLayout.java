package testmode.eebbk.com.testmodedemo.window;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import testmode.eebbk.com.testmodedemo.R;


public class FloatLayout extends FrameLayout {
    private final WindowManager mWindowManager;
    private float mTouchStartX;
    private float mTouchStartY;
    private WindowManager.LayoutParams mWmParams;
    private TextView testTxt;

    public FloatLayout(Context context) {
        this(context, null);
    }

    public FloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initView(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取相对屏幕的坐标，即以屏幕左上角为原点
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        //下面的这些事件，跟图标的移动无关，为了区分开拖动和点击事件
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //图标移动的逻辑在这里
                float mMoveStartX = event.getX();
                float mMoveStartY = event.getY();
                // 如果移动量大于3才移动
                if (Math.abs(mTouchStartX - mMoveStartX) > 3
                        && Math.abs(mTouchStartY - mMoveStartY) > 3) {
                    // 更新浮动窗口位置参数
                    mWmParams.x = (int) (x - mTouchStartX);
                    mWmParams.y = (int) (y - mTouchStartY);
                    mWindowManager.updateViewLayout(this, mWmParams);
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     * @param params 小悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mWmParams = params;
    }

    public void setTestTxt(String s){
        testTxt.setText(s);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.float_window_layout, this);
        testTxt = view.findViewById(R.id.test);

        final ScrollView contentLayout = view.findViewById(R.id.contentLayout);
        view.findViewById(R.id.show_view_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = contentLayout.getVisibility() == View.GONE ? View.VISIBLE : View.GONE;
                contentLayout.setVisibility(visibility);
            }
        });
    }
}
