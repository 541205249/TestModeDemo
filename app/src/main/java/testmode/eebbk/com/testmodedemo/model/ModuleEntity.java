package testmode.eebbk.com.testmodedemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LiXiaoFeng
 * @date 2018/4/17
 */
public class ModuleEntity {
    private String id;
    private String name;
    private ModuleEntity parent;
    private boolean isEnd;
    private List<ModuleEntity> moduleEntities;
    private List<TargetEntity> targetEntities;

    public ModuleEntity(String name, ModuleEntity parent) {
        this.name = name;
        this.parent = parent;
        this.id = UUID.randomUUID().toString();
        this.moduleEntities = new ArrayList<>();
        this.targetEntities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ModuleEntity getParent() {
        return parent;
    }

    public List<ModuleEntity> getModuleEntities() {
        return moduleEntities;
    }

    public List<TargetEntity> getTargetEntities() {
        return targetEntities;
    }

    public void setTargetEntities(List<TargetEntity> targetEntities) {
        this.targetEntities = targetEntities;
    }

    public void setModuleEntities(List<ModuleEntity> moduleEntities) {
        this.moduleEntities = moduleEntities;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public String getId() {
        return id;
    }
}
