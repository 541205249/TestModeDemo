package testmode.eebbk.com.testmodedemo.nlp;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import testmode.eebbk.com.testmodedemo.excel.ReadExcelUtils;

public class NlpExcelUtil {
    private static int mCurrentIndex = 1;

    public static void getUnderstandingData(Context context, String excelName) throws Exception {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(dir, excelName);
        ReadExcelUtils.loadExcel(context, file, sheet -> {
            getOneUnderstandingData(context, sheet, mCurrentIndex);
        });
    }

    private static void getOneUnderstandingData(Context context, Sheet sheet, int currentIndex) {
        UnderstandingInfo info;
        if(sheet == null || currentIndex >= sheet.getRows()) {
            info = new UnderstandingInfo("", "", "");
            mCurrentIndex = 1;
        } else {
            Cell[] cells = sheet.getRow(currentIndex);

            if (TextUtils.isEmpty(cells[0].getContents().trim())) {
                mCurrentIndex = 1;
                currentIndex = mCurrentIndex;
                cells = sheet.getRow(currentIndex);
            }

            info = new UnderstandingInfo(
                    cells[0].getContents().trim(),
                    cells[1].getContents().trim(),
                    cells[2].getContents().trim());

            mCurrentIndex ++;
        }

        EventBus.getDefault().post(info);
    }

}
