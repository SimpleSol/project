package biz.growapp.utils.view;


import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.TextView;

public final class TextViewUtils {
    private final static int LEFT = 0;
    private final static int TOP = 1;
    private final static int RIGHT = 2;
    private final static int BOTTOM = 3;

    private TextViewUtils() {
        throw new IllegalStateException("No instances!");
    }

    public static void setDrawableLeft(@NonNull TextView textView, @NonNull Drawable drawable) {
        final Drawable[] drawables = textView.getCompoundDrawables();
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawables[TOP], drawables[RIGHT], drawables[BOTTOM]);
    }
}
