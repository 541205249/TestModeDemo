package testmode.eebbk.com.testmodedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import testmode.eebbk.com.testmodedemo.common.Constant;
import testmode.eebbk.com.testmodedemo.common.DateUtils;
import testmode.eebbk.com.testmodedemo.model.DataRepository;
import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.setting.SettingManager;
import testmode.eebbk.com.testmodedemo.window.FloatLayout;
import testmode.eebbk.com.testmodedemo.window.FloatWindow;

public class TestBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String target = intent.getStringExtra("target");
        long spentTime = intent.getLongExtra("spentTime", 0);
        int successCount = intent.getIntExtra("successCount", 0);
        int failCount = intent.getIntExtra("failCount", 0);
        float value = intent.getFloatExtra("value", 0f);
        String methodName = intent.getStringExtra("methodName");
        String description = intent.getStringExtra("description");
        String tag = intent.getStringExtra("tag");

        LogEntity logEntity = new LogEntity();
        logEntity.setTarget(target);
        logEntity.setSpentTime(spentTime);
        logEntity.setSuccessCount(successCount);
        logEntity.setFailCount(failCount);
        logEntity.setValue(value);
        logEntity.setMethodName(methodName);
        logEntity.setDescription(description);
        logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
        logEntity.setTag(tag);

        if (!check(context, logEntity)) {
            return;
        }

        DataRepository.getInstance().insertData(logEntity);
        updateFloatWindow(logEntity);
    }

    private boolean check(Context context, LogEntity logEntity) {
        SettingManager settingManager = SettingManager.getInstance(context);

        if (!settingManager.isOpenLog()) {
            return false;
        }

        return (Constant.LogTarget.SystemWake.FILTER.accept(logEntity.getTarget()) && settingManager.getModuleSettings(Constant.LogTarget.SystemWake.PREFIX))
                || (Constant.LogTarget.AppWake.FILTER.accept(logEntity.getTarget()) && settingManager.getModuleSettings(Constant.LogTarget.AppWake.PREFIX))
                || (Constant.LogTarget.Audio.FILTER.accept(logEntity.getTarget()) && settingManager.getModuleSettings(Constant.LogTarget.Audio.PREFIX))
                || (Constant.LogTarget.Speech.FILTER.accept(logEntity.getTarget()) && settingManager.getModuleSettings(Constant.LogTarget.Speech.PREFIX))
                || (Constant.LogTarget.Semantic.FILTER.accept(logEntity.getTarget()) && settingManager.getModuleSettings(Constant.LogTarget.Semantic.PREFIX))
                || (Constant.LogTarget.OrderDistribution.FILTER.accept(logEntity.getTarget()) && settingManager.getModuleSettings(Constant.LogTarget.OrderDistribution.PREFIX))
                || (Constant.LogTarget.ServerSearch.FILTER.accept(logEntity.getTarget()) && settingManager.getModuleSettings(Constant.LogTarget.ServerSearch.PREFIX))
                || (Constant.LogTarget.Content.FILTER.accept(logEntity.getTarget()) && settingManager.getModuleSettings(Constant.LogTarget.Content.PREFIX))
                || (Constant.LogTarget.Display.FILTER.accept(logEntity.getTarget()) && settingManager.getModuleSettings(Constant.LogTarget.Display.PREFIX))
                || (Constant.LogTarget.Helper.FILTER.accept(logEntity.getTarget()) && settingManager.getModuleSettings(Constant.LogTarget.Helper.PREFIX));
    }

    private void updateFloatWindow(LogEntity logEntity) {
        FloatLayout floatLayout = FloatWindow.getFloatLayout();

        if (logEntity == null || floatLayout == null) {
            return;
        }

        floatLayout.onLogInsert(logEntity);
    }
}
