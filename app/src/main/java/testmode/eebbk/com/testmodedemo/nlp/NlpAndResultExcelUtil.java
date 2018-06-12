package testmode.eebbk.com.testmodedemo.nlp;

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
import testmode.eebbk.com.testmodedemo.excel.ReadExcelUtils;

public class NlpAndResultExcelUtil {
    private static String FILE_NAME = "0610看题目问问题新测试集.xls";
    public static int mCurrentIndex = 1;

    public interface OnGetOneDataListener {
        void onGetOneData(TestNlpResultInfo testNlpResultInfo);
    }

    public static void getNlpAndResultData(Context context, OnGetOneDataListener onGetOneDataListener) throws Exception {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(dir, FILE_NAME);
        ReadExcelUtils.loadExcel(context, file, sheet -> {
            TestNlpResultInfo data = getOneData(sheet, mCurrentIndex);
            onGetOneDataListener.onGetOneData(data);
        });
    }

    public static void updateNlpAndResultData(String reason) {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(dir, FILE_NAME);

        WritableWorkbook writableWorkbook = null;
        try {
            Workbook workbook = Workbook.getWorkbook(file);
            writableWorkbook = Workbook.createWorkbook(file, workbook);
            WritableSheet sheet = writableWorkbook.getSheet("问题原因分析");
            sheet.addCell(new Label(1, mCurrentIndex, reason));
            writableWorkbook.write();
            writableWorkbook.close();
        } catch (Exception e) {
            try {
                if (writableWorkbook != null) {
                    writableWorkbook.close();
                }
            } catch (Exception e1) {
                e.printStackTrace();
            }
        }
    }

    private static TestNlpResultInfo getOneData(Sheet sheet, int currentIndex) {
        TestNlpResultInfo info;
        if(sheet == null || currentIndex >= sheet.getRows()) {
            info = new TestNlpResultInfo("", currentIndex);
            mCurrentIndex = 1;
        } else {
            Cell[] cells = sheet.getRow(currentIndex);

            if (TextUtils.isEmpty(cells[0].getContents().trim())) {
                mCurrentIndex = 1;
                currentIndex = mCurrentIndex;
                cells = sheet.getRow(currentIndex);
            }

//            String questionEg = cells[1].getContents().trim();
            String data = cells[0].getContents().trim();
            /*if (!TextUtils.isEmpty(questionEg)) {
                data = questionEg.replace("#?#", data);
            }*/
            info = new TestNlpResultInfo(data, currentIndex);

            mCurrentIndex ++;
        }

        EventBus.getDefault().post(info);

        return info;
    }

}
