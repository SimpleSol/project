package biz.growapp.utils;


import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    public static void writeInputStreamToFile(InputStream inputStream, File destination) {
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                fo.write(buffer, 0, read);
            }
        } catch (FileNotFoundException fileNotFoundE) {
            Log.e(TAG, "Error: Destination file not found", fileNotFoundE);
        } catch (IOException ioE) {
            Log.e(TAG, "Error: IOException", ioE);
        } finally {
            try {
                if (fo != null) {
                    fo.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

}
