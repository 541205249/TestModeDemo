package testmode.eebbk.com.testmodedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import testmode.eebbk.com.testmodedemo.asr.AsrTestActivity;
import testmode.eebbk.com.testmodedemo.nlp.MonkeyAsrNlpActivity;
import testmode.eebbk.com.testmodedemo.nlp.NlpActivity;
import testmode.eebbk.com.testmodedemo.target.TargetCheckActivity;
import testmode.eebbk.com.testmodedemo.whole.WholeActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] modeArr = new String[]{"指标验收", "ASR", "ASR+NLP+搜索", "NLP+搜索", "NLP+Monkey"};
        GridView listView = findViewById(R.id.gv_mode);
        ListAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.txt_item, R.id.tv, modeArr);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent();
            switch (position) {
                case 0 :
                    intent.setClass(this, TargetCheckActivity.class);
                    break;
                case 1 :
                    intent.setClass(this, AsrTestActivity.class);
                    break;
                case 2 :
                    intent.setClass(this, WholeActivity.class);
                    break;
                case 3 :
                    intent.setClass(this, NlpActivity.class);
                    break;
                case 4 :
                    intent.setClass(this, MonkeyAsrNlpActivity.class);
                    break;
                default:
                    intent.setClass(this, TargetCheckActivity.class);
                    break;
            }
            startActivity(intent);
        });
    }
}
