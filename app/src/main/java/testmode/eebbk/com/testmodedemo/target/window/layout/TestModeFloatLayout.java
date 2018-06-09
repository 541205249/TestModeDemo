package testmode.eebbk.com.testmodedemo.target.window.layout;

import android.content.Context;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.target.model.DataRepository;
import testmode.eebbk.com.testmodedemo.target.model.LogEntity;
import testmode.eebbk.com.testmodedemo.target.model.ModuleEntity;
import testmode.eebbk.com.testmodedemo.target.model.TargetEntity;
import testmode.eebbk.com.testmodedemo.target.window.FloatWindowController;
import testmode.eebbk.com.testmodedemo.target.window.ToolView;
import testmode.eebbk.com.testmodedemo.util.LogFormatUtil;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public class TestModeFloatLayout extends FloatLayout {
    private static final String TAG = TestModeFloatLayout.class.getSimpleName();
    private final WindowManager mWindowManager;
    private float mTouchStartX;
    private float mTouchStartY;
    private ImageButton mMoveIbtn;
    private ImageButton mShowIbtn;
    private ImageButton mRefreshIbtn;
    private ImageButton mCloseIbtn;
    private TextView mLogTv;
    private LinearLayout mToolLinearLayout;
    private ListView mModuleLv;
    private LinearLayout mContainerLinearLayout;
    private ArrayAdapter<ModuleEntity> mModuleAdapter;
    private HashMap<String, ToolView> mToolViewMap = new HashMap<>();
    private ModuleEntity mCurrentModuleEntity;
    private boolean isVisible;
    private DataRepository.DataChangeBroadcastReceiver mDataChangeBroadcastReceiver;

    public TestModeFloatLayout(Context context) {
        this(context, null);
    }

    public TestModeFloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDataChangeBroadcastReceiver = new DataRepository.DataChangeBroadcastReceiver() {
            @Override
            protected void onInsertLogEntity(Context context, LogEntity logEntity) {
                if (logEntity == null) {
                    return;
                }

                showNewestLog(logEntity);
                List<ModuleEntity> moduleEntities = DataRepository.getInstance().getRootModuleEntities();
                for (ModuleEntity moduleEntity : moduleEntities) {
                    if (logEntity.getTarget().startsWith(moduleEntity.getName())) {
                        openToolView(moduleEntity);
                        mCurrentModuleEntity = moduleEntity;
                        mModuleAdapter.notifyDataSetChanged();
                        return;
                    }
                }
            }

            @Override
            protected void onRemoveLogEntity(Context context, LogEntity logEntity) {
                if (logEntity == null) {
                    return;
                }

                showNewestLog(logEntity);
                List<LogEntity> logEntities = DataRepository.getInstance().getData(null);
                if (logEntities.isEmpty()) {
                    showNewestLog(null);
                } else {
                    LogEntity preLogEntity = logEntities.get(0);
                    showNewestLog(preLogEntity);
                    List<ModuleEntity> moduleEntities = DataRepository.getInstance().getRootModuleEntities();
                    for (ModuleEntity moduleEntity : moduleEntities) {
                        if (preLogEntity.getTarget().startsWith(moduleEntity.getName())) {
                            openToolView(moduleEntity);
                            mCurrentModuleEntity = moduleEntity;
                            mModuleAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                }
            }

            @Override
            protected void onRefreshData(Context context) {
                showNewestLog(null);
            }
        };

        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.test_mode_float_window_layout, this);
        mMoveIbtn = (ImageButton) view.findViewById(R.id.float_move_ibtn);
        mShowIbtn = (ImageButton) view.findViewById(R.id.float_show_ibtn);
        mRefreshIbtn = (ImageButton) view.findViewById(R.id.float_refresh_ibtn);
        mCloseIbtn = (ImageButton) view.findViewById(R.id.float_close_ibtn);
        mLogTv = (TextView) view.findViewById(R.id.float_log_tv);
        mToolLinearLayout = (LinearLayout) view.findViewById(R.id.float_tool_ll);
        mModuleLv = (ListView) view.findViewById(R.id.float_module_lv);
        mContainerLinearLayout = (LinearLayout) view.findViewById(R.id.float_container_ll);

        mModuleAdapter = new ArrayAdapter<ModuleEntity>(context, android.R.layout.simple_list_item_1, DataRepository.getInstance().getRootModuleEntities()) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(getResources().getColor(R.color.white));
                ModuleEntity moduleEntity = mModuleAdapter.getItem(position);
                if (mCurrentModuleEntity == moduleEntity) {
                    textView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                } else {
                    textView.setBackgroundColor(getResources().getColor(R.color.transparent_black));
                }
                textView.setText(moduleEntity.getName());
                return textView;
            }
        };
        mModuleLv.setOnItemClickListener((parent, v, position, id) -> {
            ModuleEntity moduleEntity = mModuleAdapter.getItem(position);
            mCurrentModuleEntity = moduleEntity;
            mModuleAdapter.notifyDataSetChanged();
            openToolView(moduleEntity);
        });
        mModuleLv.setAdapter(mModuleAdapter);

        isVisible = true;
        mToolLinearLayout.setVisibility(VISIBLE);
        mMoveIbtn.setOnTouchListener((v, event) -> {
            int rx = (int) event.getRawX();
            int ry = (int) event.getRawY();
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    mTouchStartX = event.getX();
                    mTouchStartY = event.getY();
                }
                case MotionEvent.ACTION_MOVE: {
                    if (Math.abs(mTouchStartX - event.getX()) > 3
                            && Math.abs(mTouchStartY - event.getY()) > 3) {
                        // 更新浮动窗口位置参数
                        mWmParams.x = (int) (rx - mTouchStartX);
                        mWmParams.y = (int) (ry - mTouchStartY);
                        mWindowManager.updateViewLayout(this, mWmParams);
                    }
                }
                default: {
                    break;
                }
            }
            return true;
        });
        isVisible = mToolLinearLayout.getVisibility() == VISIBLE;
        mShowIbtn.setImageResource(isVisible ? R.drawable.icon_visible : R.drawable.icon_gone);
        mShowIbtn.setOnClickListener(v -> {
            isVisible = !isVisible;
            mToolLinearLayout.setVisibility(isVisible ? VISIBLE : GONE);
            mShowIbtn.setImageResource(isVisible ? R.drawable.icon_visible : R.drawable.icon_gone);
        });
        mRefreshIbtn.setOnClickListener(v -> {
            DataRepository.getInstance().refreshData();
        });
        mCloseIbtn.setOnClickListener(v -> {
            FloatWindowController.getInstance().close();
        });

        List<LogEntity> data = DataRepository.getInstance().getData(null);
        if (data == null || data.isEmpty()) {
            showNewestLog(null);
        } else {
            showNewestLog(data.get(0));
        }
        mCurrentModuleEntity = mModuleAdapter.getItem(0);
        openToolView(mCurrentModuleEntity);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        registerDataBroadcastReceiver();
        Log.i(TAG, this.toString() + " onAttachedToWindow");
    }

    private void registerDataBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        List<ModuleEntity> moduleEntities = DataRepository.getInstance().getRootModuleEntities();
        for (ModuleEntity moduleEntity : moduleEntities) {
            List<TargetEntity> targetEntities = DataRepository.getInstance().getTargetEntities(moduleEntity);
            for (TargetEntity targetEntity : targetEntities) {
                intentFilter.addAction(targetEntity.getFullName());
            }
        }
        intentFilter.addAction(DataRepository.ACTION_REFRESH_LOG_ENTITY);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mDataChangeBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mDataChangeBroadcastReceiver);
        Log.i(TAG, this.toString() + " onDetachedFormWindow");
    }

    private void openToolView(ModuleEntity moduleEntity) {
        if (moduleEntity == null) {
            return;
        }

        if (mContainerLinearLayout.getChildCount() != 0 && mContainerLinearLayout.getChildAt(0).getTag().equals(moduleEntity.getName())) {
            return;
        }

        ToolView toolView = new ToolView(getContext());
        toolView.setModuleEntity(moduleEntity);
        toolView.setTag(moduleEntity.getName());

        mContainerLinearLayout.removeAllViews();
        mContainerLinearLayout.addView(toolView);
    }

    private void showNewestLog(LogEntity logEntity) {
        if (logEntity == null) {
            mLogTv.setText("");
            return;
        }

        mLogTv.setText(LogFormatUtil.format(logEntity));
    }
}
