package testmode.eebbk.com.testmodedemo.statistician;

import java.util.List;

import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/17
 * 【实现的主要功能】
 * 【修改者，修改日期，修改内容】
 */
public interface LogStatistician {
    String statistics(ModuleEntity moduleEntity, List<LogEntity> logEntities);
}
