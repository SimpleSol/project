package biz.growapp.utils;


import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    private FileUtils() {
        throw new IllegalStateException("No instances!");
    }

    /**
     * Copy bytes from input stream to output <br/>
     * <u>This method don't close output stream!</u>
     *
     * @param input  copy from
     * @param output copy tщ
     * @return {@code true}, if bytes copied successfully and {@code false} - otherwise
     */
    public static boolean copyStream(@NonNull InputStream input, @NonNull OutputStream output) {
        try {
            byte data[] = new byte[1024];
            int count;
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }
            output.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Return the size of a directory in bytes
     */
    public static long getDirectorySize(File dir) {
        if (dir.exists()) {
            long result = 0;
            File[] fileList = dir.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // Recursive call if it's a directory
                if (fileList[i].isDirectory()) {
                    result += getDirectorySize(fileList[i]);
                } else {
                    // Sum the file size in bytes
                    result += fileList[i].length();
                }
            }
            return result; // return the file size
        }
        return 0;
    }

    /**
     * Delete file or directory recursive
     *
     * @param fileOrDirectory file or directory, which need to delete
     * @return {@code true} if all files was successfully deleted, {@code false} - otherwise
     */
    public static boolean delete(File fileOrDirectory) {
        boolean isSuccess = true;
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                isSuccess &= delete(child);
            }
        }
        return isSuccess & fileOrDirectory.delete();
    }

    /**
     * Delete all files in directory, but retain directory
     *
     * @param directory which child files will be deleted
     * @return {@code true} if all files was successfully deleted, {@code false} - otherwise
     */
    public static boolean cleanDirectory(File directory) {
        if (!directory.isDirectory()) {
            return false;
        }
        boolean isSuccess = true;
        for (File child : directory.listFiles()) {
            isSuccess &= delete(child);
        }
        return isSuccess;
    }

}
