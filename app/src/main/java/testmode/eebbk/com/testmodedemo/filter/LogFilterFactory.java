package testmode.eebbk.com.testmodedemo.filter;

import testmode.eebbk.com.testmodedemo.model.LogModule;
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

        LogFilter logFilter = null;

        switch (moduleEntity.getName()) {
            case LogModule.SystemWake.FULL: {
                logFilter = logEntity -> logEntity.getTarget().startsWith(moduleEntity.getName());
                break;
            }
            case LogModule.AppWake.FULL: {
                logFilter = logEntity -> logEntity.getTarget().startsWith(moduleEntity.getName());
                break;
            }
            case LogModule.Audio.FULL: {
                logFilter = logEntity -> logEntity.getTarget().startsWith(moduleEntity.getName());
                break;
            }
            case LogModule.Speech.FULL: {
                logFilter = logEntity -> logEntity.getTarget().startsWith(moduleEntity.getName());
                break;
            }
            case LogModule.Semantic.FULL: {
                logFilter = logEntity -> logEntity.getTarget().startsWith(moduleEntity.getName());
                break;
            }
            case LogModule.OrderDistribution.FULL: {
                logFilter = logEntity -> logEntity.getTarget().startsWith(moduleEntity.getName());
                break;
            }
            case LogModule.ServerSearch.FULL: {
                logFilter = logEntity -> logEntity.getTarget().startsWith(moduleEntity.getName());
                break;
            }
            case LogModule.Content.FULL: {
                logFilter = logEntity -> logEntity.getTarget().startsWith(moduleEntity.getName());
                break;
            }
            case LogModule.Display.FULL: {
                logFilter = logEntity -> logEntity.getTarget().startsWith(moduleEntity.getName());
                break;
            }
            case LogModule.Helper.FULL: {
                logFilter = logEntity -> logEntity.getTarget().startsWith(moduleEntity.getName());
                break;
            }
            default: {
                break;
            }
        }

        return logFilter;
    }
}
