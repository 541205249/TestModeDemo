package testmode.eebbk.com.testmodedemo.excel;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    public static void loadExcel(Context context, File file, OnExcelLoadListener onExcelLoadListener) throws Exception {
        if (!file.exists()) {
            boolean isSuccess = createFileFromAssets(context, file);
            if (!isSuccess) {
                onExcelLoadListener.onLoaded(null);
                return;
            }
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

    private static boolean createFileFromAssets(Context context, File file) {
        try {
            InputStream is = context.getResources().getAssets().open("语义理解monkey测试.xls");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}