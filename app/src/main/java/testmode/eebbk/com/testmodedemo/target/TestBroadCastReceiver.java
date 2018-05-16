package testmode.eebbk.com.testmodedemo.target;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import testmode.eebbk.com.testmodedemo.util.Constant;
import testmode.eebbk.com.testmodedemo.util.DateUtils;
import testmode.eebbk.com.testmodedemo.target.model.DataRepository;
import testmode.eebbk.com.testmodedemo.target.model.LogEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/15
 * <p>
 * 用于接收调试应用发送的调试信息广播
 */
public class TestBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String target = intent.getStringExtra("target");
        long spentTime = intent.getLongExtra("spentTime", 0);
        float value = intent.getFloatExtra("value", 0f);
        String methodName = intent.getStringExtra("methodName");
        String description = intent.getStringExtra("description");
        String tag = intent.getStringExtra("tag");

        LogEntity logEntity = new LogEntity();
        logEntity.setTarget(target);
        logEntity.setSpentTime(spentTime);
        logEntity.setValue(value);
        logEntity.setMethodName(methodName);
        logEntity.setDescription(description);
        logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
        logEntity.setTag(tag);

        DataRepository.getInstance().insertData(logEntity);
    }
}
