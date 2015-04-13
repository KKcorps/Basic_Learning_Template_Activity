package com.kkcorps.bmltoolkitandroid.BasicLearningTemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.gc.materialdesign.views.ButtonRectangle;
import com.kkcorps.bmltoolkitandroid.Constants;
import com.kkcorps.bmltoolkitandroid.GlobalModelCollection;
import com.kkcorps.bmltoolkitandroid.R;

/**
 * Created by root on 8/3/15.
 */
public class BasicLearningEditor extends ActionBarActivity {
    public static EditText titleTextView, descriptionTextView, authorTextView, collectionTitleTextView, numberOfItemsTextView;
    String title, description, author, collectionTitle, numberOfItems;

    public static ButtonRectangle okButton;
    public int clickIndex;
    boolean isInEditingMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_learning_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Item Editor");

        titleTextView = (EditText) findViewById(R.id.editText);
        descriptionTextView = (EditText) findViewById(R.id.editText2);
        authorTextView = (EditText) findViewById(R.id.editText3);
        collectionTitleTextView = (EditText) findViewById(R.id.editText4);
        numberOfItemsTextView = (EditText) findViewById(R.id.editText5);

        Log.i("BasicLearningForm","Request Code: "+getIntent().getStringExtra("requestCode"));

        if(getIntent().getStringExtra("requestCode").equals(String.valueOf(Constants.EDIT_REQUEST_CODE))){
            isInEditingMode = true;
            clickIndex = getIntent().getIntExtra("clickIndex",0);
            Log.i("BasicLearningForm","Click Index is: "+String.valueOf(clickIndex));
            BasicLearningItem clickedItem = (BasicLearningItem) GlobalModelCollection.globalCollectionList.get(clickIndex);
            titleTextView.setText(clickedItem.getTitle());
            descriptionTextView.setText(clickedItem.getDescription());
            authorTextView.setText(clickedItem.getAuthor());
            collectionTitleTextView.setText(clickedItem.getCollectionTitle());
            numberOfItemsTextView.setText(clickedItem.getNumberOfItems());

        }
        okButton = (ButtonRectangle) findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleTextView.getText().toString();
                description = descriptionTextView.getText().toString();
                author = authorTextView.getText().toString();
                numberOfItems = numberOfItemsTextView.getText().toString();
                collectionTitle = collectionTitleTextView.getText().toString();
                BasicLearningItem addItem = new BasicLearningItem(title,description,author,numberOfItems,collectionTitle);
                if (isInEditingMode){
                    GlobalModelCollection.globalCollectionList.set(clickIndex,addItem);
                }else {
                    GlobalModelCollection.globalCollectionList.add(addItem);
                }
                /*Intent resultIntent = new Intent();
                resultIntent.putExtra("title", titleTextView.getText().toString());
                resultIntent.putExtra("description", descriptionTextView.getText().toString());
                resultIntent.putExtra("author", authorTextView.getText().toString());
                resultIntent.putExtra("collectionTitle", collectionTitleTextView.getText().toString());
                resultIntent.putExtra("numberOfItems", numberOfItemsTextView.getText().toString());*/

                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

