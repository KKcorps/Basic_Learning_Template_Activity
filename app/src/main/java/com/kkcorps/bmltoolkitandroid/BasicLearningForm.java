package com.kkcorps.bmltoolkitandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by root on 8/3/15.
 */
public class BasicLearningForm extends ActionBarActivity {
    public static EditText titleTextView, descriptionTextView, authorTextView, collectionTitleTextView, numberOfItemsTextView;
    public static Button okButton;
    public int REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_learning_form);
        getSupportActionBar().setTitle("Item Editor");

        titleTextView = (EditText) findViewById(R.id.editText);
        descriptionTextView = (EditText) findViewById(R.id.editText2);
        authorTextView = (EditText) findViewById(R.id.editText3);
        collectionTitleTextView = (EditText) findViewById(R.id.editText4);
        numberOfItemsTextView = (EditText) findViewById(R.id.editText5);

        Log.i("BasicLearningForm","Request Code: "+getIntent().getStringExtra("requestCode"));

        if(getIntent().getStringExtra("requestCode").equals(String.valueOf(Constants.EDIT_REQUEST_CODE))){
            int clickIndex = getIntent().getIntExtra("clickIndex",0);
            Log.i("BasicLearningForm","Click Index is: "+String.valueOf(clickIndex));
            BasicLearningItem clickedItem = GlobalModelCollection.globalCollectionList.get(clickIndex);
            titleTextView.setText(clickedItem.getTitle());
            descriptionTextView.setText(clickedItem.getDescription());
            authorTextView.setText(clickedItem.getAuthor());
            collectionTitleTextView.setText(clickedItem.getCollectionTitle());
            numberOfItemsTextView.setText(clickedItem.getNumberOfItems());

        }
        okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", titleTextView.getText().toString());
                resultIntent.putExtra("description", descriptionTextView.getText().toString());
                resultIntent.putExtra("author", authorTextView.getText().toString());
                resultIntent.putExtra("collectionTitle", collectionTitleTextView.getText().toString());
                resultIntent.putExtra("numberOfItems", numberOfItemsTextView.getText().toString());

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

