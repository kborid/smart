package com.thunisoft.common.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {

    /**
     * sd卡根目录
     */
    public final static String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();

    /**
     * bitmap存文件
     *
     * @param filePath
     * @param bitmap
     */
    public static void saveBitmap2File(String filePath, Bitmap bitmap) {
        File file = new File(filePath);
        File fileDirs = new File(file.getParent());
        if (!fileDirs.exists()) {
            fileDirs.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Bitmap outB = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(outB);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            outB.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * base64转bitmap
     *
     * @param base64Str
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Str) {
        byte[] bytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * bitmap转base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        if (null != bitmap) {
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (baos != null) {
                        baos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
