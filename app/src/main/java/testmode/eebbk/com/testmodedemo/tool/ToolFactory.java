package testmode.eebbk.com.testmodedemo.tool;

import android.widget.Toast;

import testmode.eebbk.com.testmodedemo.Constant;
import testmode.eebbk.com.testmodedemo.TestModeApplication;
import testmode.eebbk.com.testmodedemo.model.DataRepository;
import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.model.LogModule;
import testmode.eebbk.com.testmodedemo.model.TargetEntity;
import testmode.eebbk.com.testmodedemo.util.DateUtils;

/**
 * @author LiXiaoFeng
 * @date 2018/4/19
 */
public class ToolFactory {
    private ToolFactory() {

    }

    public static Tool produceTool(TargetEntity target) {
        if (target == null) {
            return null;
        }

        Tool tool;

        if (target.getFullName().equals(LogModule.Audio.DECIBEL)) {
            tool = targetEntity -> {
                Toast.makeText(TestModeApplication.getTestModeApplicationContext(), "功能待开发", Toast.LENGTH_SHORT).show();
            };
        } else {
            tool = targetEntity -> {
                LogEntity logEntity = new LogEntity();
                logEntity.setTarget(targetEntity.getFullName());
                logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
                DataRepository.getInstance().insertData(logEntity);
            }
            ;
        }

        return tool;
    }
}
