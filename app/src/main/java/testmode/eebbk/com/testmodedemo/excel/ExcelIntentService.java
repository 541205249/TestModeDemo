package testmode.eebbk.com.testmodedemo.excel;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import testmode.eebbk.com.testmodedemo.model.LogEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/12
 * <p>
 * Excel操作服务
 */
public class ExcelIntentService extends IntentService {
    private static final String KEY_LOG_ENTITY = "log";
    private static final String KEY_IS_INSERT = "is_insert";

    public ExcelIntentService() {
        super("excel");
    }

    public static void insertExcel(Context context, LogEntity logEntity) {
        Intent intent = new Intent(context, ExcelIntentService.class);
        intent.putExtra(KEY_LOG_ENTITY, logEntity);
        intent.putExtra(KEY_IS_INSERT, true);
        context.startService(intent);
    }

    public static void removeExcel(Context context, LogEntity logEntity) {
        Intent intent = new Intent(context, ExcelIntentService.class);
        intent.putExtra(KEY_LOG_ENTITY, logEntity);
        intent.putExtra(KEY_IS_INSERT, false);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LogEntity logEntity = intent.getParcelableExtra(KEY_LOG_ENTITY);
        boolean isInsert = intent.getBooleanExtra(KEY_IS_INSERT, true);

        if (logEntity == null) {
            return;
        }

        try {
            if (isInsert) {
                ExcelUtil.insertLogEntity(logEntity);
            } else {
                ExcelUtil.removeLogEntity(logEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
