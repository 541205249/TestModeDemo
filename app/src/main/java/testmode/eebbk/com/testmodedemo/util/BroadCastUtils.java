package testmode.eebbk.com.testmodedemo.util;

import android.content.Context;
import android.content.Intent;

/**
 * 作者： jiazy
 * 日期： 2018/6/28.
 * 公司： 步步高教育电子有限公司
 * 描述：
 */
public class BroadCastUtils {
    private static final String SEND_ACTION_TEST_ON_OFF = "com.eebbk.askhomework.test_mode.on_off.action";

    public static final String RECEIVE_ACTION_NLP = "com.eebbk.askhomework.send.nlp_result.action";
    public static final String ACTION_TEST_NLP = "com.eebbk.askhomework.receive.test_nlp.action";
    public static final String ACTION_MONKEY_NLP = "com.eebbk.askhomework.receive.monkeynlp.action";

    public static final String RECEIVE_ACTION_ASR_RESULT = "com.eebbk.askhomework.send.asr_result.action";
    public static final String ACTION_TEST_ASR = "com.eebbk.askhomework.receive.test_asr.action";

    public static void registerBroadcast(Context context, String action) {
        Intent intent = new Intent(SEND_ACTION_TEST_ON_OFF);
        intent.putExtra("test_action", action);
        context.getApplicationContext().sendBroadcast(intent);
    }
}