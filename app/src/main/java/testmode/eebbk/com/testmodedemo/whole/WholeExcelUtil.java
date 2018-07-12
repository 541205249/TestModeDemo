package testmode.eebbk.com.testmodedemo.whole;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import testmode.eebbk.com.testmodedemo.TestModeApplication;
import testmode.eebbk.com.testmodedemo.excel.ReadExcelUtils;
import testmode.eebbk.com.testmodedemo.util.SharedPreferenceUtils;

public class WholeExcelUtil {
    public static String FILE_PATH = "";
    public static String FILE_DIR = "";

    public interface OnDataLoadedListener {
        void onLoaded(TestWholeInfo info);
    }

    public static void getWholeTestData(Context context, OnDataLoadedListener onDataLoadedListener){
        File file = new File(FILE_PATH);
        FILE_DIR = file.getParent();
        try {
            ReadExcelUtils.loadExcel(context, file, "", sheet -> {
                TestWholeInfo info = getOneData(sheet, getCurrentIndex());
                onDataLoadedListener.onLoaded(info);
            });
        } catch (Exception e) {
            e.printStackTrace();
            onDataLoadedListener.onLoaded(null);
        }
    }

    public static void updateResultData(String result, String nlpTxt, String nlpMsg, String asrTxt, String asrResult) {
        File file = new File(FILE_PATH);

        int currentIndex = getCurrentIndex();
        WritableWorkbook writableWorkbook = null;
        try {
            Workbook workbook = Workbook.getWorkbook(file);
            writableWorkbook = Workbook.createWorkbook(file, workbook);
            WritableSheet sheet = writableWorkbook.getSheet(0);
            sheet.addCell(new Label(2, currentIndex, result));
            sheet.addCell(new Label(3, currentIndex, asrResult));
            sheet.addCell(new Label(4, currentIndex, asrTxt));
            sheet.addCell(new Label(5, currentIndex, nlpTxt));
            sheet.addCell(new Label(6, currentIndex, nlpMsg));
            writableWorkbook.write();
            writableWorkbook.close();
        } catch (Exception e) {
            try {
                if (writableWorkbook != null) {
                    writableWorkbook.close();
                }
            } catch (Exception e1) {
                e.printStackTrace();
                updateCurrentIndex(currentIndex+1);
            }
        } finally {
            updateCurrentIndex(currentIndex+1);
        }

    }

    private static TestWholeInfo getOneData(Sheet sheet, int currentIndex) {
        TestWholeInfo info;
        if(sheet == null ) {
            info = new TestWholeInfo("sheet页为空", Integer.MAX_VALUE, "");
        } else if(getCurrentIndex() >= sheet.getRows()) {
            info = new TestWholeInfo("已超过最大值", Integer.MAX_VALUE, "");
        } else {
            Cell[] cells = sheet.getRow(currentIndex);
            String data = cells[0].getContents().trim();
            String pcmName = cells[1].getContents().trim();

            if (TextUtils.isEmpty(data) || TextUtils.isEmpty(pcmName)) {
                info = new TestWholeInfo("该条无数据", Integer.MAX_VALUE, "");
            } else {
                info = new TestWholeInfo(data, currentIndex, pcmName);
            }

        }

        return info;
    }

    private static final String KEY_CURRENT_INDEX = "key_whole_current_index";
    public static int getCurrentIndex() {
        return SharedPreferenceUtils.getInstance(TestModeApplication.getContext()).get(KEY_CURRENT_INDEX, 1);
    }

    public static void updateCurrentIndex(int index) {
        SharedPreferenceUtils.getInstance(TestModeApplication.getContext()).put(KEY_CURRENT_INDEX, index);
    }
}
