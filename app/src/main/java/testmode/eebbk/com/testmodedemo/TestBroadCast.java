package testmode.eebbk.com.testmodedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.eebbk.testmodetool.floatwindow.FloatWindow;

public class TestBroadCast extends BroadcastReceiver {

	public TestBroadCast(){}

	@Override
	public void onReceive(Context context, Intent intent) {
		String target = intent.getStringExtra("target");
		long timeDifference = intent.getLongExtra("timeDifference", 1);
		Toast.makeText(context,"动态广播：" + target + ",timeDifference=" + timeDifference, Toast.LENGTH_SHORT).show();
		FloatWindow.getFloatLayout().setTestTxt(target + ",timeDifference=" + timeDifference);
	}
}
