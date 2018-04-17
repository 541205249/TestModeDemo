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
import android.widget.Toast;

import java.util.List;

import testmode.eebbk.com.testmodedemo.R;
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
 * 音频日志页
 */
public class AudioLogFragment extends Fragment {
    private RecyclerView mLogRv;
    private LogAdapter mLogAdapter;
    private TextView mStatisticsTv;
    private Button mDecibelBtn;
    private LogBroadcastReceiver mLogBroadcastReceiver;
    private OnInsertLogEntityListener mOnInsertLogEntityListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLogBroadcastReceiver = new LogBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.LogTarget.Audio.DECIBEL);
        intentFilter.addAction(Constant.LogTarget.Audio.SAMPLING_RATE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mLogBroadcastReceiver, intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_audio, container, false);
        initView(view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initView(View view) {
        mLogRv = view.findViewById(R.id.log_audio_rv);
        mStatisticsTv = view.findViewById(R.id.log_audio_statistics_tv);
        mDecibelBtn = view.findViewById(R.id.log_audio_decibel_btn);

        mLogAdapter = new LogAdapter();
        mLogAdapter.insertData(DataRepository.getInstance().getData(Constant.LogTarget.Audio.FILTER));
        mLogAdapter.setOnLogChangeListener(logEntity -> updateStatistics());
        mLogRv.setAdapter(mLogAdapter);
        mLogRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDecibelBtn.setOnClickListener(v -> {
            // TODO: 2018/4/11 暂不支持
            Toast.makeText(getActivity(), "暂不支持，功能待开发", Toast.LENGTH_SHORT).show();
        });

        updateStatistics();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mLogBroadcastReceiver);
    }

    private void updateStatistics() {
        List<LogEntity> logEntities = DataRepository.getInstance().getData(Constant.LogTarget.Audio.FILTER);

        if (logEntities == null) {
            return;
        }

        int decibelNumber = 0;
        float minimalDecibel = 0f;
        float maximumDecibel = 0f;
        float totalDecibel = 0f;
        int samplingRateNumber = 0;
        float totalSamplingRate = 0f;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                return;
            }

            switch (logEntity.getTarget()) {
                case Constant.LogTarget.Audio.DECIBEL: {
                    decibelNumber++;
                    float decibel = logEntity.getValue();
                    if (decibel < minimalDecibel) {
                        minimalDecibel = decibel;
                    }
                    if (decibel > maximumDecibel) {
                        maximumDecibel = decibel;
                    }
                    totalDecibel += decibel;
                    break;
                }
                case Constant.LogTarget.Audio.SAMPLING_RATE: {
                    samplingRateNumber++;
                    float samplingRate = logEntity.getValue();
                    totalSamplingRate += samplingRate;
                    break;
                }
            }
        }

        mStatisticsTv.setText(getString(R.string.audio_statistics,
                minimalDecibel,
                maximumDecibel,
                decibelNumber == 0 ? 0 : totalDecibel / decibelNumber,
                samplingRateNumber == 0 ? 0 : totalSamplingRate / samplingRateNumber));
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
