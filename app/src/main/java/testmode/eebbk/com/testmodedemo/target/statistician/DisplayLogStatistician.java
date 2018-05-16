package testmode.eebbk.com.testmodedemo.target.statistician;

import java.util.List;

import testmode.eebbk.com.testmodedemo.target.model.LogModule;
import testmode.eebbk.com.testmodedemo.target.model.LogEntity;
import testmode.eebbk.com.testmodedemo.target.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public class DisplayLogStatistician implements LogStatistician {
    @Override
    public String statistics(ModuleEntity moduleEntity, List<LogEntity> logEntities) {
        if (logEntities == null) {
            return null;
        }

        int totalNumber = 0;
        long totalDisplayDuration = 0;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                continue;
            }

            switch (logEntity.getTarget()) {
                case LogModule.Display.DURATION: {
                    totalNumber++;
                    totalDisplayDuration += logEntity.getSpentTime();
                    break;
                }
                default: {
                    break;
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("APP页面展示平均耗时：");
        stringBuilder.append(totalNumber == 0 ? 0 : totalDisplayDuration / totalNumber);
        return stringBuilder.toString();
    }
}
