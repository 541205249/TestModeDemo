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
 * 内容工具栏
 */
public class ContentToolView extends LinearLayout {
    private TextView mStatisticsTv;
    private Button mSuccessBtn;
    private Button mFailBtn;
    private OnInsertLogEntityListener mOnInsertLogEntityListener;

    public ContentToolView(Context context) {
        this(context, null);
    }

    public ContentToolView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_tool_content, this);
        mStatisticsTv = findViewById(R.id.tool_content_statistics_tv);
        mSuccessBtn = findViewById(R.id.tool_content_success_btn);
        mFailBtn = findViewById(R.id.tool_content_fail_btn);

        mSuccessBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.Content.SUCCESS);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            insertLogEntity(logEntity);
            updateStatistics();
        });
        mFailBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.Content.FAIL);
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
        List<LogEntity> logEntities = DataRepository.getInstance().getData(Constant.LogTarget.Content.FILTER);

        if (logEntities == null) {
            return;
        }

        int totalNumber = 0;
        int successNumber = 0;
        int failNumber = 0;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                continue;
            }

            switch (logEntity.getTarget()) {
                case Constant.LogTarget.Content.SUCCESS: {
                    successNumber++;
                    break;
                }
                case Constant.LogTarget.Content.FAIL: {
                    failNumber++;
                    break;
                }
                default: {
                    break;
                }
            }
        }

        totalNumber = successNumber + failNumber;
        mStatisticsTv.setText(getContext().getString(R.string.content_statistics,
                totalNumber,
                successNumber,
                failNumber,
                totalNumber == 0 ? 0 : successNumber * 100.0f / totalNumber));
    }

    public void setOnInsertLogEntityListener(OnInsertLogEntityListener onInsertLogEntityListener) {
        mOnInsertLogEntityListener = onInsertLogEntityListener;
    }
}
