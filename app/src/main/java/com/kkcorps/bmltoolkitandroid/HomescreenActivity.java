package com.kkcorps.bmltoolkitandroid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kkcorps.bmltoolkitandroid.Utils.XmlParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by root on 22/3/15.
 */
public class HomescreenActivity extends ActionBarActivity{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_with_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setBackground(getResources().getDrawable(R.drawable.action_bar_home));
        toolbar.setTitle("HomeScreen");
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.home_drawer);
        drawerToggle = new ActionBarDrawerToggle(HomescreenActivity.this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);
        //create buildmLearn Directory in Sdcard for storing Apks
        File f = new File(Constants.DATA_BASE_DIRECTORY);
        f.mkdir();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.loadProject);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //XmlParser.readXML("assets/info_content.xml");
                loadProject(Constants.PROJECT_NAME_TEMP);
                Intent intent = new Intent(HomescreenActivity.this, BasicLearningActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout startProjectlinearLayout = (LinearLayout) findViewById(R.id.startProject);
        startProjectlinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //XmlParser.readXML("assets/info_content.xml");
                GlobalModelCollection.globalCollectionList = new ArrayList<BasicLearningItem>();
                Intent intent = new Intent(HomescreenActivity.this, BasicLearningActivity.class);
                //openApp(HomescreenActivity.this, "com.kkcorps.quiztemplate");
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

    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();

        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                //return false;
                throw new PackageManager.NameNotFoundException();

            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadProject(String ProjectName){
        try{
            //File projectFile = new File(getFilesDir()+"/"+ProjectName);
            XmlParser.readXML(Constants.DATA_BASE_DIRECTORY+"assets/"+ProjectName);

        }catch (RuntimeException f){
            Toast.makeText(this,"No Projects Found in "+Constants.DATA_BASE_DIRECTORY+"assets/"+ProjectName,Toast.LENGTH_SHORT).show();
            f.printStackTrace();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.START| Gravity.LEFT)){
            drawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general,menu);
        return true;
    }
}
