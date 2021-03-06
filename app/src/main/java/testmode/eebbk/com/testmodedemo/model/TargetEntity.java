package testmode.eebbk.com.testmodedemo.model;

import java.util.UUID;

/**
 * @author LiXiaoFeng
 * @date 2018/4/17
 */
public class TargetEntity {
    private String id;
    private String name;
    private boolean isTool;
    private ModuleEntity parent;
    private String fullName;

    TargetEntity(String name, String fullName, boolean isTool, ModuleEntity parent) {
        this.name = name;
        this.fullName = fullName;
        this.isTool = isTool;
        this.parent = parent;
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public boolean isTool() {
        return isTool;
    }

    public ModuleEntity getParent() {
        return parent;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
}
