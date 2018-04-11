package testmode.eebbk.com.testmodedemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * @author LiXiaoFeng
 * @date 2018/4/9
 */
public class LogEntity implements Parcelable {
    private String id;
    private String target;
    private long spentTime;
    private int successCount;
    private int failCount;
    private float value;
    private String methodName;
    private String description;
    private String date;

    public String getId() {
        return id;
    }

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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LogEntity() {
        this.id = UUID.randomUUID().toString();
    }

    public boolean equals(LogEntity logEntity) {
        if (logEntity == null) {
            return false;
        }

        return logEntity.id.equals(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.target);
        dest.writeLong(this.spentTime);
        dest.writeInt(this.successCount);
        dest.writeInt(this.failCount);
        dest.writeFloat(this.value);
        dest.writeString(this.methodName);
        dest.writeString(this.description);
        dest.writeString(this.date);
    }

    protected LogEntity(Parcel in) {
        this.id = in.readString();
        this.target = in.readString();
        this.spentTime = in.readLong();
        this.successCount = in.readInt();
        this.failCount = in.readInt();
        this.value = in.readFloat();
        this.methodName = in.readString();
        this.description = in.readString();
        this.date = in.readString();
    }

    public static final Creator<LogEntity> CREATOR = new Creator<LogEntity>() {
        @Override
        public LogEntity createFromParcel(Parcel source) {
            return new LogEntity(source);
        }

        @Override
        public LogEntity[] newArray(int size) {
            return new LogEntity[size];
        }
    };
}
