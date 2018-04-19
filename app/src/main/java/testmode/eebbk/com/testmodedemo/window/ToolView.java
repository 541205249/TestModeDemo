package testmode.eebbk.com.testmodedemo.window;

import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.common.Constant;
import testmode.eebbk.com.testmodedemo.common.OnInsertLogEntityListener;
import testmode.eebbk.com.testmodedemo.filter.LogFilter;
import testmode.eebbk.com.testmodedemo.filter.LogFilterFactory;
import testmode.eebbk.com.testmodedemo.statistician.LogStatistician;
import testmode.eebbk.com.testmodedemo.statistician.LogStatisticianFactory;
import testmode.eebbk.com.testmodedemo.util.DateUtils;
import testmode.eebbk.com.testmodedemo.model.DataRepository;
import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.model.ModuleEntity;
import testmode.eebbk.com.testmodedemo.model.TargetEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public class ToolView extends LinearLayout {
    private static final String TAG = ToolView.class.getSimpleName();
    private TextView mStatisticianTv;
    private RecyclerView mToolRv;
    private ToolAdapter mToolAdapter;
    private LogStatistician mLogStatistician;
    private ModuleEntity mModuleEntity;
    private List<TargetEntity> mTargetEntities;
    private LogFilter mLogFilter;
    private DataRepository.DataChangeBroadcastReceiver mDataChangeBroadcastReceiver;

    public ToolView(Context context) {
        this(context, null);
    }

    public ToolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDataChangeBroadcastReceiver = new DataRepository.DataChangeBroadcastReceiver() {
            @Override
            protected void onInsertLogEntity(Context context, LogEntity logEntity) {
                if (logEntity == null) {
                    return;
                }
                updateStatistics();
            }

            @Override
            protected void onRemoveLogEntity(Context context, LogEntity logEntity) {
                if (logEntity == null) {
                    return;
                }

                updateStatistics();
            }

            @Override
            protected void onRefreshData(Context context) {
                updateStatistics();
            }
        };
    }

    public void setModuleEntity(ModuleEntity moduleEntity) {
        mModuleEntity = moduleEntity;
        initData();
        initView(getContext());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        registerLogBroadcastReceiver();
        Log.i(TAG, this.toString() + " onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mDataChangeBroadcastReceiver);
        Log.i(TAG, this.toString() + " onAttachedToWindow");
    }

    private void initData() {
        mTargetEntities = DataRepository.getInstance().getTargetEntities(mModuleEntity);
        mLogStatistician = LogStatisticianFactory.produceLogStatistician(mModuleEntity);
        mLogFilter = LogFilterFactory.produceLogFilter(mModuleEntity);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_tool, this);
        mStatisticianTv = (TextView) findViewById(R.id.statistics_tv);
        mToolRv = (RecyclerView) findViewById(R.id.tool_rv);

        List<TargetEntity> targetEntities = new ArrayList<>();
        for (TargetEntity targetEntity : mTargetEntities) {
            if (targetEntity.isAddition()) {
                targetEntities.add(targetEntity);
            }
        }
        mToolAdapter = new ToolAdapter(targetEntities);
        mToolRv.setAdapter(mToolAdapter);
        mToolRv.setLayoutManager(new LinearLayoutManager(context));

        updateStatistics();
    }

    private void registerLogBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        for (TargetEntity targetEntity : mTargetEntities) {
            intentFilter.addAction(targetEntity.getFullName());
        }
        intentFilter.addAction(DataRepository.getInstance().ACTION_REFRESH_LOG_ENTITY);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mDataChangeBroadcastReceiver, intentFilter);
    }

    private void updateStatistics() {
        String statistics = mLogStatistician.statistics(mModuleEntity, DataRepository.getInstance().getData(mLogFilter));
        mStatisticianTv.setText(statistics);
    }

    private static class ToolAdapter extends RecyclerView.Adapter<ToolViewHolder> {
        private List<TargetEntity> mTargetEntities;
        private OnInsertLogEntityListener mOnInsertLogEntityListener;

        public ToolAdapter(List<TargetEntity> targetEntities) {
            mTargetEntities = targetEntities;
        }

        @Override
        public ToolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ToolViewHolder toolViewHolder = new ToolViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tool_tool, parent, false));
            toolViewHolder.mToolBtn.setOnClickListener(v -> {
                LogEntity logEntity = new LogEntity();
                logEntity.setTarget(mTargetEntities.get(toolViewHolder.getAdapterPosition()).getFullName());
                logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
                DataRepository.getInstance().insertData(logEntity);
                if (mOnInsertLogEntityListener != null) {
                    mOnInsertLogEntityListener.onInsertLogEntity(logEntity);
                }
            });
            return toolViewHolder;
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

        public void setOnInsertLogEntityListener(OnInsertLogEntityListener onInsertLogEntityListener) {
            mOnInsertLogEntityListener = onInsertLogEntityListener;
        }
    }

    private static class ToolViewHolder extends RecyclerView.ViewHolder {
        Button mToolBtn;

        public ToolViewHolder(View itemView) {
            super(itemView);

            mToolBtn = (Button) itemView;
        }
    }
}
