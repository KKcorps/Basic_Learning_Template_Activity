package com.kkcorps.bmltoolkitandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by root on 8/3/15.
 */
public class BasicLearningForm extends Activity {
    public static TextView titleTextView, descriptionTextView, authorTextView, collectionTitleTextView, numberOfItemsTextView;
    public static Button okButton;
    public int REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_learning_form);
        getActionBar().setTitle("Item Editor");

        titleTextView = (TextView) findViewById(R.id.editText);
        descriptionTextView = (TextView) findViewById(R.id.editText2);
        authorTextView = (TextView) findViewById(R.id.editText3);
        collectionTitleTextView = (TextView) findViewById(R.id.editText4);
        numberOfItemsTextView = (TextView) findViewById(R.id.editText5);
        okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", titleTextView.getText());
                resultIntent.putExtra("description", descriptionTextView.getText());
                resultIntent.putExtra("author", authorTextView.getText());
                resultIntent.putExtra("collectionTitle", collectionTitleTextView.getText());
                resultIntent.putExtra("numberOfItems", numberOfItemsTextView.getText());

                setResult(Activity.RESULT_OK,resultIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

