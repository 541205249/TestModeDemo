package testmode.eebbk.com.testmodedemo.window;

import android.content.Context;
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
 * @date 2018/4/10
 * <p>
 * 系统唤醒工具栏
 */
public class SystemWakeToolView extends LinearLayout {
    private TextView mStatisticsTv;
    private Button mBlackVoiceMistakeBtn;
    private Button mBlackGestureMistakeBtn;
    private Button mLockVoiceMistakeBtn;
    private Button mBrightVoiceMistakeBtn;
    private Button mBrightButtonMistakeBtn;
    private OnInsertLogEntityListener mOnInsertLogEntityListener;

    public SystemWakeToolView(Context context) {
        this(context, null);
    }

    public SystemWakeToolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_tool_system_wake, this);
        mStatisticsTv = findViewById(R.id.tool_system_wake_statistics_tv);
        mBlackVoiceMistakeBtn = findViewById(R.id.tool_system_wake_black_voice_mistake_btn);
        mBlackGestureMistakeBtn = findViewById(R.id.tool_system_wake_black_gesture_mistake_btn);
        mLockVoiceMistakeBtn = findViewById(R.id.tool_system_wake_lock_voice_mistake_btn);
        mBrightVoiceMistakeBtn = findViewById(R.id.tool_system_wake_bright_voice_mistake_btn);
        mBrightButtonMistakeBtn = findViewById(R.id.tool_system_wake_bright_button_mistake_btn);

        mBlackVoiceMistakeBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.SystemWake.Black.Voice.MISTAKE);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            insertLogEntity(logEntity);
            updateStatistics();
        });
        mBlackGestureMistakeBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.SystemWake.Black.Gesture.MISTAKE);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            insertLogEntity(logEntity);
            updateStatistics();
        });
        mLockVoiceMistakeBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.SystemWake.Lock.Voice.MISTAKE);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            insertLogEntity(logEntity);
            updateStatistics();
        });
        mBrightVoiceMistakeBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.SystemWake.Bright.Voice.MISTAKE);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            insertLogEntity(logEntity);
        });
        mBrightButtonMistakeBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.SystemWake.Bright.Button.MISTAKE);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            insertLogEntity(logEntity);
        });

        updateStatistics();
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

    public void updateStatistics() {
        List<LogEntity> logEntities = DataRepository.getInstance().getData(Constant.LogTarget.SystemWake.FILTER);

        if (logEntities == null) {
            return;
        }

        int totalNumber = 0;
        int successNumber = 0;
        int failNumber = 0;
        int mistakeNumber = 0;
        long systemTotalDuration = 0;
        long appTotalDuration = 0;
        long systemWakeTotalDuration = 0;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                return;
            }

            switch (logEntity.getTarget()) {
                case Constant.LogTarget.SystemWake.Black.Voice.SUCCESS: {
                    successNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Voice.FAIL: {
                    failNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Voice.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Voice.SYSTEM_DURATION: {
                    systemTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Voice.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Voice.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Gesture.SUCCESS: {
                    successNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Gesture.FAIL: {
                    failNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Gesture.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Gesture.SYSTEM_DURATION: {
                    systemTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Gesture.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Gesture.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Lock.Voice.SUCCESS: {
                    successNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Lock.Voice.FAIL: {
                    failNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Lock.Voice.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Lock.Voice.SYSTEM_DURATION: {
                    systemTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Lock.Voice.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Lock.Voice.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Voice.SUCCESS: {
                    successNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Voice.FAIL: {
                    failNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Voice.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Voice.SYSTEM_DURATION: {
                    systemTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Voice.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Voice.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Button.SUCCESS: {
                    successNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Button.FAIL: {
                    failNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Button.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Button.SYSTEM_DURATION: {
                    systemTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Button.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Button.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    break;
                }
                default: {
                    break;
                }
            }
        }

        totalNumber = successNumber + failNumber + mistakeNumber;
        mStatisticsTv.setText(getContext().getString(R.string.system_wake_statistics,
                totalNumber,
                successNumber,
                failNumber,
                mistakeNumber,
                totalNumber == 0 ? 0 : successNumber * 100.0f / totalNumber,
                totalNumber == 0 ? 0 : mistakeNumber * 100.0f / totalNumber,
                totalNumber == 0 ? 0 : systemTotalDuration / totalNumber,
                totalNumber == 0 ? 0 : appTotalDuration / totalNumber,
                totalNumber == 0 ? 0 : systemWakeTotalDuration / totalNumber));
    }

    public void setOnInsertLogEntityListener(OnInsertLogEntityListener onInsertLogEntityListener) {
        mOnInsertLogEntityListener = onInsertLogEntityListener;
    }
}
