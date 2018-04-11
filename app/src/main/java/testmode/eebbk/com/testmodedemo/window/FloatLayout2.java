package testmode.eebbk.com.testmodedemo.window;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.common.Constant;
import testmode.eebbk.com.testmodedemo.model.LogEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/10
 */
public class FloatLayout2 extends FrameLayout {
    private final WindowManager mWindowManager;
    private float mTouchStartX;
    private float mTouchStartY;
    private WindowManager.LayoutParams mWmParams;
    private ImageButton mMoveIbtn;
    private ImageButton mShowIbtn;
    private ImageButton mCloseIbtn;
    private TextView mLogTv;
    private LinearLayout mToolLinearLayout;
    private ListView mModuleLv;
    private ScrollView mToolContainerSv;
    private ArrayAdapter<String> mModuleAdapter;
    private SystemWakeToolView mSystemWakeToolView;
    private AppWakeToolView mAppWakeToolView;
    private AudioToolView mAudioToolView;
    private SpeechToolView mSpeechToolView;
    private SemanticToolView mSemanticToolView;
    private OrderDistributionToolView mOrderDistributionToolView;
    private ServerSearchToolView mServerSearchToolView;
    private ContentToolView mContentToolView;
    private DisplayToolView mDisplayToolView;
    private HelperToolView mHelperToolView;
    private int mCurrentSelectPosition;
    private boolean isVisible;

    public FloatLayout2(Context context) {
        this(context, null);
    }

    public FloatLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.float_window_layout_2, this);
        mMoveIbtn = (ImageButton) view.findViewById(R.id.float_move_ibtn);
        mShowIbtn = (ImageButton) view.findViewById(R.id.float_show_ibtn);
        mCloseIbtn = (ImageButton) view.findViewById(R.id.float_close_ibtn);
        mLogTv = (TextView) view.findViewById(R.id.float_log_tv);
        mToolLinearLayout = (LinearLayout) view.findViewById(R.id.float_tool_ll);
        mModuleLv = (ListView) view.findViewById(R.id.float_module_lv);
        mToolContainerSv = (ScrollView) view.findViewById(R.id.float_tool_container_sv);

        mModuleAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, Constant.Module.MODULES) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(getResources().getColor(R.color.white));
                if (position == mCurrentSelectPosition) {
                    textView.setBackgroundColor(getResources().getColor(R.color.heavy_transparent_black));
                } else {
                    textView.setBackgroundColor(getResources().getColor(R.color.half_transparent_black));
                }
                return textView;
            }
        };
        mModuleLv.setOnItemClickListener((parent, v, position, id) -> {
            mCurrentSelectPosition = position;
            mModuleAdapter.notifyDataSetChanged();
            openToolView(position);
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
        mShowIbtn.setOnClickListener(v -> {
            isVisible = !isVisible;
            mToolLinearLayout.setVisibility(isVisible ? VISIBLE : GONE);
        });
        mCloseIbtn.setOnClickListener(v -> {
            FloatWindowController.getInstance().close();
        });

        mCurrentSelectPosition = 0;

        openToolView(mCurrentSelectPosition);

    }

    private void openToolView(int position) {
        View view = null;

        switch (position) {
            case Constant.Module.INDEX_MODULE_SYSTEM_WAKE: {
                if (mSystemWakeToolView == null) {
                    mSystemWakeToolView = new SystemWakeToolView(getContext());
                    mSystemWakeToolView.setOnInsertLogEntityListener(logEntity -> showNewestLog(logEntity));
                }
                view = mSystemWakeToolView;
                break;
            }
            case Constant.Module.INDEX_MODULE_APP_WAKE: {
                if (mAppWakeToolView == null) {
                    mAppWakeToolView = new AppWakeToolView(getContext());
                    mAppWakeToolView.setOnInsertLogEntityListener(logEntity -> showNewestLog(logEntity));
                }
                view = mAppWakeToolView;
                break;
            }
            case Constant.Module.INDEX_MODULE_AUDIO: {
                if (mAudioToolView == null) {
                    mAudioToolView = new AudioToolView(getContext());
                    mAudioToolView.setOnInsertLogEntityListener(logEntity -> showNewestLog(logEntity));
                }
                view = mAudioToolView;
                break;
            }
            case Constant.Module.INDEX_MODULE_SPEECH: {
                if (mSpeechToolView == null) {
                    mSpeechToolView = new SpeechToolView(getContext());
                    mSpeechToolView.setOnInsertLogEntityListener(logEntity -> showNewestLog(logEntity));
                }
                view = mSpeechToolView;
                break;
            }
            case Constant.Module.INDEX_MODULE_SEMANTICS: {
                if (mSemanticToolView == null) {
                    mSemanticToolView = new SemanticToolView(getContext());
                    mSemanticToolView.setOnInsertLogEntityListener(logEntity -> showNewestLog(logEntity));
                }
                view = mSemanticToolView;
                break;
            }
            case Constant.Module.INDEX_MODULE_ORDER_DISTRIBUTION: {
                if (mOrderDistributionToolView == null) {
                    mOrderDistributionToolView = new OrderDistributionToolView(getContext());
                    mOrderDistributionToolView.setOnInsertLogEntityListener(logEntity -> showNewestLog(logEntity));
                }
                view = mOrderDistributionToolView;
                break;
            }
            case Constant.Module.INDEX_MODULE_SERVER_SEARCH: {
                if (mServerSearchToolView == null) {
                    mServerSearchToolView = new ServerSearchToolView(getContext());
                    mServerSearchToolView.setOnInsertLogEntityListener(logEntity -> showNewestLog(logEntity));
                }
                view = mServerSearchToolView;
                break;
            }
            case Constant.Module.INDEX_MODULE_CONTENT: {
                if (mContentToolView == null) {
                    mContentToolView = new ContentToolView(getContext());
                    mContentToolView.setOnInsertLogEntityListener(logEntity -> showNewestLog(logEntity));
                }
                view = mContentToolView;
                break;
            }
            case Constant.Module.INDEX_MODULE_DISPLAY: {
                if (mDisplayToolView == null) {
                    mDisplayToolView = new DisplayToolView(getContext());
                }
                view = mDisplayToolView;
                break;
            }
            case Constant.Module.INDEX_MODULE_HELPER: {
                if (mHelperToolView == null) {
                    mHelperToolView = new HelperToolView(getContext());
                    mSystemWakeToolView.setOnInsertLogEntityListener(logEntity -> showNewestLog(logEntity));
                }
                view = mHelperToolView;
                break;
            }
            default: {
                break;
            }
        }

        if (view == null) {
            return;
        }

        mToolContainerSv.removeAllViews();
        mToolContainerSv.addView(view);
    }

    public void setParams(WindowManager.LayoutParams params) {
        mWmParams = params;
    }

    private void showNewestLog(LogEntity logEntity) {
        mLogTv.setText(getContext().getString(R.string.log,
                logEntity.getTarget(),
                logEntity.getMethodName(),
                logEntity.getSuccessCount(),
                logEntity.getFailCount(),
                logEntity.getSpentTime(),
                logEntity.getValue(),
                logEntity.getDescription(),
                logEntity.getDate()));
    }

    public void onLogInsert(LogEntity logEntity) {
        if (logEntity == null) {
            return;
        }

        showNewestLog(logEntity);

        if (Constant.LogTarget.SystemWake.FILTER.accept(logEntity.getTarget())) {
            if (mSystemWakeToolView != null) {
                mSystemWakeToolView.updateStatistics();
            }
        } else if (Constant.LogTarget.AppWake.FILTER.accept(logEntity.getTarget())) {
            if (mAppWakeToolView != null) {
                mAppWakeToolView.updateStatistics();
            }
        } else if (Constant.LogTarget.Audio.FILTER.accept(logEntity.getTarget())) {
            if (mAudioToolView != null) {
                mAudioToolView.updateStatistics();
            }
        } else if (Constant.LogTarget.Speech.FILTER.accept(logEntity.getTarget())) {
            if (mSpeechToolView != null) {
                mSpeechToolView.updateStatistics();
            }
        } else if (Constant.LogTarget.Semantic.FILTER.accept(logEntity.getTarget())) {
            if (mSemanticToolView != null) {
                mSemanticToolView.updateStatistics();
            }
        } else if (Constant.LogTarget.OrderDistribution.FILTER.accept(logEntity.getTarget())) {
            if (mOrderDistributionToolView != null) {
                mOrderDistributionToolView.updateStatistics();
            }
        } else if (Constant.LogTarget.ServerSearch.FILTER.accept(logEntity.getTarget())) {
            if (mServerSearchToolView != null) {
                mServerSearchToolView.updateStatistics();
            }
        } else if (Constant.LogTarget.Content.FILTER.accept(logEntity.getTarget())) {
            if (mContentToolView != null) {
                mContentToolView.updateStatistics();
            }
        } else if (Constant.LogTarget.Display.FILTER.accept(logEntity.getTarget())) {
            if (mDisplayToolView != null) {
                mDisplayToolView.updateStatistics();
            }
        } else if (Constant.LogTarget.Helper.FILTER.accept(logEntity.getTarget())) {
            if (mHelperToolView != null) {
                mHelperToolView.updateStatistics();
            }
        }
    }
}
