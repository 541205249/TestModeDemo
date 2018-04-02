package com.eebbk.testmodetool.excel;

/**
 * 作者： jiazy
 * 日期： 2018/3/13.
 * 公司： 步步高教育电子有限公司
 * 描述："指标项", "耗时", "成功次数", "失败次数", "方法名", "描述"
 */
public class QualityData {
    private String target;
    private long spentTime;
    private int successCount;
    private int failCount;
    private String methodName;
    private String description;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public long getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(long spentTime) {
        this.spentTime = spentTime;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
