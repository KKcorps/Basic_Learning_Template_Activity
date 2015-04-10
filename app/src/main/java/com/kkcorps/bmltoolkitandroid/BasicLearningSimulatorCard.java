package com.kkcorps.bmltoolkitandroid;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.kkcorps.bmltoolkitandroid.Utils.SimulatorUtils;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 23/3/15.
 */
public class BasicLearningSimulatorCard extends ActionBarActivity{
    public List<BasicLearningItem> infoList= new ArrayList<BasicLearningItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_learning_simulator_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("mLearning Simulator");
        setSupportActionBar(toolbar);

        RecyclerView recList = (RecyclerView) findViewById(R.id.recyclerView);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        for(int i=0;i<GlobalModelCollection.globalCollectionList.size();i++){
            infoList.add(GlobalModelCollection.globalCollectionList.get(i));

        }
        InfoAdapter infoAdapter = new InfoAdapter(infoList);
        recList.setAdapter(infoAdapter);



        final ImageView fabIconNew = new ImageView(this);
        fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_settings_wrench));

        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this)
                .setTheme(R.style.Base_Theme_AppCompat)
                .setContentView(fabIconNew)
                .setBackgroundDrawable(R.drawable.button_action_main)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        rLSubBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_subaction));

        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);

        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.camera));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.volume_down));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.film));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.undo));

        SubActionButton rlIcon1Button = rLSubBuilder.setContentView(rlIcon1).build();
        rlIcon1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimulatorUtils simulatorUtils = new SimulatorUtils();
                simulatorUtils.takeScreenshot(BasicLearningSimulatorCard.this,"jpg","100","Simulator_Screenshot");
                Log.i("Simulator","Screenshot Clicked");
            }
        });
        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons
        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(rlIcon1Button)
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
                .attachTo(rightLowerButton)
                .build();

        // Listen menu open and close events to animate the button content view
        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIconNew.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                fabIconNew.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general,menu);
        return super.onCreateOptionsMenu(menu);
    }
}


