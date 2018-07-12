package testmode.eebbk.com.testmodedemo.target.window.layout;

import android.annotation.SuppressLint;
import android.widget.ImageButton;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.target.window.FloatWindowController;

/**
 * 作者： jiazy
 * 日期： 2018/7/7.
 * 公司： 步步高教育电子有限公司
 * 描述：
 */
public class ControlBarManager {

    @SuppressLint("ClickableViewAccessibility")
    public ControlBarManager(FloatLayout view) {
        ImageButton ibMove = view.findViewById(R.id.float_move_ibtn);
        ImageButton ibClose = view.findViewById(R.id.float_close_ibtn);

        ibClose.setOnClickListener(v -> {
            FloatWindowController.getInstance().close();
        });

        ibMove.setOnTouchListener((v, event) -> {
            view.moveLayout(event);
            return true;
        });
    }

}
