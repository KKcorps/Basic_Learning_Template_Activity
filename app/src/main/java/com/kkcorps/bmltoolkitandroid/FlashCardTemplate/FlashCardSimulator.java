package com.kkcorps.bmltoolkitandroid.FlashCardTemplate;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.kkcorps.bmltoolkitandroid.GlobalModelCollection;
import com.kkcorps.bmltoolkitandroid.R;
import com.kkcorps.bmltoolkitandroid.Utils.FileUtils;
import com.kkcorps.bmltoolkitandroid.Utils.SimulatorUtils;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

/**
 * Created by root on 14/4/15.
 */
public class FlashCardSimulator extends  ActionBarActivity{
    ImageView questionView;
    ButtonRectangle previous,next,flip;
    TextView questionNumber,answerView;
    int currentIndex=0, numberOfQuestions;
    boolean isAnswerVisible = false;
    RelativeLayout rootLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_simulator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Flash Card Simulator");
        setSupportActionBar(toolbar);

        numberOfQuestions = GlobalModelCollection.size();

        questionNumber = (TextView) findViewById(R.id.questionNumber);
        questionView = (ImageView) findViewById(R.id.question);
        answerView = (TextView) findViewById(R.id.answer);
        previous = (ButtonRectangle) findViewById(R.id.previousButton);
        next  = (ButtonRectangle) findViewById(R.id.nextButton);
        flip = (ButtonRectangle) findViewById(R.id.flipButton);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateQuestion(currentIndex-1);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateQuestion(currentIndex+1);
            }
        });
        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isAnswerVisible){
                    questionView.setVisibility(View.GONE);
                    answerView.setVisibility(View.VISIBLE);
                    isAnswerVisible = true;
                }else {
                    questionView.setVisibility(View.VISIBLE);
                    answerView.setVisibility(View.GONE);
                    isAnswerVisible = false;
                }
            }
        });

        if(numberOfQuestions>0){
            populateQuestion(0);
        }

        //Floating Action Button
        final ImageView fabIconNew = new ImageView(this);
        FloatingActionButton.LayoutParams params = new FloatingActionButton.LayoutParams(
                80,
                80
        );
        params.setMargins(0,0,0,45);

        fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_settings_wrench));

        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this)
                .setTheme(R.style.Base_Theme_AppCompat)
                .setContentView(fabIconNew)
                .setBackgroundDrawable(R.drawable.button_action_main)
                .setLayoutParams(params)
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
                simulatorUtils.takeScreenshot(FlashCardSimulator.this,"jpg","100","Simulator_Screenshot");
                Log.i("Simulator", "Screenshot Clicked");
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

    private void populateQuestion(int index) {
        if(index>=0 && index<numberOfQuestions) {
            FlashCardItem flashcard = (FlashCardItem) GlobalModelCollection.get(index);
            Bitmap questionImage = FileUtils.Base64ToImage(flashcard.getBase64Image());
            questionView.setImageBitmap(questionImage);
            questionNumber.setText("Question #" + String.valueOf(index+1) + " of " + String.valueOf(numberOfQuestions));
            answerView.setText(flashcard.getAnswer());
            currentIndex = index;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
