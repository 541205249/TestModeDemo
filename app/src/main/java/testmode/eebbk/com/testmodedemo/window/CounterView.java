package testmode.eebbk.com.testmodedemo.window;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import testmode.eebbk.com.testmodedemo.R;

/**
 * 作者： jiazy
 * 日期： 2018/1/15.
 * 公司： 步步高教育电子有限公司
 * 描述：
 */
public class CounterView extends LinearLayout implements View.OnClickListener, TextWatcher {
    public static final int MAX_VALUE = Integer.MAX_VALUE;

    public static final int MIN_VALUE = 0;

    private int countValue = 0;//数量

    private ImageView ivAdd, ivMinu;

    private EditText etCount;

    private IChangeCountCallback callback;

    private int maxValue = MAX_VALUE;

    public interface IChangeCountCallback {
        void change(int count);
    }

    public void setCallback(IChangeCountCallback c) {
        this.callback = c;
    }

    private Context mContext;

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context, attrs);
    }

    public void setMaxValue(int max) {
        this.maxValue = max;
    }


    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(mContext).inflate(R.layout.counter_view, this);

        ivMinu = (ImageView) findViewById(R.id.iv_count_minus);
        ivMinu.setOnClickListener(this);

        ivAdd = (ImageView) findViewById(R.id.iv_count_add);
        ivAdd.setOnClickListener(this);

        etCount = (EditText) findViewById(R.id.et_count);
        etCount.addTextChangedListener(this);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_count_add) {
            addAction();

        } else if (i == R.id.iv_count_minus) {
            minuAction();
        }
    }

    /**
     * 添加操
     */
    private void addAction() {
        countValue++;
        btnChangeWord();
    }


    /**
     * 删除操作
     */
    private void minuAction() {
        countValue--;
        btnChangeWord();
    }

    private void changeWord(boolean needUpdate) {
        if (needUpdate) {
            etCount.removeTextChangedListener(this);
            if (!TextUtils.isEmpty(etCount.getText().toString().trim())) {  //不为空的时候才需要赋值
                etCount.setText(String.valueOf(countValue));
            }
            etCount.addTextChangedListener(this);
        }
        etCount.setSelection(etCount.getText().toString().trim().length());
        if(callback != null) {
            callback.change(countValue);
        }
    }

    private void btnChangeWord() {
        ivMinu.setEnabled(countValue > MIN_VALUE);
        ivAdd.setEnabled(countValue < maxValue);
        etCount.setText(String.valueOf(countValue));
        etCount.setSelection(etCount.getText().toString().trim().length());
        if(callback != null) {
            callback.change(countValue);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        boolean needUpdate = false;
        if (!TextUtils.isEmpty(s)) {
            countValue = Integer.valueOf(s.toString());
            if (countValue < MIN_VALUE) {
                countValue = MIN_VALUE;
                ivMinu.setEnabled(false);
                ivAdd.setEnabled(true);
                needUpdate = true;
                Toast.makeText(mContext, String.format("最少添加%s个数量", MIN_VALUE), Toast.LENGTH_SHORT).show();
            } else if (countValue >= maxValue) {
                countValue = maxValue;
                ivMinu.setEnabled(true);
                ivAdd.setEnabled(false);
                needUpdate = true;
                Toast.makeText(mContext, String.format("最多只能添加%s个数量", maxValue), Toast.LENGTH_SHORT).show();

            } else {
                ivMinu.setEnabled(true);
                ivAdd.setEnabled(true);
            }
        } else {  //如果编辑框被清空了，直接填1
            countValue = 1;
            ivMinu.setEnabled(false);
            ivAdd.setEnabled(true);
            needUpdate = true;
            Toast.makeText(mContext, String.format("最少添加%s个数量", MIN_VALUE), Toast.LENGTH_SHORT).show();

        }
        changeWord(needUpdate);
    }
}
