package testmode.eebbk.com.testmodedemo.statistician;

import java.util.List;

import testmode.eebbk.com.testmodedemo.model.LogModule;
import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public class ServerSearchLogStatistician implements LogStatistician {
    @Override
    public String statistics(ModuleEntity moduleEntity, List<LogEntity> logEntities) {
        if (logEntities == null) {
            return null;
        }

        int totalNumber;
        int successNumber = 0;
        int failNumber = 0;
        int totalServerNumber = 0;
        long totalServerDuration = 0;
        int totalTransportNumber = 0;
        long totalTransportDuration = 0;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                continue;
            }

            switch (logEntity.getTarget()) {
                case LogModule.ServerSearch.SUCCESS: {
                    successNumber++;
                    break;
                }
                case LogModule.ServerSearch.FAIL: {
                    failNumber++;
                    break;
                }
                case LogModule.ServerSearch.SERVER_DURATION: {
                    totalServerDuration += logEntity.getSpentTime();
                    totalServerNumber++;
                    break;
                }
                case LogModule.ServerSearch.TRANSPORT_DURATION: {
                    totalTransportDuration += logEntity.getSpentTime();
                    totalTransportNumber++;
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
        stringBuilder.append("后台搜索准确率：");
        stringBuilder.append(totalNumber == 0 ? 0 : successNumber * 100.0f / totalNumber + "%");
        stringBuilder.append("\n");
        stringBuilder.append("后台搜索平均耗时：");
        stringBuilder.append(totalServerNumber == 0 ? 0 : totalServerDuration / totalServerNumber);
        stringBuilder.append("\n");
        stringBuilder.append("空中传输平均耗时：");
        stringBuilder.append(totalTransportNumber == 0 ? 0 : totalTransportDuration / totalTransportNumber);
        return stringBuilder.toString();
    }
}
