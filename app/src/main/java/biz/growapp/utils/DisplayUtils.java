package biz.growapp.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public final class DisplayUtils {
    private DisplayUtils() {
    }

    public static void hideSoftKeyboard(@NonNull final Activity activity) {
        final View view = activity.getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static int dpToPx(final Context context, final int dp) {
        return (int) dpToPxF(context, dp);
    }

    public static int spToPx(final Context context, final int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static float dpToPxF(final Context context, final float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = wm.getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics;
    }
}
