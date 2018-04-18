package testmode.eebbk.com.testmodedemo.statistician;

import testmode.eebbk.com.testmodedemo.model.LogModule;
import testmode.eebbk.com.testmodedemo.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
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
            case LogModule.SystemWake.FULL: {
                logStatistician = new SystemWakeLogStatistician();
                break;
            }
            case LogModule.AppWake.FULL: {
                logStatistician = new AppWakeLogStatistician();
                break;
            }
            case LogModule.Audio.FULL: {
                logStatistician = new AudioLogStatistician();
                break;
            }
            case LogModule.Speech.FULL: {
                logStatistician = new SpeechLogStatistician();
                break;
            }
            case LogModule.Semantic.FULL: {
                logStatistician = new SemanticLogStatistician();
                break;
            }
            case LogModule.OrderDistribution.FULL: {
                logStatistician = new OrderDistributionLogStatistician();
                break;
            }
            case LogModule.ServerSearch.FULL: {
                logStatistician = new ServerSearchLogStatistician();
                break;
            }
            case LogModule.Content.FULL: {
                logStatistician = new ContentLogStatistician();
                break;
            }
            case LogModule.Display.FULL: {
                logStatistician = new DisplayLogStatistician();
                break;
            }
            case LogModule.Helper.FULL: {
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
