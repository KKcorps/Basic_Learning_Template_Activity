package com.kkcorps.bmltoolkitandroid.BasicLearningTemplate;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.kkcorps.bmltoolkitandroid.GlobalModelCollection;
import com.kkcorps.bmltoolkitandroid.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

/**
 * Created by root on 21/3/15.
 */
public class BasicLearningSimulatorList extends ActionBarActivity{

    FloatingActionMenu rightLowerMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_learning_simulator_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        String [] itemTitles = new String[GlobalModelCollection.globalCollectionList.size()];
        for(int i=0;i<GlobalModelCollection.globalCollectionList.size();i++){
            itemTitles[i] = GlobalModelCollection.globalCollectionList.get(i).getTitle();
        }
        Adapter adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.list_child_view, itemTitles);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter((ListAdapter) adapter);

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

        rlIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Sim","Clicked");
            }
        });

        SubActionButton rlIcon1Button = rLSubBuilder.setContentView(null).build();

        rlIcon1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Sim","Clicked2");
            }
        });
        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons
        rightLowerMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(rlIcon1Button)
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
