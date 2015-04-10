package com.kkcorps.bmltoolkitandroid.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by root on 10/4/15.
 */
public class SimulatorUtils {

    private Bitmap getBitmap(Activity activity) {
        View view = activity.getWindow().getDecorView().getRootView();
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);

// Get current theme to know which background to use
        final Resources.Theme theme = activity.getTheme();
        final TypedArray ta = theme
                .obtainStyledAttributes(new int[] { android.R.attr.windowBackground });
        final int res = ta.getResourceId(0, 0);
        final Drawable background = activity.getResources().getDrawable(res);

// Draw background
        background.draw(canvas);

// Draw views
        view.draw(canvas);
        //bitmap = Bitmap.createBitmap(view.getDrawingCache());

        return bitmap;
    }

    public boolean takeScreenshot(final Activity activity, String...args){
        final String format = (String) args[0];
        final Integer quality = Integer.parseInt(args[1]);
        final String fileName = (String)args[2];


        Runnable runOnUi = new Runnable() {
            @Override
            public void run() {

                try {
                    if (format.equals("png") || format.equals("jpg")) {
                        Bitmap bitmap = getBitmap(activity);
                        File folder = new File(Environment.getExternalStorageDirectory(), "Pictures");
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }

                        File f = new File(folder, fileName + "." + format);

                        FileOutputStream fos = new FileOutputStream(f);
                        if (format.equals("png")) {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        }
                        if (format.equals("jpg")) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, quality == null ? 100 : quality, fos);
                        }
                    } else {

                    }

                } catch (IOException e) {
                    //callbackContext.error(e.getMessage());
                    Log.i("SimulatorUtils",e.getMessage());

                } catch (Exception e) {
                    //callbackContext.error(e.getMessage());
                    Log.i("SimulatorUtils",e.getMessage());

                }
            }
        };
        runOnUi.run();

        return true;
    }
}
