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
import android.widget.Switch;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.TestBroadCastReceiver;
import testmode.eebbk.com.testmodedemo.adapter.LogAdapter;
import testmode.eebbk.com.testmodedemo.common.Constant;
import testmode.eebbk.com.testmodedemo.model.DataRepository;
import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.setting.SettingManager;

/**
 * @author LiXiaoFeng
 * @date 2018/4/8
 * <p>
 * 全部日志页
 */
public class AllLogFragment extends Fragment {
    private RecyclerView mLogRv;
    private LogAdapter mLogAdapter;
    private LogBroadcastReceiver mLogBroadcastReceiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLogBroadcastReceiver = new LogBroadcastReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mLogBroadcastReceiver, getIntentFilter());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_all, container, false);
        mLogRv = view.findViewById(R.id.log_all_rv);
        mLogRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLogAdapter = new LogAdapter();
        mLogAdapter.insertData(DataRepository.getInstance().getData(null));
        mLogRv.setAdapter(mLogAdapter);
        return view;
    }

    private IntentFilter getIntentFilter() {
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
        intentFilter.addAction(Constant.LogTarget.AppWake.Voice.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.AppWake.Voice.FAIL);
        intentFilter.addAction(Constant.LogTarget.AppWake.Voice.MISTAKE);
        intentFilter.addAction(Constant.LogTarget.AppWake.Voice.DURATION);
        intentFilter.addAction(Constant.LogTarget.AppWake.Button.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.AppWake.Button.FAIL);
        intentFilter.addAction(Constant.LogTarget.AppWake.Button.MISTAKE);
        intentFilter.addAction(Constant.LogTarget.AppWake.Button.DURATION);
        intentFilter.addAction(Constant.LogTarget.Audio.DECIBEL);
        intentFilter.addAction(Constant.LogTarget.Audio.SAMPLING_RATE);
        intentFilter.addAction(Constant.LogTarget.Speech.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.Speech.FAIL);
        intentFilter.addAction(Constant.LogTarget.Speech.AUDIO_DURATION);
        intentFilter.addAction(Constant.LogTarget.Speech.CONVERTING_DURATION);
        intentFilter.addAction(Constant.LogTarget.Semantic.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.Semantic.FAIL);
        intentFilter.addAction(Constant.LogTarget.Semantic.DURATION);
        intentFilter.addAction(Constant.LogTarget.OrderDistribution.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.OrderDistribution.FAIL);
        intentFilter.addAction(Constant.LogTarget.OrderDistribution.APP_DURATION);
        intentFilter.addAction(Constant.LogTarget.OrderDistribution.TRANSPORT_DURATION);
        intentFilter.addAction(Constant.LogTarget.ServerSearch.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.ServerSearch.FAIL);
        intentFilter.addAction(Constant.LogTarget.ServerSearch.SERVER_DURATION);
        intentFilter.addAction(Constant.LogTarget.ServerSearch.TRANSPORT_DURATION);
        intentFilter.addAction(Constant.LogTarget.Content.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.Content.FAIL);
        intentFilter.addAction(Constant.LogTarget.Display.DURATION);
        intentFilter.addAction(Constant.LogTarget.Helper.SUCCESS);
        intentFilter.addAction(Constant.LogTarget.Helper.FAIL);
        intentFilter.addAction(Constant.LogTarget.Helper.APP_DURATION);
        intentFilter.addAction(Constant.LogTarget.Helper.TRANSPORT_DURATION);
        return intentFilter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mLogBroadcastReceiver);
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
            mLogAdapter.notifyItemInserted(position);
            if (SettingManager.getInstance(getContext()).isAutoScroll()) {
                mLogRv.scrollToPosition(position);
            }
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
        }
    }
}
