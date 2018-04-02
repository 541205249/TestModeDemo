package com.eebbk.testmodetool.excel;

import android.os.Environment;

import com.eebbk.testmodetool.DateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtil {
	private static final String EXCEL_FILE_NAME = "问作业指标测试结果.xls";
	private static final String[] EXCEL_TITLE = { "指标项", "耗时", "成功次数", "失败次数", "方法名", "描述", "入表时间" };
	private static final String SHEET_NAME = "业务指标";

	public static synchronized void writeExcel(QualityData quality) throws Exception {
		WritableWorkbook wwb = getWritableWorkbook();
		WritableSheet sheet = getWritableSheet(wwb);
		writeRow(quality, wwb, sheet);
		wwb.close();
	}

	private static void writeRow(QualityData quality, WritableWorkbook wwb, WritableSheet sheet)
			throws WriteException, IOException {
		int sheetRows = sheet.getRows();

		sheet.addCell(new Label(0, sheetRows, quality.getTarget()));
		sheet.addCell(new Label(1, sheetRows, quality.getSpentTime() + ""));
		sheet.addCell(new Label(2, sheetRows, quality.getSuccessCount() + ""));
		sheet.addCell(new Label(3, sheetRows, quality.getFailCount() + ""));
		sheet.addCell(new Label(4, sheetRows, quality.getMethodName()));
		sheet.addCell(new Label(5, sheetRows, quality.getDescription()));
        sheet.addCell(new Label(6, sheetRows, DateUtils.getCurTimeString("yyyy-MM-dd HH:mm:ss")));
		wwb.write();
	}

	private static WritableSheet getWritableSheet(WritableWorkbook wwb) throws WriteException {
        String sheetName = getSheetName();
        WritableSheet sheet = wwb.getSheet(sheetName);
		if (sheet == null) {
			sheet = createWritableSheet(wwb);
		}

		return sheet;
	}

    private static WritableSheet createWritableSheet(WritableWorkbook wwb) throws WriteException {
		WritableSheet sheet = wwb.createSheet(getSheetName(), 0);

		Label label;
		for (int i = 0; i < EXCEL_TITLE.length; i++) {
			// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
			// 在Label对象的子对象中指明单元格的位置和内容
			label = new Label(i, 0, EXCEL_TITLE[i], getHeader());
			sheet.addCell(label);
		}

		return sheet;
	}

	private static WritableWorkbook getWritableWorkbook() throws IOException, BiffException {
		File dir = Environment.getExternalStorageDirectory();
		File file = new File(dir, EXCEL_FILE_NAME);

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

    private static String getSheetName() {
        return SHEET_NAME + DateUtils.getCurTimeString("yyyy-MM-dd");
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

}
