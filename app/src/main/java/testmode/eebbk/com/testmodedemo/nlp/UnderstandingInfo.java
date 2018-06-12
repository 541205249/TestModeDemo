package testmode.eebbk.com.testmodedemo.nlp;

/**
 * 作者： jiazy
 * 日期： 2018/3/22.
 * 公司： 步步高教育电子有限公司
 * 描述：
 */
public class UnderstandingInfo {
    private String skillId;
    private String intentName;
    private String data;

    public UnderstandingInfo(String data, String skillId, String intentName) {
        this.data = data;
        this.skillId = skillId;
        this.intentName = intentName;
    }

    public UnderstandingInfo() {
    }

    public String getSkillId() {
        return skillId == null ? "" : skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getIntentName() {
        return intentName == null ? "" : intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getData() {
        return data == null ? "" : data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UnderstandingInfo{" +
                "skillId='" + skillId + '\'' +
                ", intentName='" + intentName + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
