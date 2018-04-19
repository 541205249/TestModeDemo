package testmode.eebbk.com.testmodedemo.excel;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import testmode.eebbk.com.testmodedemo.TestBroadCastReceiver;
import testmode.eebbk.com.testmodedemo.model.LogEntity;

public class ExcelUtil {
    private static final String EXCEL_FILE_NAME = "指标测试结果.xls";
    private static final String[] EXCEL_TITLE = {"指标项", "耗时", "值", "方法名", "描述", "标签", "入表时间", "ID"};
    private static final String SHEET_NAME = "业务指标";

    public static void insertLogEntity(Context context, LogEntity logEntity) throws Exception {
        WritableWorkbook wwb = getWritableWorkbook(context);
        WritableSheet sheet = getWritableSheet(wwb, logEntity);
        insertRow(logEntity, wwb, sheet);
    }

    private static void insertRow(LogEntity logEntity, WritableWorkbook wwb, WritableSheet sheet)
            throws WriteException, IOException {
        int sheetRows = sheet.getRows();

        sheet.addCell(new Label(0, sheetRows, logEntity.getTarget()));
        sheet.addCell(new Label(1, sheetRows, logEntity.getSpentTime() + ""));
        sheet.addCell(new Label(2, sheetRows, logEntity.getValue() + ""));
        sheet.addCell(new Label(3, sheetRows, logEntity.getMethodName()));
        sheet.addCell(new Label(4, sheetRows, logEntity.getDescription()));
        sheet.addCell(new Label(5, sheetRows, logEntity.getTag()));
        sheet.addCell(new Label(6, sheetRows, logEntity.getDate()));
        sheet.addCell(new Label(7, sheetRows, logEntity.getId()));
        wwb.write();
        wwb.close();
    }

    public static void removeLogEntity(Context context, LogEntity logEntity) throws Exception {
        WritableWorkbook wwb = getWritableWorkbook(context);
        WritableSheet sheet = getWritableSheet(wwb, logEntity);
        int sheetRows = sheet.getRows();
        for (int i = 0; i < sheetRows; i++) {
            Cell cell = sheet.getCell(7, i);
            if (cell.getContents().equals(logEntity.getId())) {
                sheet.removeRow(i);
                break;
            }
        }

        wwb.write();
        wwb.close();
    }

    private static WritableSheet getWritableSheet(WritableWorkbook wwb, LogEntity logEntity) throws WriteException {
        String sheetName = getSheetName(logEntity);
        WritableSheet sheet = wwb.getSheet(sheetName);
        if (sheet == null) {
            sheet = createWritableSheet(sheetName, wwb);
        }

        return sheet;
    }

    private static WritableSheet createWritableSheet(String sheetName, WritableWorkbook wwb) throws WriteException {
        WritableSheet sheet = wwb.createSheet(sheetName, 0);

        Label label;
        for (int i = 0; i < EXCEL_TITLE.length; i++) {
            // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
            // 在Label对象的子对象中指明单元格的位置和内容
            label = new Label(i, 0, EXCEL_TITLE[i], getHeader());
            sheet.addCell(label);
        }

        return sheet;
    }

    private static WritableWorkbook getWritableWorkbook(Context context) throws IOException, BiffException, PackageManager.NameNotFoundException {
        File file = getExcelFile(context);

        WritableWorkbook wwb;
        if (file.exists()) {
            Workbook wb = Workbook.getWorkbook(file);
            wwb = Workbook.createWorkbook(file, wb);
        } else {
            OutputStream os = new FileOutputStream(file);
            wwb = Workbook.createWorkbook(os);
        }

        return wwb;
    }

    private static File getExcelFile(Context context) throws PackageManager.NameNotFoundException {
        File dir = Environment.getExternalStorageDirectory();
        ComponentName componentName = new ComponentName(context, TestBroadCastReceiver.class);
        ActivityInfo activityInfo = context.getPackageManager().getReceiverInfo(componentName, PackageManager.GET_META_DATA);
        return new File(dir, activityInfo.metaData.getString("test_app_name") + EXCEL_FILE_NAME);
    }

    private static String getSheetName(LogEntity logEntity) {
        return SHEET_NAME + logEntity.getDate().substring(0, 10);
    }

    private static WritableCellFormat getHeader() {
        WritableFont font = new WritableFont(WritableFont.TIMES, 12,
                WritableFont.BOLD);// 定义字体
        try {
            font.setColour(Colour.BLUE);// 蓝色字体
        } catch (WriteException e1) {
            e1.printStackTrace();
        }

        WritableCellFormat format = new WritableCellFormat(font);
        try {
            format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
            // format.setBorder(Border.ALL, BorderLineStyle.THIN,
            // Colour.BLACK);// 黑色边框
            // format.setBackground(Colour.YELLOW);// 黄色背景
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return format;
    }

    public static void test() {
        WritableWorkbook writableWorkbook = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "man.xls");
            Workbook workbook = Workbook.getWorkbook(file);
            writableWorkbook = Workbook.createWorkbook(file, workbook);
            WritableSheet sheet = writableWorkbook.getSheet("Sheet1");
            sheet.addCell(new Label(2, 0, "xxx"));
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
}
