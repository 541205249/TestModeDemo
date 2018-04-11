package testmode.eebbk.com.testmodedemo.log;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.common.Constant;

/**
 * @author LiXiaoFeng
 * @date 2018/4/8
 */
public class LogActivity extends Activity {
    private AllLogFragment mAllLogFragment;
    private SystemWakeLogFragment mSystemWakeLogFragment;
    private AppWakeLogFragment mAppWakeLogFragment;
    private AudioLogFragment mAudioLogFragment;
    private ContentLogFragment mContentLogFragment;
    private HelperLogFragment mHelperLogFragment;
    private SpeechLogFragment mSpeechLogFragment;
    private DisplayLogFragment mDisplayLogFragment;
    private ListView mTypeLv;
    private FrameLayout mContentFl;
    private ArrayAdapter<String> mTypeAdapter;
    private int mCurrentSelectPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initView();
    }

    private void initView() {
        mTypeLv = (ListView) findViewById(R.id.log_type);
        mContentFl = (FrameLayout) findViewById(R.id.log_container);

        mTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Constant.Type.TYPES) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                if (position == mCurrentSelectPosition) {
                    textView.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    textView.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
                return textView;
            }
        };
        mTypeLv.setAdapter(mTypeAdapter);
        mTypeLv.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            openFragment(position);
            mCurrentSelectPosition = position;
            mTypeAdapter.notifyDataSetChanged();
        });
        openFragment(0);
        mCurrentSelectPosition = 0;
    }

    private void openFragment(int position) {
        Fragment fragment = null;

        switch (position) {
            case Constant.Type.INDEX_TYPE_ALL: {
                if (mAllLogFragment == null) {
                    mAllLogFragment = new AllLogFragment();
                }
                fragment = mAllLogFragment;
                break;
            }
            case Constant.Type.INDEX_TYPE_DISPLAY: {
                if (mDisplayLogFragment == null) {
                    mDisplayLogFragment = new DisplayLogFragment();
                }
                fragment = mDisplayLogFragment;
                break;
            }
            case Constant.Type.INDEX_TYPE_APP_WAKE: {
                if (mAppWakeLogFragment == null) {
                    mAppWakeLogFragment = new AppWakeLogFragment();
                }
                fragment = mAppWakeLogFragment;
                break;
            }
            case Constant.Type.INDEX_TYPE_AUDIO: {
                if (mAudioLogFragment == null) {
                    mAudioLogFragment = new AudioLogFragment();
                }
                fragment = mAudioLogFragment;
                break;
            }
            case Constant.Type.INDEX_TYPE_SERVER_SEARCH: {
                // TODO: 2018/4/10
                break;
            }
            case Constant.Type.INDEX_TYPE_CONTENT: {
                if (mContentLogFragment == null) {
                    mContentLogFragment = new ContentLogFragment();
                }
                fragment = mContentLogFragment;
                break;
            }
            case Constant.Type.INDEX_TYPE_ORDER_DISTRIBUTION: {
                // TODO: 2018/4/10
                break;
            }
            case Constant.Type.INDEX_TYPE_HELPER: {
                if (mHelperLogFragment == null) {
                    mHelperLogFragment = new HelperLogFragment();
                }
                fragment = mHelperLogFragment;
                break;
            }
            case Constant.Type.INDEX_TYPE_SEMANTICS: {
                // TODO: 2018/4/10
                break;
            }
            case Constant.Type.INDEX_TYPE_SPEECH: {
                if (mSpeechLogFragment == null) {
                    mSpeechLogFragment = new SpeechLogFragment();
                }
                fragment = mSpeechLogFragment;
                break;
            }
            case Constant.Type.INDEX_TYPE_SYSTEM_WAKE: {
                if (mSystemWakeLogFragment == null) {
                    mSystemWakeLogFragment = new SystemWakeLogFragment();
                }
                fragment = mSystemWakeLogFragment;
                break;
            }
            default: {
                break;
            }
        }

        if (fragment == null) {
            return;
        }

        getFragmentManager().beginTransaction()
                .replace(mContentFl.getId(), fragment)
                .commit();
    }
}
