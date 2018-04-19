package testmode.eebbk.com.testmodedemo.statistician;

import java.util.List;

import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/17
 * <p>
 * 日志统计器
 */
public interface LogStatistician {
    String statistics(ModuleEntity moduleEntity, List<LogEntity> logEntities);
}
