package testmode.eebbk.com.testmodedemo.nlp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.data.ExFilePickerResult;
import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.target.window.FloatPermissionManager;
import testmode.eebbk.com.testmodedemo.target.window.FloatWindowController;
import testmode.eebbk.com.testmodedemo.util.ToastUtils;

import static testmode.eebbk.com.testmodedemo.target.window.FloatWindow.TYPE_TEST_NLP_RESULT;
import static testmode.eebbk.com.testmodedemo.util.BroadCastUtils.ACTION_TEST_NLP;

public class NlpAndResultActivity extends Activity {
    private static final String TAG = "Log_NlpAndResult";
    public static EditText mQuestionIndexEt;
    private TextView mFilePathTv;
    private final int EX_FILE_PICKER_RESULT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nlp_and_result);

        EventBus.getDefault().register(this);

        mQuestionIndexEt = findViewById(R.id.edit_question_index);
        mFilePathTv = findViewById(R.id.tv_file_path);

        findViewById(R.id.btn_start_nlp).setOnClickListener(v -> {
            boolean isPermission = FloatPermissionManager.getInstance().applyFloatWindow(NlpAndResultActivity.this);
            //有对应权限或者系统版本小于7.0
            if (isPermission || Build.VERSION.SDK_INT < 24) {
                //开启悬浮窗
                FloatWindowController.getInstance().open(getApplicationContext(), TYPE_TEST_NLP_RESULT);
            }
        });

        findViewById(R.id.btn_select_file).setOnClickListener(v -> {
            selectFile();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != EX_FILE_PICKER_RESULT) {
            return;
        }

        ExFilePickerResult result = ExFilePickerResult.getFromIntent(data);
        if (result == null || result.getCount() <= 0) {
            return;
        }

        String path = result.getPath();
        List<String> names = result.getNames();
        for (int i = 0; i < names.size(); i++) {
            File f = new File(path, names.get(i));
            try {
                Uri uri = Uri.fromFile(f); //这里获取了真实可用的文件资源
                ToastUtils.getInstance(getApplicationContext()).s("选择文件:" + uri.getPath());

                NlpAndResultExcelUtil.FILE_PATH = f.getAbsolutePath();
                mFilePathTv.setText(NlpAndResultExcelUtil.FILE_PATH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TestNlpResultInfo testNlpResultInfo) {
        Log.i(TAG, testNlpResultInfo.toString());
        if (testNlpResultInfo.index == Integer.MAX_VALUE) {
            ToastUtils.getInstance(getApplicationContext()).l(testNlpResultInfo.data);
            return;
        }

        if (TextUtils.isEmpty(testNlpResultInfo.data)) {
            Log.i(TAG, "data为空");
            return;
        }

        Intent intent = new Intent(ACTION_TEST_NLP);
        intent.putExtra("text", testNlpResultInfo.data);
        sendBroadcast(intent);
    }

    private void selectFile() {
        ExFilePicker exFilePicker = new ExFilePicker();
        exFilePicker.setCanChooseOnlyOneItem(true);// 单选
        exFilePicker.setQuitButtonEnabled(true);

        if (TextUtils.isEmpty(NlpAndResultExcelUtil.FILE_PATH)) {
            exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath());
        }

        exFilePicker.setChoiceType(ExFilePicker.ChoiceType.FILES);
        exFilePicker.start(this, EX_FILE_PICKER_RESULT);
    }
}
