package testmode.eebbk.com.testmodedemo.window;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.common.Constant;
import testmode.eebbk.com.testmodedemo.common.DateUtils;
import testmode.eebbk.com.testmodedemo.common.OnInsertLogEntityListener;
import testmode.eebbk.com.testmodedemo.model.DataRepository;
import testmode.eebbk.com.testmodedemo.model.LogEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/11
 * <p>
 * 应用内唤醒工具栏
 */
public class AppWakeToolView extends LinearLayout {
    private TextView mStatisticsTv;
    private Button mVoiceMistakeBtn;
    private Button mButtonMistakeBtn;
    private OnInsertLogEntityListener mOnInsertLogEntityListener;

    public AppWakeToolView(Context context) {
        this(context, null);
    }

    public AppWakeToolView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_tool_app_wake, this);
        mStatisticsTv = findViewById(R.id.tool_app_wake_statistics_tv);
        mVoiceMistakeBtn = findViewById(R.id.tool_app_wake_voice_mistake_btn);
        mButtonMistakeBtn = findViewById(R.id.tool_app_wake_button_mistake_btn);

        mVoiceMistakeBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.AppWake.Voice.MISTAKE);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            insertLogEntity(logEntity);
            updateStatistics();
        });
        mButtonMistakeBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.AppWake.Button.MISTAKE);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            insertLogEntity(logEntity);
            updateStatistics();
        });

        updateStatistics();
    }

    public void updateStatistics() {
        List<LogEntity> logEntities = DataRepository.getInstance().getData(Constant.LogTarget.AppWake.FILTER);

        if (logEntities == null) {
            return;
        }

        int totalNumber = 0;
        int successNumber = 0;
        int failNumber = 0;
        int mistakeNumber = 0;
        int totalDurationNumber = 0;
        long totalDuration = 0;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                continue;
            }

            switch (logEntity.getTarget()) {
                case Constant.LogTarget.AppWake.Voice.SUCCESS: {
                    successNumber++;
                    break;
                }
                case Constant.LogTarget.AppWake.Voice.FAIL: {
                    failNumber++;
                    break;
                }
                case Constant.LogTarget.AppWake.Voice.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case Constant.LogTarget.AppWake.Voice.DURATION: {
                    totalDuration += logEntity.getSpentTime();
                    totalDurationNumber++;
                    break;
                }
                case Constant.LogTarget.AppWake.Button.SUCCESS: {
                    successNumber++;
                    break;
                }
                case Constant.LogTarget.AppWake.Button.FAIL: {
                    failNumber++;
                    break;
                }
                case Constant.LogTarget.AppWake.Button.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case Constant.LogTarget.AppWake.Button.DURATION: {
                    totalDuration += logEntity.getSpentTime();
                    totalDurationNumber++;
                    break;
                }
                default: {
                    break;
                }
            }
        }

        totalNumber = successNumber + failNumber + mistakeNumber;
        mStatisticsTv.setText(getContext().getString(R.string.app_wake_statistics,
                totalNumber,
                successNumber,
                failNumber,
                mistakeNumber,
                totalNumber == 0 ? 0 : successNumber * 100.0f / totalNumber,
                totalNumber == 0 ? 0 : mistakeNumber * 100.0f / totalNumber,
                totalDurationNumber == 0 ? 0 : totalDuration / totalDurationNumber));
    }

    private void insertLogEntity(LogEntity logEntity) {
        if (logEntity == null) {
            return;
        }

        DataRepository.getInstance().insertData(logEntity);
        if (mOnInsertLogEntityListener != null) {
            mOnInsertLogEntityListener.onInsertLogEntity(logEntity);
        }
    }

    public void setOnInsertLogEntityListener(OnInsertLogEntityListener onInsertLogEntityListener) {
        mOnInsertLogEntityListener = onInsertLogEntityListener;
    }
}
