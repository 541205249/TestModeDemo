package testmode.eebbk.com.testmodedemo.log;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.common.DateUtils;

import java.util.List;

import testmode.eebbk.com.testmodedemo.adapter.LogAdapter;
import testmode.eebbk.com.testmodedemo.common.Constant;
import testmode.eebbk.com.testmodedemo.common.OnInsertLogEntityListener;
import testmode.eebbk.com.testmodedemo.model.DataRepository;
import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.setting.SettingManager;

/**
 * @author LiXiaoFeng
 * @date 2018/4/10
 * <p>
 * 语音识别日志页
 */
public class ContentLogFragment extends Fragment {
    private RecyclerView mLogRv;
    private TextView mStatisticsTv;
    private Button mSuccessBtn;
    private Button mFailBtn;
    private LogAdapter mLogAdapter;
    private LogBroadcastReceiver mLogBroadcastReceiver;
    private OnInsertLogEntityListener mOnInsertLogEntityListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLogBroadcastReceiver = new LogBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.LogTarget.Content.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.Content.FAIL);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mLogBroadcastReceiver, intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_content, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLogRv = (RecyclerView) view.findViewById(R.id.log_content_rv);
        mStatisticsTv = (TextView) view.findViewById(R.id.log_content_statistics_tv);
        mSuccessBtn = (Button) view.findViewById(R.id.log_content_success_btn);
        mFailBtn = (Button) view.findViewById(R.id.log_content_fail_btn);

        mLogAdapter = new LogAdapter();
        mLogAdapter.insertData(DataRepository.getInstance().getData(Constant.LogTarget.Content.FILTER));
        mLogAdapter.setOnLogChangeListener(logEntity -> updateStatistics());
        mLogRv.setAdapter(mLogAdapter);
        mLogRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSuccessBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.Content.SUCCESS);
            logEntity.setSuccessCount(1);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            DataRepository.getInstance().insertData(logEntity);
        });
        mFailBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.Content.FAIL);
            logEntity.setFailCount(1);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            DataRepository.getInstance().insertData(logEntity);
        });

        updateStatistics();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mLogBroadcastReceiver);
    }

    private void updateStatistics() {
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
        mStatisticsTv.setText(getString(R.string.content_statistics,
                totalNumber,
                successNumber,
                failNumber,
                totalNumber == 0 ? 0 : successNumber * 100.0f / totalNumber));
    }

    public void setOnInsertLogEntityListener(OnInsertLogEntityListener onInsertLogEntityListener) {
        mOnInsertLogEntityListener = onInsertLogEntityListener;
    }

    private class LogBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogEntity logEntity = intent.getParcelableExtra(DataRepository.KEY_LOG_ENTITY);

            if (logEntity == null) {
                return;
            }
            switch (intent.getIntExtra(DataRepository.KEY_CHANGE_TYPE, -1)) {
                case -1: {
                    break;
                }
                case DataRepository.CHANGE_TYPE_INSERT: {
                    onInsertLogEntity(logEntity);
                    break;
                }
                case DataRepository.CHANGE_TYPE_REMOVE: {
                    onRemoveLogEntity(logEntity);
                    break;
                }
            }
        }

        private void onInsertLogEntity(LogEntity logEntity) {
            if (logEntity == null) {
                return;
            }

            int position = mLogAdapter.insertData(logEntity);
            if (position == -1) {
                return;
            }

            if (mOnInsertLogEntityListener != null) {
                mOnInsertLogEntityListener.onInsertLogEntity(logEntity);
            }

            mLogAdapter.notifyItemInserted(position);
            if (SettingManager.getInstance(getContext()).isAutoScroll()) {
                mLogRv.scrollToPosition(position);
            }
            updateStatistics();
        }

        private void onRemoveLogEntity(LogEntity logEntity) {
            if (logEntity == null) {
                return;
            }

            int position = mLogAdapter.removeData(logEntity);
            if (position == -1) {
                return;
            }
            mLogAdapter.notifyItemRemoved(position);
            updateStatistics();
        }
    }
}
