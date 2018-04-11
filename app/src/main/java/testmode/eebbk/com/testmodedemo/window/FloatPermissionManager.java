package testmode.eebbk.com.testmodedemo.window;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FloatPermissionManager {
    private static final String TAG = "FloatPermissionManager";

    private static volatile FloatPermissionManager instance;

    private Dialog dialog;

    public static FloatPermissionManager getInstance() {
        if (instance == null) {
            synchronized (FloatPermissionManager.class) {
                if (instance == null) {
                    instance = new FloatPermissionManager();
                }
            }
        }
        return instance;
    }

    public boolean applyFloatWindow(Activity activity) {
        if (checkPermission(activity)) {
            return true;
        } else {
            applyPermission(activity);
            return false;
        }
    }

    private boolean checkPermission(Context context) {
        return commonROMPermissionCheck(context);
    }

    private boolean commonROMPermissionCheck(Context context) {
        Boolean result = true;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class clazz = Settings.class;
                Method canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                result = (Boolean) canDrawOverlays.invoke(null, context);
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        return result;
    }

    private void applyPermission(Context context) {
        commonROMPermissionApply(context);
    }

    /**
     * 通用 rom 权限申请
     */
    private void commonROMPermissionApply(final Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            showConfirmDialog(context, new OnConfirmResult() {
                @Override
                public void confirmResult(boolean confirm) {
                    if (confirm) {
                        try {
                            Class clazz = Settings.class;
                            Field field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");

                            Intent intent = new Intent(field.get(null).toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.parse("package:" + context.getPackageName()));
                            context.startActivity(intent);
                        } catch (Exception e) {
                            Log.e(TAG, Log.getStackTraceString(e));
                        }
                    } else {
                        Log.d(TAG, "user manually refuse OVERLAY_PERMISSION");
                    }
                }
            });
        }
    }

    private void showConfirmDialog(Context context, OnConfirmResult result) {
        showConfirmDialog(context, "您的手机没有授予悬浮窗权限，请开启后再试", result);
    }

    private void showConfirmDialog(Context context, String message, final OnConfirmResult result) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new AlertDialog.Builder(context).setCancelable(true).setTitle("")
                .setMessage(message)
                .setPositiveButton("现在去开启",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirmResult(true);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("暂不开启",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirmResult(false);
                                dialog.dismiss();
                            }
                        }).create();
        dialog.show();
    }

    private interface OnConfirmResult {
        void confirmResult(boolean confirm);
    }
}
