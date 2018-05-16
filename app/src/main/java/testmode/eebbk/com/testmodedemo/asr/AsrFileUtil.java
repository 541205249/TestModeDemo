package testmode.eebbk.com.testmodedemo.asr;

import android.os.Environment;

import java.io.File;

public class AsrFileUtil {
    private static int mCurrentIndex = 0;
    private static final String PCM_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AsrPcm";

    public static String getPcmPath() {
        File dir = new File(PCM_DIR);
        File[] listFiles = dir.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return "";
        }

        File file = listFiles[mCurrentIndex];
        if (file.exists()) {
            mCurrentIndex ++;
            return file.getAbsolutePath();
        } else {
            mCurrentIndex = 0;
            return "";
        }
    }

}
