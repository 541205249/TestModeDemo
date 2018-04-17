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

import java.util.List;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.adapter.LogAdapter;
import testmode.eebbk.com.testmodedemo.common.Constant;
import testmode.eebbk.com.testmodedemo.common.DateUtils;
import testmode.eebbk.com.testmodedemo.common.OnInsertLogEntityListener;
import testmode.eebbk.com.testmodedemo.model.DataRepository;
import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.setting.SettingManager;

/**
 * @author LiXiaoFeng
 * @date 2018/4/9
 * <p>
 * 系统唤醒日志页
 */
public class SystemWakeLogFragment extends Fragment {
    private RecyclerView mLogRv;
    private TextView mStatisticsTv;
    private Button mBlackVoiceMistakeBtn;
    private Button mBlackGestureMistakeBtn;
    private Button mLockVoiceMistakeBtn;
    private Button mBrightVoiceMistakeBtn;
    private Button mBrightButtonMistakeBtn;
    private LogAdapter mLogAdapter;
    private LogBroadcastReceiver mLogBroadcastReceiver;
    private OnInsertLogEntityListener mOnInsertLogEntityListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLogBroadcastReceiver = new LogBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.LogTarget.SystemWake.Black.Voice.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Black.Voice.FAIL);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Black.Voice.MISTAKE);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Black.Voice.SYSTEM_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Black.Voice.APP_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Black.Voice.TOTAL_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Black.Gesture.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Black.Gesture.FAIL);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Black.Gesture.MISTAKE);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Black.Gesture.SYSTEM_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Black.Gesture.APP_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Black.Gesture.TOTAL_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Lock.Voice.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Lock.Voice.FAIL);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Lock.Voice.MISTAKE);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Lock.Voice.SYSTEM_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Lock.Voice.APP_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Lock.Voice.TOTAL_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Bright.Voice.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Bright.Voice.FAIL);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Bright.Voice.MISTAKE);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Bright.Voice.SYSTEM_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Bright.Voice.APP_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Bright.Voice.TOTAL_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Bright.Button.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Bright.Button.FAIL);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Bright.Button.MISTAKE);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Bright.Button.SYSTEM_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Bright.Button.APP_DURATION);
        intentFilter.addAction(Constant.LogTarget.SystemWake.Bright.Button.TOTAL_DURATION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mLogBroadcastReceiver, intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_system_wake, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mLogBroadcastReceiver);
    }

    private void initView(View view) {
        mLogRv = view.findViewById(R.id.log_system_wake_rv);
        mStatisticsTv = view.findViewById(R.id.log_system_wake_statistics_tv);
        mBlackVoiceMistakeBtn = view.findViewById(R.id.black_voice_wake_mistake_btn);
        mBlackGestureMistakeBtn = view.findViewById(R.id.black_gesture_wake_mistake_btn);
        mLockVoiceMistakeBtn = view.findViewById(R.id.lock_voice_wake_mistake_btn);
        mBrightVoiceMistakeBtn = view.findViewById(R.id.bright_voice_wake_mistake_btn);
        mBrightButtonMistakeBtn = view.findViewById(R.id.bright_button_wake_mistake_btn);

        mLogAdapter = new LogAdapter();
        mLogAdapter.setOnLogChangeListener(logEntity -> updateStatistics());
        mLogRv.setAdapter(mLogAdapter);
        mLogRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLogAdapter.insertData(DataRepository.getInstance().getData(Constant.LogTarget.SystemWake.FILTER));

        mBlackVoiceMistakeBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.SystemWake.Black.Voice.MISTAKE);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            DataRepository.getInstance().insertData(logEntity);
        });
        mBlackGestureMistakeBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.SystemWake.Black.Gesture.MISTAKE);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            DataRepository.getInstance().insertData(logEntity);
        });
        mLockVoiceMistakeBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.SystemWake.Lock.Voice.MISTAKE);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            DataRepository.getInstance().insertData(logEntity);
        });
        mBrightVoiceMistakeBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.SystemWake.Bright.Voice.MISTAKE);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            DataRepository.getInstance().insertData(logEntity);
        });
        mBrightButtonMistakeBtn.setOnClickListener(v -> {
            LogEntity logEntity = new LogEntity();
            logEntity.setTarget(Constant.LogTarget.SystemWake.Bright.Button.MISTAKE);
            logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
            DataRepository.getInstance().insertData(logEntity);
        });

        updateStatistics();
    }

    private void updateStatistics() {
        List<LogEntity> logEntities = DataRepository.getInstance().getData(Constant.LogTarget.SystemWake.FILTER);

        if (logEntities == null) {
            return;
        }

        int totalNumber = 0;
        int successNumber = 0;
        int failNumber = 0;
        int mistakeNumber = 0;
        int systemTotalDurationNumber = 0;
        long systemTotalDuration = 0;
        int appTotalDurationNumber = 0;
        long appTotalDuration = 0;
        int systemWakeTotalDurationNumber = 0;
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
                    systemTotalDurationNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Voice.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    appTotalDurationNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Voice.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    systemWakeTotalDurationNumber++;
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
                    systemTotalDurationNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Gesture.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    appTotalDurationNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Black.Gesture.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    systemWakeTotalDurationNumber++;
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
                    systemTotalDurationNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Lock.Voice.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    appTotalDurationNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Lock.Voice.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    systemWakeTotalDurationNumber++;
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
                    systemTotalDurationNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Voice.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    appTotalDurationNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Voice.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    systemWakeTotalDurationNumber++;
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
                    systemTotalDurationNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Button.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    appTotalDurationNumber++;
                    break;
                }
                case Constant.LogTarget.SystemWake.Bright.Button.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    systemWakeTotalDurationNumber++;
                    break;
                }
                default: {
                    break;
                }
            }
        }

        totalNumber = successNumber + failNumber + mistakeNumber;
        mStatisticsTv.setText(getString(R.string.system_wake_statistics,
                totalNumber,
                successNumber,
                failNumber,
                mistakeNumber,
                totalNumber == 0 ? 0 : successNumber * 100.0f / totalNumber,
                totalNumber == 0 ? 0 : mistakeNumber * 100.0f / totalNumber,
                systemTotalDurationNumber == 0 ? 0 : systemTotalDuration / systemTotalDurationNumber,
                appTotalDurationNumber == 0 ? 0 : appTotalDuration / appTotalDurationNumber,
                systemWakeTotalDurationNumber == 0 ? 0 : systemWakeTotalDuration / systemWakeTotalDurationNumber));
    }

    public void setOnInsertLogEntityListener(OnInsertLogEntityListener onInsertLogEntityListener) {
        mOnInsertLogEntityListener = onInsertLogEntityListener;
    }

    public class LogBroadcastReceiver extends BroadcastReceiver {
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
