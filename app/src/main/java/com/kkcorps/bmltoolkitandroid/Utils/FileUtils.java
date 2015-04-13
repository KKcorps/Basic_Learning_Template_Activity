package com.kkcorps.bmltoolkitandroid.Utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by root on 11/4/15.
 */
public class FileUtils {

    public static String getPath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static File copyFileFromAsset(Context context, String assetPath, String FileName){
        try {
            File directory = new File(context.getCacheDir(),"/"+assetPath);
            directory.mkdir();
            File f = new File(context.getCacheDir()+"/"+assetPath,FileName);
            InputStream is = context.getAssets().open(assetPath+FileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(buffer);
            fos.close();
            return f;
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static URL UrlFromFile(File f){
        URL url = null;
        try {
            url = new URL("file://" + f.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public static String ImageToBase64(String pathToFile){

        Bitmap bm = BitmapFactory.decodeFile(pathToFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return encodedImage;

    }

    public static Bitmap Base64ToImage(String base64String){

        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByteImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByteImage;
    }
}
