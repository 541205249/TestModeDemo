package testmode.eebbk.com.testmodedemo.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import testmode.eebbk.com.testmodedemo.common.LogFilter;
import testmode.eebbk.com.testmodedemo.excel.ExcelIntentService;

/**
 * @author LiXiaoFeng
 * @date 2018/4/8
 */
public class DataRepository {
    private static final String TAG = DataRepository.class.getSimpleName();
    public static final String KEY_CHANGE_TYPE = "change_type";
    public static final String KEY_LOG_ENTITY = "log_entity";
    public static final int CHANGE_TYPE_INSERT = 1;
    public static final int CHANGE_TYPE_REMOVE = 2;
    private Context mContext;
    private static DataRepository instance;
    private LinkedList<LogEntity> mLogEntities;
    private LocalBroadcastManager mLocalBroadcastManager;

    private DataRepository() {
        mLogEntities = new LinkedList<>();
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public List<LogEntity> getData(LogFilter filter) {
        List<LogEntity> data = new ArrayList<>();
        if (filter == null) {
            data.addAll(mLogEntities);
        } else {
            for (LogEntity logEntity : mLogEntities) {
                if (logEntity != null && filter.accept(logEntity.getTarget())) {
                    data.add(logEntity);
                }
            }
        }
        return data;
    }

    public void insertData(LogEntity logEntity) {
        if (logEntity == null) {
            return;
        }

        mLogEntities.addFirst(logEntity);
        notifyDataChange(logEntity, CHANGE_TYPE_INSERT);
        ExcelIntentService.insertExcel(mContext, logEntity);
    }

    public void removeData(LogEntity logEntity) {
        if (mLogEntities == null || logEntity == null) {
            return;
        }

        mLogEntities.remove(logEntity);
        notifyDataChange(logEntity, CHANGE_TYPE_REMOVE);
        ExcelIntentService.removeExcel(mContext, logEntity);
    }

    public void init(Context context) {
        mContext = context;
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    private void notifyDataChange(LogEntity logEntity, int changeType) {
        if (mLocalBroadcastManager == null || logEntity == null) {
            return;
        }

        Intent intent = new Intent();
        intent.setAction(logEntity.getTarget());
        intent.putExtra(KEY_LOG_ENTITY, logEntity);
        intent.putExtra(KEY_CHANGE_TYPE, changeType);
        mLocalBroadcastManager.sendBroadcast(intent);
    }
}
