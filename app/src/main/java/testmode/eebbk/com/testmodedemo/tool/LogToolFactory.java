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
 *
 * 测试项工具静态工厂
 */
public class LogToolFactory {
    private LogToolFactory() {

    }

    public static LogTool produceTool(TargetEntity target) {
        if (target == null) {
            return null;
        }

        LogTool logTool;

        if (target.getFullName().equals(LogModule.Audio.DECIBEL)) {
            logTool = targetEntity -> {
                Toast.makeText(TestModeApplication.getTestModeApplicationContext(), "功能待开发", Toast.LENGTH_SHORT).show();
            };
        } else {
            logTool = targetEntity -> {
                LogEntity logEntity = new LogEntity();
                logEntity.setTarget(targetEntity.getFullName());
                logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
                DataRepository.getInstance().insertData(logEntity);
            }
            ;
        }

        return logTool;
    }
}