package biz.growapp.base.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class SelectItemDialog extends BaseDialog<SelectItemDialog.OnSelectItemDialogListener> {
    static final String PARAM_ITEMS = "PARAM_ITEMS";
    static final String PARAM_ITEMS_ID = "PARAM_ITEMS_ID";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = buildDialog();
        final Bundle arguments = getArguments();
        final DialogInterface.OnClickListener onItemClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getListener().onItemSelected(getRequestCode(), getParams(), which);
            }
        };
        final int itemsId = arguments.getInt(PARAM_ITEMS_ID, View.NO_ID);
        if (itemsId != View.NO_ID) {
            builder.setItems(itemsId, onItemClickListener);
        } else {
            builder.setItems(arguments.getCharSequenceArray(PARAM_ITEMS), onItemClickListener);
        }
        return builder.create();
    }

    public interface OnSelectItemDialogListener {
        void onItemSelected(int requestCode, Bundle params, int position);
    }

    public static class SelectItemDialogBuilder extends BaseDialogBuilder<SelectItemDialogBuilder> {

        public SelectItemDialogBuilder(Context context) {
            super(context);
        }

        public SelectItemDialogBuilder setItems(CharSequence[] items) {
            args.putCharSequenceArray(PARAM_ITEMS, items);
            return this;
        }

        public SelectItemDialogBuilder setItems(@ArrayRes int itemsId) {
            args.putInt(PARAM_ITEMS_ID, itemsId);
            return this;
        }

        public SelectItemDialog build() {
            return build(SelectItemDialog.class);
        }
    }
}
