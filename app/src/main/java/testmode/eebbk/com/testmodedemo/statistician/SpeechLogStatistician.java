package testmode.eebbk.com.testmodedemo.statistician;

import java.util.List;

import testmode.eebbk.com.testmodedemo.model.LogModule;
import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public class SpeechLogStatistician implements LogStatistician {
    @Override
    public String statistics(ModuleEntity moduleEntity, List<LogEntity> logEntities) {
        if (logEntities == null) {
            return null;
        }

        int totalNumber;
        int successNumber = 0;
        int failNumber = 0;
        int totalAudioDurationNumber = 0;
        long totalAudioDuration = 0;
        int totalConvertingDurationNumber = 0;
        long totalConvertingDuration = 0;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                continue;
            }

            switch (logEntity.getTarget()) {
                case LogModule.Speech.SUCCESS: {
                    successNumber++;
                    break;
                }
                case LogModule.Speech.FAIL: {
                    failNumber++;
                    break;
                }
                case LogModule.Speech.AUDIO_DURATION: {
                    totalAudioDuration += logEntity.getSpentTime();
                    totalAudioDurationNumber++;
                    break;
                }
                case LogModule.Speech.CONVERTING_DURATION: {
                    totalConvertingDuration += logEntity.getSpentTime();
                    totalConvertingDurationNumber++;
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
        stringBuilder.append("语音识别率：");
        stringBuilder.append(totalNumber == 0 ? 0 : successNumber * 100.0f / totalNumber + "%");
        stringBuilder.append("\n");
        stringBuilder.append("语音平均时长：");
        stringBuilder.append(totalAudioDurationNumber == 0 ? 0 : totalAudioDuration / totalAudioDurationNumber);
        stringBuilder.append("\n");
        stringBuilder.append("语音即时转换平均耗时：");
        stringBuilder.append(totalConvertingDurationNumber == 0 ? 0 : totalConvertingDuration / totalConvertingDurationNumber);
        return stringBuilder.toString();
    }
}
