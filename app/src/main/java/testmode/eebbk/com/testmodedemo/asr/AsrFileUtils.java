package testmode.eebbk.com.testmodedemo.asr;

import android.os.Environment;

import java.io.File;

public class AsrFileUtils {
    public static String PCM_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AsrPcm";

    public static String getPcmPath(int currentIndex) {
        File dir = new File(PCM_DIR);
        File[] listFiles = dir.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return "";
        }

        File file = listFiles[currentIndex];
        if (file.exists()) {
            return file.getAbsolutePath();
        } else {
            return "";
        }
    }

}
