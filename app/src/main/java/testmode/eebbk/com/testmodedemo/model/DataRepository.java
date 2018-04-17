package testmode.eebbk.com.testmodedemo.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import testmode.eebbk.com.testmodedemo.common.LogFilter;
import testmode.eebbk.com.testmodedemo.common.LogModuleParseException;
import testmode.eebbk.com.testmodedemo.common.XmlUtil;
import testmode.eebbk.com.testmodedemo.excel.ExcelIntentService;

/**
 * @author LiXiaoFeng
 * @date 2018/4/8
 * <p>
 * 数据缓存库，负责存储日志数据及执行相关的全局操作
 */
public class DataRepository {
    private static final String TAG = DataRepository.class.getSimpleName();
    public static final String KEY_CHANGE_TYPE = "change_type";
    public static final String KEY_LOG_ENTITY = "log_entity";
    public static final int CHANGE_TYPE_INVALID = -1;
    public static final int CHANGE_TYPE_INSERT = 1;
    public static final int CHANGE_TYPE_REMOVE = 2;
    private Context mContext;
    private static DataRepository instance;
    private List<ModuleEntity> mModuleEntities;
    private LinkedList<LogEntity> mLogEntities;
    private LocalBroadcastManager mLocalBroadcastManager;

    private DataRepository() {
        mLogEntities = new LinkedList<>();
        mModuleEntities = new ArrayList<>();
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public ModuleEntity getModuleEntity(String id) {
        if (id == null) {
            return null;
        }

        return getModuleEntity(mModuleEntities, id);
    }

    private ModuleEntity getModuleEntity(List<ModuleEntity> moduleEntities, String id) {
        for (ModuleEntity moduleEntity : moduleEntities) {
            if (moduleEntity.getId().equals(id)) {
                return moduleEntity;
            }

            if (!moduleEntity.isEnd()) {
                ModuleEntity result = getModuleEntity(moduleEntity.getModuleEntities(), id);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    public TargetEntity getTargetEntity(String id) {
        if (id == null) {
            return null;
        }

        return getTargetEntity(mModuleEntities, id);
    }

    private TargetEntity getTargetEntity(List<ModuleEntity> moduleEntities, String id) {
        for (ModuleEntity moduleEntity : moduleEntities) {
            if (!moduleEntity.isEnd()) {
                TargetEntity result = getTargetEntity(moduleEntity.getModuleEntities(), id);
                if (result != null) {
                    return result;
                }
            } else {
                for (TargetEntity targetEntity : moduleEntity.getTargetEntities()) {
                    if (targetEntity.getId().equals(id)) {
                        return targetEntity;
                    }
                }
            }
        }
        return null;
    }

    public List<TargetEntity> getTargetEntities(ModuleEntity moduleEntity) {
        if (moduleEntity == null) {
            return null;
        }

        List<TargetEntity> result = new ArrayList<>();
        getTargetEntities(result, moduleEntity);
        return result;
    }

    public List<TargetEntity> getAllTargetEntities() {
        List<TargetEntity> result = new ArrayList<>();
        for (ModuleEntity moduleEntity : mModuleEntities) {
            getTargetEntities(result, moduleEntity);
        }
        return result;
    }

    private void getTargetEntities(List<TargetEntity> result, ModuleEntity moduleEntity) {
        if (moduleEntity.isEnd()) {
            result.addAll(moduleEntity.getTargetEntities());
        } else {
            for (ModuleEntity child : moduleEntity.getModuleEntities()) {
                getTargetEntities(result, child);
            }
        }
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

    public void init(Context context) throws LogModuleParseException {
        mContext = context;
        mModuleEntities = XmlUtil.parse(context);
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
