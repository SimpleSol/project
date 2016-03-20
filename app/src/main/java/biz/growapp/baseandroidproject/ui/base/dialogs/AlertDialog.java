package biz.growapp.baseandroidproject.ui.base.dialogs;

import android.content.Context;
import android.os.Bundle;

public class AlertDialog extends BaseDialog<AlertDialog.AlertDialogListener> {

    @Override
    protected void onPositiveButtonClicked() {
        super.onPositiveButtonClicked();
        getListener().onPositiveButtonClicked(getRequestCode(), getParams());
    }

    @Override
    protected void onNegativeButtonClicked() {
        super.onNegativeButtonClicked();
        getListener().onNegativeButtonClicked(getRequestCode(), getParams());
    }

    public interface AlertDialogListener {
        void onPositiveButtonClicked(int requestCode, Bundle params);

        void onNegativeButtonClicked(int requestCode, Bundle params);
    }

    public static class AlertDialogBuilder extends BaseDialogBuilder<AlertDialogBuilder> {

        public AlertDialogBuilder(Context context) {
            super(context);
        }

        public AlertDialog build() {
            return build(AlertDialog.class);
        }

    }
}
