package testmode.eebbk.com.testmodedemo.window;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.common.Constant;
import testmode.eebbk.com.testmodedemo.model.DataRepository;
import testmode.eebbk.com.testmodedemo.model.LogEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/11
 * <p>
 * APP端页面展示工具栏
 */
public class DisplayToolView extends LinearLayout {
    private TextView mStatisticsTv;

    public DisplayToolView(Context context) {
        this(context, null);
    }

    public DisplayToolView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_tool_display, this);
        mStatisticsTv = findViewById(R.id.tool_display_statistics_tv);

        updateStatistics();
    }

    public void updateStatistics() {
        List<LogEntity> logEntities = DataRepository.getInstance().getData(Constant.LogTarget.Display.FILTER);

        if (logEntities == null) {
            return;
        }

        int totalNumber = 0;
        long totalDisplayDuration = 0;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                continue;
            }

            switch (logEntity.getTarget()) {
                case Constant.LogTarget.Display.DURATION: {
                    totalNumber++;
                    totalDisplayDuration += logEntity.getSpentTime();
                    break;
                }
                default: {
                    break;
                }
            }
        }

        mStatisticsTv.setText(getContext().getString(R.string.display_statistics,
                totalNumber == 0 ? 0 : totalDisplayDuration / totalNumber));
    }
}
