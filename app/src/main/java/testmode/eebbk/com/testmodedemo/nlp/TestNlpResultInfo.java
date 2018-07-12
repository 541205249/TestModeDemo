package testmode.eebbk.com.testmodedemo.nlp;

/**
 * 作者： jiazy
 * 日期： 2018/3/22.
 * 公司： 步步高教育电子有限公司
 * 描述：
 */
public class TestNlpResultInfo {
    public String data;
    public int index;

    public TestNlpResultInfo(String data, int index) {
        this.data = data;
        this.index = index;
    }

    @Override
    public String toString() {
        return "TestNlpResultInfo{" +
                "txt='" + data + '\'' +
                ",index='" + data + '\'' +
                '}';
    }
}
