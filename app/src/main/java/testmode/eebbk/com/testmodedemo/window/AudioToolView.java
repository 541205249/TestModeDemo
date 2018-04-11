package testmode.eebbk.com.testmodedemo.window;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.common.Constant;
import testmode.eebbk.com.testmodedemo.model.DataRepository;
import testmode.eebbk.com.testmodedemo.model.LogEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/11
 *
 * 音频工具栏
 */
public class AudioToolView extends LinearLayout {
    private TextView mStatisticsTv;
    private Button mDecibelBtn;
    private OnInsertLogEntityListener mOnInsertLogEntityListener;

    public AudioToolView(Context context) {
        this(context, null);
    }

    public AudioToolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_tool_audio, this);
        mStatisticsTv = findViewById(R.id.tool_audio_statistics_tv);
        mDecibelBtn = findViewById(R.id.tool_audio_decibel_btn);

        mDecibelBtn.setOnClickListener(v -> {
            // TODO: 2018/4/11
        });

        updateStatistics();
    }

    private void insertLogEntity(LogEntity logEntity) {
        if (logEntity == null) {
            return;
        }

        DataRepository.getInstance().insertData(logEntity);
        if (mOnInsertLogEntityListener != null) {
            mOnInsertLogEntityListener.onInsertLogEntity(logEntity);
        }
    }

    public void updateStatistics() {
        List<LogEntity> logEntities = DataRepository.getInstance().getData(Constant.LogTarget.Audio.FILTER);

        if (logEntities == null) {
            return;
        }

        int decibelNumber = 0;
        float minimalDecibel = 0f;
        float maximumDecibel = 0f;
        float totalDecibel = 0f;
        int samplingRateNumber = 0;
        float totalSamplingRate = 0f;

        for (LogEntity logEntity : logEntities) {
            if (logEntity == null) {
                return;
            }

            switch (logEntity.getTarget()) {
                case Constant.LogTarget.Audio.DECIBEL: {
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
                case Constant.LogTarget.Audio.SAMPLING_RATE: {
                    samplingRateNumber++;
                    float samplingRate = logEntity.getValue();
                    totalSamplingRate += samplingRate;
                    break;
                }
            }
        }

        mStatisticsTv.setText(getContext().getString(R.string.audio_statistics,
                minimalDecibel,
                maximumDecibel,
                decibelNumber == 0 ? 0 : totalDecibel / decibelNumber,
                samplingRateNumber == 0 ? 0 : totalSamplingRate / samplingRateNumber));
    }

    public void setOnInsertLogEntityListener(OnInsertLogEntityListener onInsertLogEntityListener) {
        mOnInsertLogEntityListener = onInsertLogEntityListener;
    }
}
