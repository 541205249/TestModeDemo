package testmode.eebbk.com.testmodedemo.asr;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import testmode.eebbk.com.testmodedemo.TestModeApplication;
import testmode.eebbk.com.testmodedemo.excel.ReadExcelUtils;
import testmode.eebbk.com.testmodedemo.nlp.TestNlpResultInfo;
import testmode.eebbk.com.testmodedemo.util.SharedPreferenceUtils;

public class AsrExcelUtil {
    private static String FILE_NAME = "";

    public static void getNlpAndResultData(Context context) throws Exception {
        ReadExcelUtils.loadExcel(context, null, "Asr测试结果记录表.xls", sheet -> {
            getOneData(sheet, getCurrentIndex());
        });
    }

    public static void updateNlpAndResultData(String result, String asrTxt, String pcmFileName) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), FILE_NAME);

        int currentIndex = getCurrentIndex();
        WritableWorkbook writableWorkbook = null;
        try {
            Workbook workbook = Workbook.getWorkbook(file);
            writableWorkbook = Workbook.createWorkbook(file, workbook);
            WritableSheet sheet = writableWorkbook.getSheet(0);
            sheet.addCell(new Label(0, currentIndex, pcmFileName));
            sheet.addCell(new Label(1, currentIndex, asrTxt));
            sheet.addCell(new Label(2, currentIndex, result));
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

    private static void getOneData(Sheet sheet, int currentIndex) {
        TestNlpResultInfo info;
        if(sheet == null ) {
            info = new TestNlpResultInfo("sheet页为空", Integer.MAX_VALUE);
        } else if(getCurrentIndex() >= sheet.getRows()) {
            info = new TestNlpResultInfo("已超过最大值", Integer.MAX_VALUE);
        } else {
            Cell[] cells = sheet.getRow(currentIndex);
            if (TextUtils.isEmpty(cells[0].getContents().trim())) {
                info = new TestNlpResultInfo("该条无数据", Integer.MAX_VALUE);
            } else {
                String data = cells[0].getContents().trim();
                info = new TestNlpResultInfo(data, currentIndex);
            }

        }

        EventBus.getDefault().post(info);
    }

    private static final String KEY_CURRENT_INDEX = "key_current_index";
    public static int getCurrentIndex() {
        return SharedPreferenceUtils.getInstance(TestModeApplication.getContext()).get(KEY_CURRENT_INDEX, 1);
    }

    public static void updateCurrentIndex(int index) {
        SharedPreferenceUtils.getInstance(TestModeApplication.getContext()).put(KEY_CURRENT_INDEX, index);
    }
}
