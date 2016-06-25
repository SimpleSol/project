package biz.growapp;

import android.content.Context;
import android.support.annotation.NonNull;

public final class Navigator {

    private Navigator() {
        throw new IllegalStateException("Not today!");
    }

    public static void navigateToSome(@NonNull Context context) {
//        context.startActivity(new Intent(context, SomeActivity.class));
    }
}
