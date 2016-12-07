package biz.growapp.base;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.File;

import biz.growapp.helpers.PhotoPicker;
import biz.growapp.base.loading.BaseAppLoadingActivity;
import biz.growapp.utils.BitmapUtils;
import biz.growapp.utils.DisplayUtils;
import biz.growapp.utils.UriUtils;

public abstract class BasePhotoRequestActivity extends BaseAppLoadingActivity implements PhotoPicker.OnPhotoPickerListener {
    protected static final int REMOVE_PHOTO = 0;
    protected static final int PICK_PHOTO = 1;
    protected static final int PICK_GALLERY = 2;

    private PhotoPicker photoPicker;
    private File currentPhotoFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoPicker = new PhotoPicker(this, this, savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!photoPicker.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onPhotoPicked(File photoFile) {
        currentPhotoFile = photoFile;
    }

    @Nullable
    public File getPhotoFile() {
        return currentPhotoFile;
    }

    @Nullable
    public File getCompressedPhotoFile(int width, int height) {
        if (currentPhotoFile == null) {
            return null;
        }
        final String path = UriUtils.getPath(this, Uri.fromFile(currentPhotoFile));
        final File compressedFile = BitmapUtils.getCompressFileFromGallery(this, path, width, height);
        return BitmapUtils.rotateToCorrectOrientation(this, compressedFile, path);
    }

    public void performClear() {
        currentPhotoFile = null;
    }

    public void requestCamera() {
        DisplayUtils.hideSoftKeyboard(this);
        if (!photoPicker.requestCameraIntent()) {
            // TODO: 20.02.16 show alert
//                    new AlertDialog.AlertDialogBuilder(this)
//                            .setMessage().setPositiveButtonText(android.R.string.ok)
//                            .build().show(getSupportFragmentManager(), null);
        }
    }

    public void requestGallery() {
        DisplayUtils.hideSoftKeyboard(this);
        // TODO: 20.02.16 show alert
        photoPicker.requestGalleryIntent();
    }
}
