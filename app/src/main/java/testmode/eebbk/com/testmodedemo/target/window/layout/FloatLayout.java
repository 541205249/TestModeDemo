package testmode.eebbk.com.testmodedemo.target.window.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * 作者： jiazy
 * 日期： 2018/6/9.
 * 公司： 步步高教育电子有限公司
 * 描述：
 */
public class FloatLayout extends FrameLayout {
    public final WindowManager mWindowManager;
    public WindowManager.LayoutParams mWmParams;
    private float mTouchStartX;
    private float mTouchStartY;

    public FloatLayout(@NonNull Context context) {
        super(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public FloatLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public FloatLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public void setParams(WindowManager.LayoutParams params) {
        mWmParams = params;
    }

    public void moveLayout(MotionEvent event) {
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
    }
}
