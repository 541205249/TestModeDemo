package testmode.eebbk.com.testmodedemo.excel;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Excel不能超过3万8千行，否则读取报错
 */
public class ReadExcelUtils {

    public interface OnExcelLoadListener {
        void onLoaded(Sheet sheet);
    }

    public static void loadExcel(File file, OnExcelLoadListener onExcelLoadListener) throws Exception {
        if (!file.exists()) {
            onExcelLoadListener.onLoaded(null);
        }

        new Thread(() -> {
            try {
                Workbook wb = Workbook.getWorkbook(file);
                onExcelLoadListener.onLoaded(wb.getSheet(0));
                wb.close();
            } catch (IOException | BiffException e) {
                e.printStackTrace();
                onExcelLoadListener.onLoaded(null);
            }
        }).start();
    }

}