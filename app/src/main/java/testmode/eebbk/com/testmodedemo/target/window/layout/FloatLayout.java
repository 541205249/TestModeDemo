package testmode.eebbk.com.testmodedemo.target.window.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * 作者： jiazy
 * 日期： 2018/6/9.
 * 公司： 步步高教育电子有限公司
 * 描述：
 */
public class FloatLayout extends FrameLayout {
    WindowManager.LayoutParams mWmParams;

    public FloatLayout(@NonNull Context context) {
        super(context);
    }

    public FloatLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setParams(WindowManager.LayoutParams params) {
        mWmParams = params;
    }

}
