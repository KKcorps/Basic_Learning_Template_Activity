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
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.logging.LoggerManager;
import kellinwood.logging.android.AndroidLogger;
import kellinwood.logging.android.AndroidLoggerFactory;
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


        /*
        try {
            //Process p = Runtime.getRuntime().exec("export ANDROID_DATA=/sdcard");

            String[] cmds = new String[]{"export ANDROID_DATA=/sdcard/dalvik-cache","bash -c 'echo ANDROID_DATA' "};
            Process p = Runtime.getRuntime().exec(cmds);
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            Log.i("Home",total.toString());
            Log.i("Random",System.getenv("ANDROID_DATA"));
        }catch (Exception e){
            Log.i("Home","Error: "+e.toString());
        }*/

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
