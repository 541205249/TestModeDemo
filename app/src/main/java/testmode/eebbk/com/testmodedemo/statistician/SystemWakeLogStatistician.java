package testmode.eebbk.com.testmodedemo.statistician;

import java.util.List;

import testmode.eebbk.com.testmodedemo.model.LogModule;
import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public class SystemWakeLogStatistician implements LogStatistician {
    @Override
    public String statistics(ModuleEntity moduleEntity, List<LogEntity> logEntities) {
        if (logEntities == null) {
            return null;
        }

        int totalNumber;
        int successNumber = 0;
        int failNumber = 0;
        int mistakeNumber = 0;
        int systemTotalDurationNumber = 0;
        long systemTotalDuration = 0;
        int appTotalDurationNumber = 0;
        long appTotalDuration = 0;
        int systemWakeTotalDurationNumber = 0;
        long systemWakeTotalDuration = 0;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                return null;
            }

            switch (logEntity.getTarget()) {
                case LogModule.SystemWake.Black.Voice.SUCCESS: {
                    successNumber++;
                    break;
                }
                case LogModule.SystemWake.Black.Voice.FAIL: {
                    failNumber++;
                    break;
                }
                case LogModule.SystemWake.Black.Voice.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case LogModule.SystemWake.Black.Voice.SYSTEM_DURATION: {
                    systemTotalDuration += logEntity.getSpentTime();
                    systemTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Black.Voice.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    appTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Black.Voice.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    systemWakeTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Black.Gesture.SUCCESS: {
                    successNumber++;
                    break;
                }
                case LogModule.SystemWake.Black.Gesture.FAIL: {
                    failNumber++;
                    break;
                }
                case LogModule.SystemWake.Black.Gesture.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case LogModule.SystemWake.Black.Gesture.SYSTEM_DURATION: {
                    systemTotalDuration += logEntity.getSpentTime();
                    systemTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Black.Gesture.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    appTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Black.Gesture.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    systemWakeTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Lock.Voice.SUCCESS: {
                    successNumber++;
                    break;
                }
                case LogModule.SystemWake.Lock.Voice.FAIL: {
                    failNumber++;
                    break;
                }
                case LogModule.SystemWake.Lock.Voice.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case LogModule.SystemWake.Lock.Voice.SYSTEM_DURATION: {
                    systemTotalDuration += logEntity.getSpentTime();
                    systemTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Lock.Voice.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    appTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Lock.Voice.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    systemWakeTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Bright.Voice.SUCCESS: {
                    successNumber++;
                    break;
                }
                case LogModule.SystemWake.Bright.Voice.FAIL: {
                    failNumber++;
                    break;
                }
                case LogModule.SystemWake.Bright.Voice.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case LogModule.SystemWake.Bright.Voice.SYSTEM_DURATION: {
                    systemTotalDuration += logEntity.getSpentTime();
                    systemTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Bright.Voice.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    appTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Bright.Voice.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    systemWakeTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Bright.Button.SUCCESS: {
                    successNumber++;
                    break;
                }
                case LogModule.SystemWake.Bright.Button.FAIL: {
                    failNumber++;
                    break;
                }
                case LogModule.SystemWake.Bright.Button.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case LogModule.SystemWake.Bright.Button.SYSTEM_DURATION: {
                    systemTotalDuration += logEntity.getSpentTime();
                    systemTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Bright.Button.APP_DURATION: {
                    appTotalDuration += logEntity.getSpentTime();
                    appTotalDurationNumber++;
                    break;
                }
                case LogModule.SystemWake.Bright.Button.TOTAL_DURATION: {
                    systemWakeTotalDuration += logEntity.getSpentTime();
                    systemWakeTotalDurationNumber++;
                    break;
                }
                default: {
                    break;
                }
            }
        }
        totalNumber = successNumber + failNumber + mistakeNumber;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("总次数：");
        stringBuilder.append(totalNumber);
        stringBuilder.append("\n");
        stringBuilder.append("成功次数：");
        stringBuilder.append(successNumber);
        stringBuilder.append("\n");
        stringBuilder.append("失败次数：");
        stringBuilder.append(failNumber);
        stringBuilder.append("\n");
        stringBuilder.append("误唤醒次数：");
        stringBuilder.append(mistakeNumber);
        stringBuilder.append("\n");
        stringBuilder.append("唤醒成功率：");
        stringBuilder.append(totalNumber == 0 ? 0 : successNumber * 100.0f / totalNumber + "%");
        stringBuilder.append("\n");
        stringBuilder.append("误唤醒率：");
        stringBuilder.append(totalNumber == 0 ? 0 : mistakeNumber * 100.0f / totalNumber + "%");
        stringBuilder.append("\n");
        stringBuilder.append("系统处理平均耗时：");
        stringBuilder.append(systemTotalDurationNumber == 0 ? 0 : systemTotalDuration / systemTotalDurationNumber);
        stringBuilder.append("\n");
        stringBuilder.append("应用响应平均耗时：");
        stringBuilder.append(appTotalDurationNumber == 0 ? 0 : appTotalDuration / appTotalDurationNumber);
        stringBuilder.append("\n");
        stringBuilder.append("系统唤醒平均总耗时：");
        stringBuilder.append(systemWakeTotalDurationNumber == 0 ? 0 : systemWakeTotalDuration / systemWakeTotalDurationNumber);
        return stringBuilder.toString();
    }
}
