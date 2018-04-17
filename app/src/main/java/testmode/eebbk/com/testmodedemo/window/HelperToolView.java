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
 * 作业助手工具栏
 */
public class HelperToolView extends LinearLayout {
    private TextView mStatisticsTv;
    private Button mSuccessBtn;
    private Button mFailBtn;
    private OnInsertLogEntityListener mOnInsertLogEntityListener;

    public HelperToolView(Context context) {
        this(context, null);
    }

    public HelperToolView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_tool_helper, this);
        mStatisticsTv = findViewById(R.id.tool_helper_statistics_tv);
        mSuccessBtn = findViewById(R.id.tool_helper_success_btn);
        mFailBtn = findViewById(R.id.tool_helper_fail_btn);

        mSuccessBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.Helper.SUCCESS);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            insertLogEntity(logEntity);
            updateStatistics();
        });
        mFailBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.Helper.FAIL);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            insertLogEntity(logEntity);
            updateStatistics();
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
        List<LogEntity> logEntities = DataRepository.getInstance().getData(Constant.LogTarget.Helper.FILTER);

        if (logEntities == null) {
            return;
        }

        int totalNumber = 0;
        int successNumber = 0;
        int failNumber = 0;
        int totalAppDurationNumber = 0;
        long totalAppDuration = 0;
        int totalTransportDurationNumber = 0;
        long totalTransportDuration = 0;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                continue;
            }

            switch (logEntity.getTarget()) {
                case Constant.LogTarget.Helper.SUCCESS: {
                    successNumber++;
                    break;
                }
                case Constant.LogTarget.Helper.FAIL: {
                    failNumber++;
                    break;
                }
                case Constant.LogTarget.Helper.APP_DURATION: {
                    totalAppDuration += logEntity.getSpentTime();
                    totalAppDurationNumber++;
                    break;
                }
                case Constant.LogTarget.Helper.TRANSPORT_DURATION: {
                    totalTransportDuration += logEntity.getSpentTime();
                    totalTransportDurationNumber++;
                    break;
                }
                default: {
                    break;
                }
            }
        }

        totalNumber = successNumber + failNumber;
        mStatisticsTv.setText(getContext().getString(R.string.helper_statistics,
                totalNumber,
                successNumber,
                failNumber,
                totalAppDurationNumber == 0 ? 0 : totalAppDuration / totalAppDurationNumber,
                totalTransportDurationNumber == 0 ? 0 : totalTransportDuration / totalTransportDurationNumber));
    }

    public void setOnInsertLogEntityListener(OnInsertLogEntityListener onInsertLogEntityListener) {
        mOnInsertLogEntityListener = onInsertLogEntityListener;
    }
}
