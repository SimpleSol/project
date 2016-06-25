package biz.growapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;

public final class VectorUtils {
    private VectorUtils() {
        throw new IllegalStateException("No instances!");
    }

    public static Drawable getVectorDrawable(@NonNull Context context, @DrawableRes int vectorId) {
        return VectorDrawableCompat.create(context.getResources(), vectorId, context.getTheme());
    }

}
