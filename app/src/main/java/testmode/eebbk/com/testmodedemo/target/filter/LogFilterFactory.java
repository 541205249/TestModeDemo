package testmode.eebbk.com.testmodedemo.target.filter;

import testmode.eebbk.com.testmodedemo.target.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 * <p>
 * 日志筛选器静态工厂
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
