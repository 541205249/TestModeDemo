package testmode.eebbk.com.testmodedemo.filter;

import testmode.eebbk.com.testmodedemo.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 * 【实现的主要功能】
 * 【修改者，修改日期，修改内容】
 */
public final class LogFilterFactory {

    private LogFilterFactory() {

    }

    public static LogFilter produceLogFilter(ModuleEntity moduleEntity) {
        if (moduleEntity.getParent() != null) {
            return null;
        }

        return logEntity -> logEntity.getTarget().startsWith(moduleEntity.getName());
    }
}
