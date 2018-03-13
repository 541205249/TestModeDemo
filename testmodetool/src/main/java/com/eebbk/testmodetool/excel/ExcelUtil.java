package com.eebbk.testmodetool.excel;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtil {

	public static String ROOT_PATH = Environment.getExternalStorageDirectory().getPath();
	private static final String[] EXCEL_TITLE = { "指标项", "耗时" };

	public static void writeExcel(Context context, List<QualityData> qualityList,
                                  String fileName) throws Exception {
		if (isStorageUsable()) {
			Toast.makeText(context, "SD卡不可用", Toast.LENGTH_LONG).show();
			return;
		}

		File dir = new File(ROOT_PATH);
		File file = new File(dir, fileName + ".xls");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 创建Excel工作表
		WritableWorkbook wwb;
		OutputStream os = new FileOutputStream(file);
		wwb = Workbook.createWorkbook(os);
		// 添加第一个工作表并设置第一个Sheet的名字
		WritableSheet sheet = wwb.createSheet("业务指标", 0);
		Label label;
		for (int i = 0; i < EXCEL_TITLE.length; i++) {
			// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
						// 在Label对象的子对象中指明单元格的位置和内容
			label = new Label(i, 0, EXCEL_TITLE[i], getHeader());
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
		}

		for (int i = 0; i < qualityList.size(); i++) {
			QualityData quality = qualityList.get(i);

			Label name = new Label(0, i + 1, quality.getName());
			Label time = new Label(1, i + 1, quality.getTime() + "");

			sheet.addCell(name);
			sheet.addCell(time);
			Toast.makeText(context, "写入成功", Toast.LENGTH_LONG).show();
			
		}
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
	}

	public static WritableCellFormat getHeader() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 10,
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

	private static final int MB_1 = 1024 * 1024;
	private static boolean isStorageUsable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || getAvailableStorage() < MB_1;
	}

	private static long getAvailableStorage() {
		StatFs statFs = new StatFs(ROOT_PATH);
		long blockSize = statFs.getBlockSize();
		long availableBlocks = statFs.getAvailableBlocks();
		return blockSize * availableBlocks;
	}
}
