package biz.growapp.utils.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;

public final class ViewUtils {

    private ViewUtils() {
        throw new IllegalStateException("Not today!");
    }

    public static void tintBackgroundRes(@NonNull Button button, @ColorRes int colorId) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            ((AppCompatButton) button).setSupportBackgroundTintList(getColorStateList(button.getContext(), colorId));
        } else {
            tintBackgroundRes((View) button, colorId);
        }
    }

    public static void tintBackgroundRes(@NonNull View view, @ColorRes int colorId) {
        ViewCompat.setBackgroundTintList(view, getColorStateList(view.getContext(), colorId));
    }

    public static void tintBackground(@NonNull Button button, @ColorInt int color) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            ((AppCompatButton) button).setSupportBackgroundTintList(ColorStateList.valueOf(color));
        } else {
            tintBackground((View) button, color);
        }
    }

    public static void tintBackground(@NonNull View view, @ColorInt int color) {
        ViewCompat.setBackgroundTintList(view, ColorStateList.valueOf(color));
    }

    @NonNull
    private static ColorStateList getColorStateList(
            @NonNull Context context, @ColorRes int colorId) {
        return ColorStateList.valueOf(ContextCompat.getColor(context, colorId));
    }

}
