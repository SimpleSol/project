package biz.growapp.helpers;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import biz.growapp.baseandroidproject.R;
import biz.growapp.utils.FileUtils;

public class PhotoPicker {
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMATTER = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        }
    };

    static final int REQUEST_IMAGE_CAPTURE = 254;
    static final int REQUEST_PICK_GALLERY = 255;
    public static final String FILE_EXTENSION = ".jpg";

    private final WeakReference<Activity> activity;
    private final WeakReference<OnPhotoPickerListener> listener;
    private Fragment fragment;
    String mCurrentPhotoPath;

    public PhotoPicker(Activity activity, OnPhotoPickerListener listener) {
        this.activity = new WeakReference<>(activity);
        this.listener = new WeakReference<>(listener);
    }

    /**
     * Sets the fragment that contains this control. This allows the picker to be embedded inside a
     * Fragment, and will allow the fragment to receive the
     * {@link Fragment#onActivityResult(int, int, Intent) onActivityResult}
     * call rather than the Activity.
     *
     * @param fragment the android.support.v4.app.Fragment that contains this control
     */
    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    /**
     * Start activity for result to capture image from camera
     *
     * @return true if intent successfully dispatched, false - otherwise
     */
    public boolean requestCameraIntent() {
        final Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            // TODO: 06.02.2016 maybe return errorCodes?
            return false;
        }
        if (photoFile != null) {
            imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            if (imageCaptureIntent.resolveActivity(activity.get().getPackageManager()) == null) {
                return false;
            }
            startActivityForResult(imageCaptureIntent, REQUEST_IMAGE_CAPTURE);
            return true;
        }
        return false;
    }

    public boolean requestGalleryIntent() {
        final Intent selectIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (selectIntent.resolveActivity(activity.get().getPackageManager()) != null) {
            startActivityForResult(selectIntent, REQUEST_PICK_GALLERY);
            return true;
        } else {
            return false;
        }
    }

    private void startActivityForResult(final Intent intent, final int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            final Activity activity = this.activity.get();
            if (activity != null) {
                activity.startActivityForResult(intent, requestCode);
            }
        }
    }

    private String generateFileName() {
        return "JPEG_" + DATE_FORMATTER.get().format(new Date());
    }

    /**
     * Create a temp file, which will be user for saving image
     *
     * @return file
     * @throws IOException if any error has been occurred
     */
    private File createImageFile() throws IOException {
        final File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), activity.get().getString(R.string.app_name));
        //create dirs if not exist
        storageDir.mkdirs();
        final File imageFile = File.createTempFile(generateFileName(), FILE_EXTENSION, storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    private void galleryAddPic() {
        MediaScannerConnection.scanFile(activity.get(),
                new String[]{mCurrentPhotoPath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalRfStorage", "-> uri=" + uri);
                    }
                });
    }

    public boolean onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return false;
        }
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE: {
                galleryAddPic();
                final OnPhotoPickerListener pickerListener = listener.get();
                if (pickerListener != null) {
                    pickerListener.onPhotoPicked(new File(mCurrentPhotoPath));
                }
            }
            return true;
            case REQUEST_PICK_GALLERY: {
                try {
                    new SaveFileTask(listener, createImageFile(), activity).execute(data.getData());
                } catch (IOException e) {
                    // TODO: 19.02.16 call faile
                }
            }
            return true;
        }
        return false;
    }

    public interface OnPhotoPickerListener {
        void onPhotoPicked(File photoFile);
    }

    static class SaveFileTask extends AsyncTask<Uri, Void, File> {
        private static final String TAG = SaveFileTask.class.getSimpleName();

        private final WeakReference<OnPhotoPickerListener> listener;
        private final WeakReference<Activity> activity;
        private File destination;

        public SaveFileTask(WeakReference<OnPhotoPickerListener> listener, File imageFile, WeakReference<Activity> activity) {
            this.listener = listener;
            this.destination = imageFile;
            this.activity = activity;
        }

        @Override
        protected File doInBackground(Uri... params) {
            FileOutputStream out = null;
            try {
                InputStream inputStream = activity.get().getContentResolver().openInputStream(params[0]);
                out = new FileOutputStream(destination);
                if (inputStream != null && FileUtils.copyStream(inputStream,
                        new BufferedOutputStream(out))) {
                    return destination;
                } else {
                    return null;
                }
            } catch (FileNotFoundException e) {
                Log.e(TAG, "File not found while coping stream", e);
                return null;
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(File file) {
            final OnPhotoPickerListener pickerListener = listener.get();
            if (pickerListener != null) {
                pickerListener.onPhotoPicked(file);
            }
        }
    }
}
