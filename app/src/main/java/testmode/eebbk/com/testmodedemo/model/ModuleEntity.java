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

    ModuleEntity(String name, ModuleEntity parent) {
        this.name = name;
        this.parent = parent;
        this.id = UUID.randomUUID().toString();
        this.isEnd = true;
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
        List<ModuleEntity> result = new ArrayList<>();
        result.addAll(moduleEntities);
        return result;
    }

    public List<TargetEntity> getTargetEntities() {
        List<TargetEntity> result = new ArrayList<>();
        result.addAll(targetEntities);
        return result;
    }

    public void addTargetEntity(TargetEntity targetEntity) {
        if (targetEntity == null) {
            return;
        }

        targetEntities.add(targetEntity);
    }

    public void addModuleEntity(ModuleEntity moduleEntity) {
        if (moduleEntity == null) {
            return;
        }

        moduleEntities.add(moduleEntity);
        isEnd = false;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public String getId() {
        return id;
    }
}
