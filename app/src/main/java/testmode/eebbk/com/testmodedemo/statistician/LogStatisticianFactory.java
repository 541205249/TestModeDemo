package testmode.eebbk.com.testmodedemo.statistician;

import testmode.eebbk.com.testmodedemo.model.LogModule;
import testmode.eebbk.com.testmodedemo.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 * <p>
 * 日志统计器静态工厂
 */
public final class LogStatisticianFactory {
    private LogStatisticianFactory() {

    }

    public static LogStatistician produceLogStatistician(ModuleEntity moduleEntity) {
        if (moduleEntity.getParent() != null) {
            return null;
        }

        LogStatistician logStatistician = null;
        switch (moduleEntity.getName()) {
            case LogModule.SystemWake.NAME: {
                logStatistician = new SystemWakeLogStatistician();
                break;
            }
            case LogModule.AppWake.NAME: {
                logStatistician = new AppWakeLogStatistician();
                break;
            }
            case LogModule.Audio.NAME: {
                logStatistician = new AudioLogStatistician();
                break;
            }
            case LogModule.Speech.NAME: {
                logStatistician = new SpeechLogStatistician();
                break;
            }
            case LogModule.Semantic.NAME: {
                logStatistician = new SemanticLogStatistician();
                break;
            }
            case LogModule.OrderDistribution.NAME: {
                logStatistician = new OrderDistributionLogStatistician();
                break;
            }
            case LogModule.ServerSearch.NAME: {
                logStatistician = new ServerSearchLogStatistician();
                break;
            }
            case LogModule.Content.NAME: {
                logStatistician = new ContentLogStatistician();
                break;
            }
            case LogModule.Display.NAME: {
                logStatistician = new DisplayLogStatistician();
                break;
            }
            case LogModule.Helper.NAME: {
                logStatistician = new HelperLogStatistician();
                break;
            }
            default: {
                break;
            }
        }
        return logStatistician;
    }
}
