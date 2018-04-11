package testmode.eebbk.com.testmodedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import testmode.eebbk.com.testmodedemo.common.Constant;
import testmode.eebbk.com.testmodedemo.common.DateUtils;
import testmode.eebbk.com.testmodedemo.excel.ExcelUtil;
import testmode.eebbk.com.testmodedemo.excel.QualityData;
import testmode.eebbk.com.testmodedemo.model.DataRepository;
import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.window.FloatLayout2;
import testmode.eebbk.com.testmodedemo.window.FloatWindow;

public class TestBroadCastReceiver extends BroadcastReceiver {
    public TestBroadCastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        insert2Memory(intent);
        insert2Excel(intent);
    }

    private void insert2Memory(Intent intent) {
        String target = intent.getStringExtra("target");
        long spentTime = intent.getLongExtra("spentTime", 0);
        int successCount = intent.getIntExtra("successCount", 0);
        int failCount = intent.getIntExtra("failCount", 0);
        float value = intent.getFloatExtra("value", 0f);
        String methodName = intent.getStringExtra("methodName");
        String description = intent.getStringExtra("description");

        LogEntity logEntity = new LogEntity();
        logEntity.setTarget(target);
        logEntity.setSpentTime(spentTime);
        logEntity.setSuccessCount(successCount);
        logEntity.setFailCount(failCount);
        logEntity.setValue(value);
        logEntity.setMethodName(methodName);
        logEntity.setDescription(description);
        logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
        DataRepository.getInstance().insertData(logEntity);
        updateFloatWindow(logEntity);
    }

    private void insert2Excel(Intent intent) {
        String target = intent.getStringExtra("target");
        long spentTime = intent.getLongExtra("spentTime", 0);
        int successCount = intent.getIntExtra("successCount", 0);
        int failCount = intent.getIntExtra("failCount", 0);
        float value = intent.getFloatExtra("value", 0f);
        String methodName = intent.getStringExtra("methodName");
        String description = intent.getStringExtra("description");

        QualityData quality = new QualityData();
        quality.setTarget(target);
        quality.setSpentTime(spentTime);
        quality.setSuccessCount(successCount);
        quality.setFailCount(failCount);
        quality.setValue(value);
        quality.setMethodName(methodName);
        quality.setDescription(description);
        quality.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));

        try {
            ExcelUtil.writeExcel(quality);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateFloatWindow(LogEntity logEntity) {
        FloatLayout2 floatLayout2 = FloatWindow.getFloatLayout();

        if (logEntity == null || floatLayout2 == null) {
            return;
        }

        floatLayout2.onLogInsert(logEntity);
    }
}
