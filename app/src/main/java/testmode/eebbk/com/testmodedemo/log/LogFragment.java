package testmode.eebbk.com.testmodedemo.log;

import android.app.Fragment;
import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.filter.LogFilter;
import testmode.eebbk.com.testmodedemo.filter.LogFilterFactory;
import testmode.eebbk.com.testmodedemo.model.DataRepository;
import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.model.ModuleEntity;
import testmode.eebbk.com.testmodedemo.model.TargetEntity;
import testmode.eebbk.com.testmodedemo.statistician.LogStatistician;
import testmode.eebbk.com.testmodedemo.statistician.LogStatisticianFactory;
import testmode.eebbk.com.testmodedemo.tool.LogToolFactory;
import testmode.eebbk.com.testmodedemo.util.LogFormatUtil;

/**
 * @author LiXiaoFeng
 * @date 2018/4/17
 */
public class LogFragment extends Fragment {
    private static final String KEY_MODULE_ENTITY_ID = "module_entity_id";
    private ModuleEntity mModuleEntity;
    private List<TargetEntity> mTargetEntities;
    private LogAdapter mLogAdapter;
    private ToolAdapter mToolAdapter;
    private TextView mStatisticsTv;
    private RecyclerView mLogRv;
    private RecyclerView mToolRv;
    private LogFilter mLogFilter;
    private LogStatistician mLogStatistician;
    private DataRepository.DataChangeBroadcastReceiver mDataChangeBroadcastReceiver;

    public static LogFragment newInstance(ModuleEntity moduleEntity) {
        if (moduleEntity == null) {
            return null;
        }

        LogFragment logFragment = new LogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MODULE_ENTITY_ID, moduleEntity.getId());
        logFragment.setArguments(bundle);
        return logFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initLogBroadcastReceiver();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mStatisticsTv = (TextView) view.findViewById(R.id.statistics_tv);
        mLogRv = (RecyclerView) view.findViewById(R.id.log_rv);
        mToolRv = (RecyclerView) view.findViewById(R.id.tool_rv);

        mLogAdapter = new LogAdapter();
        mLogAdapter.insertData(DataRepository.getInstance().getData(mLogFilter));
        mLogRv.setAdapter(mLogAdapter);
        mLogRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<TargetEntity> toolTargetEntities = new ArrayList<>();
        for (TargetEntity targetEntity : mTargetEntities) {
            if (targetEntity.isTool()) {
                toolTargetEntities.add(targetEntity);
            }
        }
        mToolAdapter = new ToolAdapter(toolTargetEntities);
        mToolRv.setAdapter(mToolAdapter);
        mToolRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateStatistics();
    }

    private void initData() {
        mModuleEntity = DataRepository.getInstance().getModuleEntity(getArguments().getString(KEY_MODULE_ENTITY_ID));
        mLogFilter = LogFilterFactory.produceLogFilter(mModuleEntity);
        mLogStatistician = LogStatisticianFactory.produceLogStatistician(mModuleEntity);
        mTargetEntities = DataRepository.getInstance().getTargetEntities(mModuleEntity);
    }

    private void initLogBroadcastReceiver() {
        mDataChangeBroadcastReceiver = new DataRepository.DataChangeBroadcastReceiver() {
            @Override
            protected void onInsertLogEntity(Context context, LogEntity logEntity) {
                if (logEntity == null) {
                    return;
                }
                int position = mLogAdapter.insertData(logEntity);
                if (position == -1) {
                    return;
                }

                mLogAdapter.notifyItemInserted(position);
                mLogRv.scrollToPosition(position);
                updateStatistics();
            }

            @Override
            protected void onRemoveLogEntity(Context context, LogEntity logEntity) {
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

            @Override
            protected void onRefreshData(Context context) {
                mLogAdapter.refreshData();
                mLogAdapter.notifyDataSetChanged();
                updateStatistics();
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        for (TargetEntity targetEntity : mTargetEntities) {
            intentFilter.addAction(targetEntity.getFullName());
        }
        intentFilter.addAction(DataRepository.getInstance().ACTION_REFRESH_LOG_ENTITY);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mDataChangeBroadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mDataChangeBroadcastReceiver);
    }

    private void updateStatistics() {
        String statistics = mLogStatistician.statistics(mModuleEntity, DataRepository.getInstance().getData(mLogFilter));
        mStatisticsTv.setText(statistics);
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {
        private LinkedList<LogEntity> mLogEntities;

        public LogAdapter() {
            mLogEntities = new LinkedList<>();
        }

        @Override
        public LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final LogViewHolder logViewHolder = new LogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false));
            logViewHolder.mDeleteBtn.setOnClickListener(v -> {
                int position = logViewHolder.getAdapterPosition();
                LogEntity logEntity = mLogEntities.remove(position);
                DataRepository.getInstance().removeData(logEntity);
                notifyItemRemoved(position);
            });
            return logViewHolder;
        }

        @Override
        public void onBindViewHolder(LogViewHolder holder, int position) {
            if (mLogEntities == null) {
                return;
            }

            final LogEntity logEntity = mLogEntities.get(position);

            if (logEntity == null) {
                return;
            }

            holder.mLogTv.setText(LogFormatUtil.format(logEntity));
        }

        @Override
        public int getItemCount() {
            return mLogEntities == null ? 0 : mLogEntities.size();
        }

        public int insertData(LogEntity logEntity) {
            if (mLogEntities == null || logEntity == null) {
                return -1;
            }

            mLogEntities.addFirst(logEntity);
            return 0;
        }

        public int[] insertData(List<LogEntity> logEntities) {
            if (mLogEntities == null || logEntities == null) {
                return null;
            }

            int count = 0;
            for (int i = logEntities.size() - 1; i >= 0; i--) {
                LogEntity logEntity = logEntities.get(i);
                if (logEntity != null) {
                    mLogEntities.addFirst(logEntity);
                    count++;
                }
            }

            return new int[]{0, count};
        }

        public int removeData(LogEntity logEntity) {
            if (mLogEntities == null || logEntity == null) {
                return -1;
            }

            for (int i = 0; i < mLogEntities.size(); i++) {
                if (logEntity.equals(mLogEntities.get(i))) {
                    mLogEntities.remove(i);
                    return i;
                }
            }

            return -1;
        }

        public void refreshData() {
            mLogEntities.clear();
        }
    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView mLogTv;
        ImageButton mDeleteBtn;

        LogViewHolder(View itemView) {
            super(itemView);
            mLogTv = itemView.findViewById(R.id.log_tv);
            mDeleteBtn = itemView.findViewById(R.id.log_delete_btn);
        }
    }

    private static class ToolAdapter extends RecyclerView.Adapter<ToolViewHolder> {
        private List<TargetEntity> mTargetEntities;

        public ToolAdapter(List<TargetEntity> mTargetEntities) {
            this.mTargetEntities = mTargetEntities;
        }

        @Override
        public ToolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ToolViewHolder holder = new ToolViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_tool, parent, false));
            holder.mToolBtn.setOnClickListener(v -> {
                TargetEntity targetEntity = mTargetEntities.get(holder.getAdapterPosition());
                LogToolFactory.produceTool(targetEntity).function(targetEntity);
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ToolViewHolder holder, int position) {
            if (mTargetEntities == null) {
                return;
            }

            final TargetEntity targetEntity = mTargetEntities.get(position);

            if (targetEntity == null) {
                return;
            }

            holder.mToolBtn.setText(targetEntity.getFullName());
        }

        @Override
        public int getItemCount() {
            return mTargetEntities == null ? 0 : mTargetEntities.size();
        }
    }

    private static class ToolViewHolder extends RecyclerView.ViewHolder {
        Button mToolBtn;

        public ToolViewHolder(View itemView) {
            super(itemView);

            mToolBtn = itemView.findViewById(R.id.log_tool_btn);
        }
    }
}
