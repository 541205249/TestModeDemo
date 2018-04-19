package testmode.eebbk.com.testmodedemo.statistician;

import java.util.List;

import testmode.eebbk.com.testmodedemo.model.LogModule;
import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public class AppWakeLogStatistician implements LogStatistician {
    @Override
    public String statistics(ModuleEntity moduleEntity, List<LogEntity> logEntities) {
        if (logEntities == null) {
            return null;
        }

        int totalNumber = 0;
        int successNumber = 0;
        int failNumber = 0;
        int mistakeNumber = 0;
        int totalDurationNumber = 0;
        long totalDuration = 0;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                continue;
            }

            switch (logEntity.getTarget()) {
                case LogModule.AppWake.Voice.SUCCESS: {
                    successNumber++;
                    break;
                }
                case LogModule.AppWake.Voice.FAIL: {
                    failNumber++;
                    break;
                }
                case LogModule.AppWake.Voice.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case LogModule.AppWake.Voice.DURATION: {
                    totalDuration += logEntity.getSpentTime();
                    totalDurationNumber++;
                    break;
                }
                case LogModule.AppWake.Button.SUCCESS: {
                    successNumber++;
                    break;
                }
                case LogModule.AppWake.Button.FAIL: {
                    failNumber++;
                    break;
                }
                case LogModule.AppWake.Button.MISTAKE: {
                    mistakeNumber++;
                    break;
                }
                case LogModule.AppWake.Button.DURATION: {
                    totalDuration += logEntity.getSpentTime();
                    totalDurationNumber++;
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
        stringBuilder.append("应用内唤醒平均耗时：");
        stringBuilder.append(totalDurationNumber == 0 ? 0 : totalDuration / totalDurationNumber);

        return stringBuilder.toString();
    }
}
