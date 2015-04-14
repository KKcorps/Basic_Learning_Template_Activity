package com.kkcorps.bmltoolkitandroid.FlashCardTemplate;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.gc.materialdesign.utils.Utils;
import com.gc.materialdesign.views.ButtonRectangle;
import com.kkcorps.bmltoolkitandroid.Constants;
import com.kkcorps.bmltoolkitandroid.GlobalModelCollection;
import com.kkcorps.bmltoolkitandroid.R;
import com.kkcorps.bmltoolkitandroid.Utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 12/4/15.
 */
public class FlashCardEditor extends ActionBarActivity{

    EditText questionView, hintView , answerView, authorView, numberOfItemsView , collectionTitleView;
    String question, hint, answer, author, numberOfItems, collectionTitle, base64Image;
    ImageView imageView;
    ButtonRectangle OkButton;
    private Uri outputFileUri;
    int PICTURE_REQUEST_CODE = 101;
    int clickIndex;
    boolean isInEditingMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Flash Card Editor");
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.i("FlashCardActivity","Image View Touched");
                openImageIntent();
                return false;
            }
        });

        questionView = (EditText) findViewById(R.id.question);
        answerView = (EditText) findViewById(R.id.answer);
        hintView = (EditText) findViewById(R.id.hint);
        authorView = (EditText) findViewById(R.id.author);
        numberOfItemsView = (EditText) findViewById(R.id.numberOfItems);
        collectionTitleView = (EditText) findViewById(R.id.collectionTitle);

        if(getIntent().getStringExtra("requestCode").equals(String.valueOf(Constants.EDIT_REQUEST_CODE))) {
            isInEditingMode = true;
            clickIndex = getIntent().getIntExtra("clickIndex", 0);
            FlashCardItem item = (FlashCardItem) GlobalModelCollection.globalCollectionList.get(clickIndex);
            questionView.setText(item.getQuestion());
            answerView.setText(item.getAnswer());
            hintView.setText(item.getHint());
            authorView.setText(item.getAuthor());
            numberOfItemsView.setText(item.getNumberOfItems());
            collectionTitleView.setText(item.getCollectionTitle());
            imageView.setImageBitmap(FileUtils.Base64ToImage(item.getBase64Image()));

        }

        OkButton = (ButtonRectangle) findViewById(R.id.ok_button);
        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(base64Image==null || base64Image.isEmpty()){
                    Toast.makeText(FlashCardEditor.this,"No Image Selected.Please select a image to continue!", Toast.LENGTH_SHORT).show();
                    return;
                }
                question = questionView.getText().toString();
                answer = answerView.getText().toString();
                hint = hintView.getText().toString();
                author = authorView.getText().toString();
                numberOfItems = numberOfItemsView.getText().toString();
                collectionTitle = collectionTitleView.getText().toString();
                FlashCardItem flashCardItem = new FlashCardItem(question,answer,hint,author,numberOfItems,collectionTitle,base64Image);
                if(isInEditingMode) {
                    GlobalModelCollection.globalCollectionList.set(clickIndex,flashCardItem);
                }else{
                    GlobalModelCollection.globalCollectionList.add(flashCardItem);
                }

                setResult(Activity.RESULT_OK);
                finish();
                }
        });
    }

    private void openImageIntent() {

        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "bmb" + File.separator);
        root.mkdirs();
        final String fname = "TestImageCapture_"+String.valueOf(System.currentTimeMillis())+".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        startActivityForResult(chooserIntent, PICTURE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICTURE_REQUEST_CODE) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    Log.i("FlashCardEditor","Yes it's a Camera click!");
                    selectedImageUri = outputFileUri;
                    imageView.setImageDrawable(null);
                    imageView.setImageURI(selectedImageUri);
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                    imageView.setImageDrawable(null);
                    imageView.setImageURI(selectedImageUri);
                }
                base64Image = FileUtils.ImageToBase64(FileUtils.getPath(this,selectedImageUri));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
