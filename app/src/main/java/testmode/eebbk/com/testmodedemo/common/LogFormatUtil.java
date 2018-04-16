package testmode.eebbk.com.testmodedemo.common;

import java.math.BigDecimal;

import testmode.eebbk.com.testmodedemo.model.LogEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/15
 * <p>
 * 日志格式化工具
 */
public class LogFormatUtil {
    public static String format(LogEntity logEntity) {
        String target = logEntity.getTarget();
        long spentTime = logEntity.getSpentTime();
        float value = logEntity.getValue();
        String methodName = logEntity.getMethodName();
        String description = logEntity.getDescription();
        String tag = logEntity.getTag();
        String date = logEntity.getDate();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("指标项：");
        stringBuilder.append(target == null ? "" : target);
        stringBuilder.append("\n");
        stringBuilder.append("时间：");
        stringBuilder.append(date == null ? "" : date);
        stringBuilder.append("\n");
        stringBuilder.append("描述：");
        stringBuilder.append(description == null ? "" : description);
        stringBuilder.append("\n");
        stringBuilder.append("方法名：");
        stringBuilder.append(methodName == null ? "" : methodName);
        stringBuilder.append("\n");
        stringBuilder.append("耗时：");
        stringBuilder.append(spentTime);
        stringBuilder.append("\n");
        stringBuilder.append("值：");
        stringBuilder.append(new BigDecimal(value).stripTrailingZeros());
        stringBuilder.append("\n");
        stringBuilder.append("标签：");
        stringBuilder.append(tag == null ? "" : tag);
        return stringBuilder.toString();
    }
}
