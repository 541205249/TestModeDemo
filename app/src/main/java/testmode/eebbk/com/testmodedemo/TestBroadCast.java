package testmode.eebbk.com.testmodedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.eebbk.testmodetool.excel.ExcelUtil;
import com.eebbk.testmodetool.excel.QualityData;
import com.eebbk.testmodetool.floatwindow.FloatWindow;

public class TestBroadCast extends BroadcastReceiver {

	public TestBroadCast(){}

	@Override
	public void onReceive(Context context, Intent intent) {
		insert2Excel(intent);
		showResult(context, intent);
	}

	private void insert2Excel(Intent intent) {
		String target = intent.getStringExtra("target");
		long spentTime = intent.getLongExtra("spentTime", 0);
		int successCount = intent.getIntExtra("successCount", 0);
		int failCount = intent.getIntExtra("failCount", 0);
		String methodName = intent.getStringExtra("methodName");
		String description = intent.getStringExtra("description");

		QualityData quality = new QualityData();
		quality.setTarget(target);
		quality.setSpentTime(spentTime);
		quality.setSuccessCount(successCount);
		quality.setFailCount(failCount);
		quality.setMethodName(methodName);
		quality.setDescription(description);

		try {
			ExcelUtil.writeExcel(quality);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showResult(Context context, Intent intent) {
		String target = intent.getStringExtra("target");
		long spentTime = intent.getLongExtra("spentTime", 0);
		Toast.makeText(context,"动态广播：" + target + ",spentTime=" + spentTime, Toast.LENGTH_SHORT).show();
		FloatWindow.getFloatLayout().setTestTxt(target + ",spentTime=" + spentTime);
	}
}
