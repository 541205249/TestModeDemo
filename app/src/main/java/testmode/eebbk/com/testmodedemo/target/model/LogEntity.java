package testmode.eebbk.com.testmodedemo.target.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * @author LiXiaoFeng
 * @date 2018/4/9
 */
public class LogEntity implements Parcelable {
    /**
     * id
     */
    private String id;
    /**
     * 指标
     */
    private String target;
    /**
     * 耗时
     */
    private long spentTime;
    /**
     * 值
     */
    private float value;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 描述
     */
    private String description;
    /**
     * 标签
     */
    private String tag;
    /**
     * 时间
     */
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LogEntity() {
        this.id = UUID.randomUUID().toString();
    }

    public boolean equals(LogEntity logEntity) {
        return logEntity != null && logEntity.id.equals(id);
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
        dest.writeFloat(this.value);
        dest.writeString(this.methodName);
        dest.writeString(this.description);
        dest.writeString(this.tag);
        dest.writeString(this.date);
    }

    protected LogEntity(Parcel in) {
        this.id = in.readString();
        this.target = in.readString();
        this.spentTime = in.readLong();
        this.value = in.readFloat();
        this.methodName = in.readString();
        this.description = in.readString();
        this.tag = in.readString();
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
