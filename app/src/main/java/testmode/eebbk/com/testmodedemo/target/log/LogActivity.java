package testmode.eebbk.com.testmodedemo.target.log;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.target.model.DataRepository;
import testmode.eebbk.com.testmodedemo.target.model.LogEntity;
import testmode.eebbk.com.testmodedemo.target.model.ModuleEntity;
import testmode.eebbk.com.testmodedemo.target.model.TargetEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public class LogActivity extends Activity {
    private ListView mModuleLv;
    private FrameLayout mContentFl;
    private ArrayAdapter<ModuleEntity> mModuleAdapter;
    private ModuleEntity mCurrentModule;
    private Map<String, LogFragment> mLogFragmentMap = new HashMap<>();
    private DataRepository.DataChangeBroadcastReceiver mDataChangeBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initView();
        initDataChangeBroadcastReceiver();
    }

    private void initView() {
        mModuleLv = findViewById(R.id.log_module_lv);
        mContentFl = findViewById(R.id.log_container_fl);

        mModuleAdapter = new ArrayAdapter<ModuleEntity>(this, android.R.layout.simple_list_item_1, DataRepository.getInstance().getRootModuleEntities()) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                ModuleEntity moduleEntity = mModuleAdapter.getItem(position);
                if (moduleEntity == mCurrentModule) {
                    textView.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    textView.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
                textView.setText(moduleEntity.getName());
                return textView;
            }
        };
        mModuleLv.setAdapter(mModuleAdapter);
        mModuleLv.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            ModuleEntity moduleEntity = mModuleAdapter.getItem(position);
            openFragment(moduleEntity);
            mCurrentModule = moduleEntity;
            mModuleAdapter.notifyDataSetChanged();
        });
        mCurrentModule = mModuleAdapter.getItem(0);
        openFragment(mCurrentModule);
    }

    private void initDataChangeBroadcastReceiver() {
        mDataChangeBroadcastReceiver = new DataRepository.DataChangeBroadcastReceiver() {
            @Override
            protected void onInsertLogEntity(Context context, LogEntity logEntity) {
                List<ModuleEntity> moduleEntities = DataRepository.getInstance().getRootModuleEntities();
                if (moduleEntities == null) {
                    return;
                }
                for (ModuleEntity moduleEntity : moduleEntities) {
                    if (logEntity.getTarget().startsWith(moduleEntity.getName())) {
                        openFragment(moduleEntity);
                        mCurrentModule = moduleEntity;
                        mModuleAdapter.notifyDataSetChanged();
                        return;
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        for (ModuleEntity moduleEntity : DataRepository.getInstance().getRootModuleEntities()) {
            List<TargetEntity> targetEntities = DataRepository.getInstance().getTargetEntities(moduleEntity);
            for (TargetEntity targetEntity : targetEntities) {
                intentFilter.addAction(targetEntity.getFullName());
            }
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mDataChangeBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mDataChangeBroadcastReceiver);
    }

    private void openFragment(ModuleEntity moduleEntity) {
        FragmentManager fragmentManager = getFragmentManager();

        if (fragmentManager.findFragmentByTag(moduleEntity.getName()) != null) {
            return;
        }

        LogFragment logFragment = mLogFragmentMap.get(moduleEntity.getName());
        if (logFragment == null) {
            logFragment = LogFragment.newInstance(moduleEntity);
        }

        fragmentManager.beginTransaction()
                .replace(mContentFl.getId(), logFragment, moduleEntity.getName())
                .commitAllowingStateLoss();
    }
}
