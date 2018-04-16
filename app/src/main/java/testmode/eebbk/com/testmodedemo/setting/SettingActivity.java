package testmode.eebbk.com.testmodedemo.setting;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.common.Constant;

/**
 * @author LiXiaoFeng
 * @date 2018/4/12
 * <p>
 * 设置页
 */
public class SettingActivity extends Activity {
    private static final String SETTING_AUTO_JUMP = "自动跳转";
    private static final String SETTING_AUTO_SCROLL = "自动滚动";
    private static final String SETTING_GLOBAL_LOG = "全局日志";
    private static final String SETTING_SYSTEM_WAKE_LOG = "系统唤醒日志";
    private static final String SETTING_APP_WAKE_LOG = "应用内唤醒日志";
    private static final String SETTING_AUDIO_LOG = "音频日志";
    private static final String SETTING_SPEECH_LOG = "语音识别日志";
    private static final String SETTING_SEMANTICS_LOG = "语义理解日志";
    private static final String SETTING_ORDER_DISTRIBUTION_LOG = "APP指令分发日志";
    private static final String SETTING_SERVER_SEARCH_LOG = "后台搜索日志";
    private static final String SETTING_CONTENT_LOG = "内容日志";
    private static final String SETTING_DISPLAY_LOG = "APP端页面展示日志";
    private static final String SETTING_HELPER_LOG = "作业助手日志";
    private ListView mSettingLv;
    private ArrayAdapter<String> mSettingAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mSettingLv = (ListView) findViewById(R.id.setting_lv);
        mSettingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, new String[]{
                SETTING_AUTO_JUMP
                , SETTING_AUTO_SCROLL
                , SETTING_GLOBAL_LOG
                , SETTING_SYSTEM_WAKE_LOG
                , SETTING_APP_WAKE_LOG
                , SETTING_AUDIO_LOG
                , SETTING_SPEECH_LOG
                , SETTING_SEMANTICS_LOG
                , SETTING_ORDER_DISTRIBUTION_LOG
                , SETTING_SERVER_SEARCH_LOG
                , SETTING_CONTENT_LOG
                , SETTING_DISPLAY_LOG
                , SETTING_HELPER_LOG
        }) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                final CheckedTextView checkedTextView = (CheckedTextView) super.getView(position, convertView, parent);
                String setting = getItem(position);
                if (setting != null) {
                    SettingManager settingManager = SettingManager.getInstance(SettingActivity.this);
                    switch (setting) {
                        case SETTING_AUTO_JUMP: {
                            checkedTextView.setChecked(settingManager.isAutoJump());
                            break;
                        }
                        case SETTING_AUTO_SCROLL: {
                            checkedTextView.setChecked(settingManager.isAutoScroll());
                            break;
                        }
                        case SETTING_GLOBAL_LOG: {
                            checkedTextView.setChecked(settingManager.isOpenLog());
                            break;
                        }
                        case SETTING_SYSTEM_WAKE_LOG: {
                            checkedTextView.setChecked(settingManager.getModuleSettings(Constant.LogTarget.SystemWake.PREFIX));
                            break;
                        }
                        case SETTING_APP_WAKE_LOG: {
                            checkedTextView.setChecked(settingManager.getModuleSettings(Constant.LogTarget.AppWake.PREFIX));
                            break;
                        }
                        case SETTING_AUDIO_LOG: {
                            checkedTextView.setChecked(settingManager.getModuleSettings(Constant.LogTarget.Audio.PREFIX));
                            break;
                        }
                        case SETTING_SPEECH_LOG: {
                            checkedTextView.setChecked(settingManager.getModuleSettings(Constant.LogTarget.Speech.PREFIX));
                            break;
                        }
                        case SETTING_SEMANTICS_LOG: {
                            checkedTextView.setChecked(settingManager.getModuleSettings(Constant.LogTarget.Semantic.PREFIX));
                            break;
                        }
                        case SETTING_ORDER_DISTRIBUTION_LOG: {
                            checkedTextView.setChecked(settingManager.getModuleSettings(Constant.LogTarget.OrderDistribution.PREFIX));
                            break;
                        }
                        case SETTING_SERVER_SEARCH_LOG: {
                            checkedTextView.setChecked(settingManager.getModuleSettings(Constant.LogTarget.ServerSearch.PREFIX));
                            break;
                        }
                        case SETTING_CONTENT_LOG: {
                            checkedTextView.setChecked(settingManager.getModuleSettings(Constant.LogTarget.Content.PREFIX));
                            break;
                        }
                        case SETTING_DISPLAY_LOG: {
                            checkedTextView.setChecked(settingManager.getModuleSettings(Constant.LogTarget.Display.PREFIX));
                            break;
                        }
                        case SETTING_HELPER_LOG: {
                            checkedTextView.setChecked(settingManager.getModuleSettings(Constant.LogTarget.Helper.PREFIX));
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
                checkedTextView.setOnClickListener(v -> {
                    checkedTextView.toggle();
                    onCheckChange(getItem(position), checkedTextView.isChecked());
                });
                return checkedTextView;
            }
        }

        ;
        mSettingLv.setAdapter(mSettingAdapter);
    }

    public void onCheckChange(String setting, boolean isChecked) {
        SettingManager settingManager = SettingManager.getInstance(this);
        switch (setting) {
            case SETTING_AUTO_JUMP: {
                settingManager.setAutoJump(isChecked);
                break;
            }
            case SETTING_AUTO_SCROLL: {
                settingManager.setAutoScroll(isChecked);
                break;
            }
            case SETTING_GLOBAL_LOG: {
                settingManager.setOpenLog(isChecked);
                break;
            }
            case SETTING_SYSTEM_WAKE_LOG: {
                settingManager.setModuleSetting(Constant.LogTarget.SystemWake.PREFIX, isChecked);
                break;
            }
            case SETTING_APP_WAKE_LOG: {
                settingManager.setModuleSetting(Constant.LogTarget.AppWake.PREFIX, isChecked);
                break;
            }
            case SETTING_AUDIO_LOG: {
                settingManager.setModuleSetting(Constant.LogTarget.Audio.PREFIX, isChecked);
                break;
            }
            case SETTING_SPEECH_LOG: {
                settingManager.setModuleSetting(Constant.LogTarget.Speech.PREFIX, isChecked);
                break;
            }
            case SETTING_SEMANTICS_LOG: {
                settingManager.setModuleSetting(Constant.LogTarget.Semantic.PREFIX, isChecked);
                break;
            }
            case SETTING_ORDER_DISTRIBUTION_LOG: {
                settingManager.setModuleSetting(Constant.LogTarget.OrderDistribution.PREFIX, isChecked);
                break;
            }
            case SETTING_SERVER_SEARCH_LOG: {
                settingManager.setModuleSetting(Constant.LogTarget.ServerSearch.PREFIX, isChecked);
                break;
            }
            case SETTING_CONTENT_LOG: {
                settingManager.setModuleSetting(Constant.LogTarget.Content.PREFIX, isChecked);
                break;
            }
            case SETTING_DISPLAY_LOG: {
                settingManager.setModuleSetting(Constant.LogTarget.Display.PREFIX, isChecked);
                break;
            }
            case SETTING_HELPER_LOG: {
                settingManager.setModuleSetting(Constant.LogTarget.Helper.PREFIX, isChecked);
                break;
            }
            default: {
                break;
            }
        }
    }
}
