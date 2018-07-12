package testmode.eebbk.com.testmodedemo.whole;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.data.ExFilePickerResult;
import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.target.window.FloatPermissionManager;
import testmode.eebbk.com.testmodedemo.target.window.FloatWindowController;
import testmode.eebbk.com.testmodedemo.util.ToastUtils;

import static testmode.eebbk.com.testmodedemo.target.window.FloatWindow.TYPE_TEST_WHOLE;

public class WholeActivity extends Activity {
    public static EditText mQuestionIndexEt;
    private TextView mFilePathTv;
    private final int EX_FILE_PICKER_RESULT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole);

        mQuestionIndexEt = findViewById(R.id.edit_question_index);
        mFilePathTv = findViewById(R.id.tv_file_path);

        findViewById(R.id.btn_start).setOnClickListener(v -> {
            boolean isPermission = FloatPermissionManager.getInstance().applyFloatWindow(WholeActivity.this);
            //有对应权限或者系统版本小于7.0
            if (isPermission || Build.VERSION.SDK_INT < 24) {
                //开启悬浮窗
                FloatWindowController.getInstance().open(getApplicationContext(), TYPE_TEST_WHOLE);
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

                WholeExcelUtil.FILE_PATH = f.getAbsolutePath();
                mFilePathTv.setText(WholeExcelUtil.FILE_PATH);
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

    private void selectFile() {
        ExFilePicker exFilePicker = new ExFilePicker();
        exFilePicker.setCanChooseOnlyOneItem(true);// 单选
        exFilePicker.setQuitButtonEnabled(true);

        if (TextUtils.isEmpty(WholeExcelUtil.FILE_PATH)) {
            exFilePicker.setStartDirectory(Environment.getExternalStorageDirectory().getPath());
        }

        exFilePicker.setChoiceType(ExFilePicker.ChoiceType.FILES);
        exFilePicker.start(this, EX_FILE_PICKER_RESULT);
    }
}
