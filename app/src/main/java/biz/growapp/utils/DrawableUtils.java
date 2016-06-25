package biz.growapp.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;

public final class DrawableUtils {
    private DrawableUtils() {
    }

    public static Drawable getTintedDrawable(@NonNull Drawable drawable, @ColorInt int color) {
        drawable = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTint(drawable, color);
        return drawable;
    }

    public static Drawable getTintedDrawable(@NonNull Drawable drawable, @NonNull ColorStateList colors) {
        drawable = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintList(drawable, colors);
        return drawable;
    }

}
