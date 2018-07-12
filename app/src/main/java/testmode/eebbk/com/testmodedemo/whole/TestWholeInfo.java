package testmode.eebbk.com.testmodedemo.whole;

/**
 * 作者： jiazy
 * 日期： 2018/3/22.
 * 公司： 步步高教育电子有限公司
 * 描述：
 */
public class TestWholeInfo {
    public int index;
    public String txt;
    public String pcmName;

    public TestWholeInfo(String txt, int index, String pcmName) {
        this.txt = txt;
        this.index = index;
        this.pcmName = pcmName;
    }

    @Override
    public String toString() {
        return "TestNlpResultInfo{" +
                "txt='" + txt + '\'' +
                ",index='" + txt + '\'' +
                ",pcmName='" + pcmName + '\'' +
                '}';
    }
}
