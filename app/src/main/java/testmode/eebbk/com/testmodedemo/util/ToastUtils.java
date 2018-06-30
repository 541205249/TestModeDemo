package testmode.eebbk.com.testmodedemo.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

public class ToastUtils {

    private volatile static ToastUtils sInstance;
    private final Context mContext;
    private Toast mToast;

    private ToastUtils(Context context) {
        mContext = context.getApplicationContext();
    }

    public static ToastUtils getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ToastUtils.class) {
                if (sInstance == null) {
                    sInstance = new ToastUtils(context);
                }
            }
        }
        return sInstance;
    }


    /**
     * 显示一个短toast
     *
     * @param text toast的文本
     */
    public Toast s(String text) {
        showToast(text, Toast.LENGTH_SHORT);
        return mToast;
    }

    /**
     * 显示一个短toast
     *
     * @param textId toast的文本的ResourcesId
     */
    public Toast s(@StringRes int textId) {
        String text = mContext.getResources().getString(textId);
        showToast(text, Toast.LENGTH_SHORT);
        return mToast;
    }


    /**
     * 显示一个长toast
     *
     * @param text toast的文本
     */
    public Toast l(String text) {
        showToast(text, Toast.LENGTH_LONG);
        return mToast;
    }

    /**
     * 显示一个长toast
     *
     * @param textId toast的文本的ResourcesId
     */

    public Toast l(@StringRes int textId) {
        String text = mContext.getResources().getString(textId);
        showToast(text, Toast.LENGTH_LONG);
        return mToast;
    }


    // 由于主题商店更换字体的缘故, 每次toast字体可能不一样, 需要每次重新构建toast
    private void showToast(final String text, final int duration) {
        this.cancel();

        if (ThreadUtils.isMainThread()) {
            mToast = newToast(text, duration);
            mToast.show();
        } else {
            ThreadUtils.runOnMainThread(() -> {
                mToast = newToast(text, duration);
                mToast.show();
            });
        }
    }

    private Toast newToast(String text, int duration) {
        return Toast.makeText(mContext, text, duration);
    }

    /***
     * 取消toast
     */
    public void cancel() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    @Deprecated
    public void cancle() {
        this.cancel();
    }
}
