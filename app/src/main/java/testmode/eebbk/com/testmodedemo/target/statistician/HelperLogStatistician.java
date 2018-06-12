package testmode.eebbk.com.testmodedemo.target.statistician;

import java.util.List;

import testmode.eebbk.com.testmodedemo.target.model.LogModule;
import testmode.eebbk.com.testmodedemo.target.model.LogEntity;
import testmode.eebbk.com.testmodedemo.target.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/19
 */
public class HelperLogStatistician implements LogStatistician {
    @Override
    public String statistics(ModuleEntity moduleEntity, List<LogEntity> logEntities) {
        if (logEntities == null) {
            return null;
        }

        int totalNumber;
        int successNumber = 0;
        int failNumber = 0;
        int totalAppDurationNumber = 0;
        long totalAppDuration = 0;
        int totalTransportDurationNumber = 0;
        long totalTransportDuration = 0;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                continue;
            }

            switch (logEntity.getTarget()) {
                case LogModule.Helper.SUCCESS: {
                    successNumber++;
                    break;
                }
                case LogModule.Helper.FAIL: {
                    failNumber++;
                    break;
                }
                case LogModule.Helper.APP_DURATION: {
                    totalAppDuration += logEntity.getSpentTime();
                    totalAppDurationNumber++;
                    break;
                }
                case LogModule.Helper.TRANSPORT_DURATION: {
                    totalTransportDuration += logEntity.getSpentTime();
                    totalTransportDurationNumber++;
                    break;
                }
                default: {
                    break;
                }
            }
        }

        totalNumber = successNumber + failNumber;

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
        stringBuilder.append("其他应用响应平均耗时：");
        stringBuilder.append(totalAppDurationNumber == 0 ? 0 : totalAppDuration / totalAppDurationNumber);
        stringBuilder.append("\n");
        stringBuilder.append("空中传输平均耗时：");
        stringBuilder.append(totalTransportDurationNumber == 0 ? 0 : totalTransportDuration / totalTransportDurationNumber);
        return stringBuilder.toString();
    }
}
