package testmode.eebbk.com.testmodedemo.target.statistician;

import java.util.List;

import testmode.eebbk.com.testmodedemo.target.model.LogModule;
import testmode.eebbk.com.testmodedemo.target.model.LogEntity;
import testmode.eebbk.com.testmodedemo.target.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public class SemanticLogStatistician implements LogStatistician {
    @Override
    public String statistics(ModuleEntity moduleEntity, List<LogEntity> logEntities) {
        if (logEntities == null) {
            return null;
        }

        int totalNumber;
        int successNumber = 0;
        int failNumber = 0;
        int totalDurationNumber = 0;
        long totalDuration = 0;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                continue;
            }

            switch (logEntity.getTarget()) {
                case LogModule.Semantic.SUCCESS: {
                    successNumber++;
                    break;
                }
                case LogModule.Semantic.FAIL: {
                    failNumber++;
                    break;
                }
                case LogModule.Semantic.DURATION: {
                    totalDuration += logEntity.getSpentTime();
                    totalDurationNumber++;
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
        stringBuilder.append("语义理解正确率：");
        stringBuilder.append(totalNumber == 0 ? 0 : successNumber * 100.0f / totalNumber + "%");
        stringBuilder.append("\n");
        stringBuilder.append("语义理解平均耗时：");
        stringBuilder.append(totalDurationNumber == 0 ? 0 : totalDuration / totalDurationNumber);
        return stringBuilder.toString();
    }
}
