package testmode.eebbk.com.testmodedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.eebbk.testmodetool.floatwindow.FloatWindow;

public class TestBroadCast extends BroadcastReceiver {

	public TestBroadCast(){}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("hahaoop",intent.getStringExtra("test"));
		FloatWindow.getFloatLayout().setTestTxt(intent.getStringExtra("test"));
	}
}
