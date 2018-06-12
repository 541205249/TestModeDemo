package testmode.eebbk.com.testmodedemo.dumiasr;

/**
 * 作者： jiazy
 * 日期： 2018/3/22.
 * 公司： 步步高教育电子有限公司
 * 描述：
 */
public class ResultInfo1 {
    public String data;
    public int index;

    public ResultInfo1(String data, int index) {
        this.data = data;
        this.index = index;
    }

    @Override
    public String toString() {
        return "TestNlpResultInfo{" +
                "data='" + data + '\'' +
                ",index='" + data + '\'' +
                '}';
    }
}
