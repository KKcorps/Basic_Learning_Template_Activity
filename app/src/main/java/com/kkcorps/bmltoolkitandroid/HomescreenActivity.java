package com.kkcorps.bmltoolkitandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by root on 22/3/15.
 */
public class HomescreenActivity extends ActionBarActivity implements View.OnTouchListener, View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_wdrawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setBackground(getResources().getDrawable(R.drawable.action_bar_home));
        toolbar.setTitle("HomeScreen");
        setSupportActionBar(toolbar);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.loadProject);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomescreenActivity.this, BasicLearningActivity.class);
                startActivity(intent);
            }
        });


    }

    public void a(Context ctx, File orgJar) {
        try {
            // create a tmp dir for extract content in unsigend.jar
            final File dir = ctx.getDir("tmp", Context.MODE_PRIVATE);
            InputStream in = ctx.getAssets().open("unsigned.apk");
            // do extract to tmp dir
            extract(in, dir);
            in.close();
            File distFile = ctx.getFileStreamPath("signed.apk");
            FileOutputStream fos = new FileOutputStream(distFile);
            // do sign
            TinySign.sign(dir, fos);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void extract(InputStream in, File dir) throws IOException {
        ZipInputStream zis = new ZipInputStream(in);
        for (ZipEntry e = zis.getNextEntry(); e != null; e = zis.getNextEntry()) {
            String name = e.getName();
            if (!e.isDirectory()) {
                FileOutputStream fos = new FileOutputStream(dir+"\\"+name);
                TinySign.copy(zis, fos);
                fos.close();
            }
        }
    }

    @Override
    public void onClick(View view) {
        Log.i("HomeScreen", "Unknown View clicked oustide");

        switch (view.getId()){
            case R.id.loadProject:
                    Intent intent = new Intent(HomescreenActivity.this, BasicLearningActivity.class);
                    startActivity(intent);
            default:
                Log.i("HomeScreen", "Unknown View clicked");
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.i("HomeScreen", "Unknown View clicked oustide");

        switch (view.getId()){
            case R.id.loadProject:
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(HomescreenActivity.this, BasicLearningActivity.class);
                    startActivity(intent);
                    return true;
                }
            default:
                Log.i("HomeScreen", "Unknown View clicked");
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general,menu);
        return true;
    }
}
