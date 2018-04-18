package testmode.eebbk.com.testmodedemo.statistician;

import java.util.List;

import testmode.eebbk.com.testmodedemo.model.LogModule;
import testmode.eebbk.com.testmodedemo.model.LogEntity;
import testmode.eebbk.com.testmodedemo.model.ModuleEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public class AudioLogStatistician implements LogStatistician {
    @Override
    public String statistics(ModuleEntity moduleEntity, List<LogEntity> logEntities) {
        if (logEntities == null) {
            return null;
        }

        int decibelNumber = 0;
        float minimalDecibel = 0f;
        float maximumDecibel = 0f;
        float totalDecibel = 0f;
        int samplingRateNumber = 0;
        float totalSamplingRate = 0f;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                return null;
            }

            switch (logEntity.getTarget()) {
                case LogModule.Audio.DECIBEL: {
                    decibelNumber++;
                    float decibel = logEntity.getValue();
                    if (decibel < minimalDecibel) {
                        minimalDecibel = decibel;
                    }
                    if (decibel > maximumDecibel) {
                        maximumDecibel = decibel;
                    }
                    totalDecibel += decibel;
                    break;
                }
                case LogModule.Audio.SAMPLING_RATE: {
                    samplingRateNumber++;
                    float samplingRate = logEntity.getValue();
                    totalSamplingRate += samplingRate;
                    break;
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("历史最低分贝：");
        stringBuilder.append(minimalDecibel);
        stringBuilder.append("\n");
        stringBuilder.append("历史最高分贝：");
        stringBuilder.append(maximumDecibel);
        stringBuilder.append("\n");
        stringBuilder.append("平均分贝：");
        stringBuilder.append(decibelNumber == 0 ? 0 : totalDecibel / decibelNumber);
        stringBuilder.append("\n");
        stringBuilder.append("平均采样率：");
        stringBuilder.append(samplingRateNumber == 0 ? 0 : totalSamplingRate / samplingRateNumber + "%");

        return stringBuilder.toString();
    }
}
