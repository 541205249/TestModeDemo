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
 * 语义理解工具栏
 */
public class SemanticToolView extends LinearLayout {
    private TextView mStatisticsTv;
    private Button mSuccessBtn;
    private Button mFailBtn;
    private OnInsertLogEntityListener mOnInsertLogEntityListener;

    public SemanticToolView(Context context) {
        this(context, null);
    }

    public SemanticToolView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_tool_semantic, this);
        mStatisticsTv = findViewById(R.id.tool_semantic_statistics_tv);
        mSuccessBtn = findViewById(R.id.tool_semantic_success_btn);
        mFailBtn = findViewById(R.id.tool_semantic_fail_btn);

        mSuccessBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.Speech.SUCCESS);
            logEntity.setSuccessCount(1);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            insertLogEntity(logEntity);
            updateStatistics();
        });
        mFailBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.Speech.FAIL);
            logEntity.setFailCount(1);
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
        List<LogEntity> logEntities = DataRepository.getInstance().getData(Constant.LogTarget.Semantic.FILTER);

        if (logEntities == null) {
            return;
        }

        int totalNumber = 0;
        int successNumber = 0;
        int failNumber = 0;
        long totalDuration = 0;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                continue;
            }

            switch (logEntity.getTarget()) {
                case Constant.LogTarget.Semantic.SUCCESS: {
                    successNumber++;
                    break;
                }
                case Constant.LogTarget.Semantic.FAIL: {
                    failNumber++;
                    break;
                }
                case Constant.LogTarget.Semantic.DURATION: {
                    totalDuration += logEntity.getSpentTime();
                    break;
                }
                default: {
                    break;
                }
            }
        }

        totalNumber = successNumber + failNumber;
        mStatisticsTv.setText(getContext().getString(R.string.semantic_statistics,
                totalNumber,
                successNumber,
                failNumber,
                totalNumber == 0 ? 0 : successNumber * 100.0f / totalNumber,
                totalNumber == 0 ? 0 : totalDuration / totalNumber));
    }

    public void setOnInsertLogEntityListener(OnInsertLogEntityListener onInsertLogEntityListener) {
        mOnInsertLogEntityListener = onInsertLogEntityListener;
    }
}
