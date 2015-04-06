package com.kkcorps.bmltoolkitandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.kkcorps.bmltoolkitandroid.ApkParser.BasicLearningGenerator;
import com.mobeta.android.dslv.DragSortItemView;
import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.SimpleDragSortCursorAdapter;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.zipio.ZioEntry;
import kellinwood.zipio.ZipInput;
import kellinwood.zipio.ZipOutput;


public class BasicLearningActivity extends ActionBarActivity {

    private DragSortListView dragSortListView;

    private DragSortListView.DragSortListener dragSortListener;
    private ArrayAdapter DSAdapter;
    private String[] placesArray = new String[]{};
    private List<String> places = new ArrayList<String>(Arrays.asList(placesArray));

    private List<BasicLearningItem> itemsArray = new ArrayList<BasicLearningItem>(GlobalModelCollection.globalCollectionList);
    private MatrixCursor matrixCursor;
    private SimpleDragSortCursorAdapter cursorAdapter;
    private ButtonRectangle addItem, runSimulator;
    private int clickIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_learning);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Basic Learning Items");

        dragSortListView = (DragSortListView) findViewById(R.id.dragsortlistview);
        TextView textView = (TextView) findViewById(R.id.dsitem);
        addItem = (ButtonRectangle) findViewById(R.id.addItem);
        runSimulator = (ButtonRectangle) findViewById(R.id.runItem);

        DSAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.drag_sort_itemwhandle, places);
        matrixCursor = new MatrixCursor(new String[]{"_id","placeTitle"});
        for (int i = 0; i < itemsArray.size(); i++) {
            matrixCursor.newRow()
                    .add(i)
                    .add(itemsArray.get(i).getTitle());
        }
        cursorAdapter = new SimpleDragSortCursorAdapter(getApplicationContext(),R.layout.drag_sort_itemwhandle,matrixCursor,new String[]{"placeTitle"},new int[]{R.id.text},0);
        dragSortListener = new DragSortListView.DragSortListener() {
            @Override
            public void drag(int i, int i2) {

            }

            @Override
            public void drop(int i, int i2) {
                if(i!=i2) {

                    BasicLearningItem itemDragged = itemsArray.get(i);
                    itemsArray.set(i, itemsArray.get(i2));
                    itemsArray.set(i2, itemDragged);
                    GlobalModelCollection.globalCollectionList.clear();
                    GlobalModelCollection.globalCollectionList.addAll(itemsArray);
                    refreshList();
                }
            }

            @Override
            public void remove(int i) {
                itemsArray.remove(i);
                GlobalModelCollection.globalCollectionList.remove(i);
                refreshList();
            }
        };

        dragSortListView.setAdapter(cursorAdapter);
        dragSortListView.setDragSortListener(dragSortListener);

        dragSortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("BasicLearningForm","Item no. "+String.valueOf(i)+" has been clicked");
                Intent intent = new Intent(BasicLearningActivity.this,BasicLearningForm.class);
                intent.putExtra("clickIndex",i);
                clickIndex = i;
                intent.putExtra("requestCode", String.valueOf(Constants.EDIT_REQUEST_CODE) );
                startActivityForResult(intent, Constants.EDIT_REQUEST_CODE);
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BasicLearningActivity.this,BasicLearningForm.class);
                intent.putExtra("requestCode", String.valueOf(Constants.ADD_REQUEST_CODE));
                startActivityForResult(intent, Constants.ADD_REQUEST_CODE);
            }
        });

        runSimulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BasicLearningActivity.this,CardViewActivity.class);
                startActivity(intent);
            }
        });



    }
    //Signed Apk working properly
    //template apk file and certs have to be copied to /mnt/external_sd/bmb/ directory
    private void SignApk(){
        try {
            ZipSigner zipSigner = new ZipSigner();
            URL publicKeyUrl = new URL("file://"+Constants.DATA_BASE_DIRECTORY+"certificate.pem");
            URL privateKeyUrl = new URL("file://"+Constants.DATA_BASE_DIRECTORY+"key.pk8");
            X509Certificate certificate = zipSigner.readPublicKey(publicKeyUrl);
            PrivateKey privateKey = zipSigner.readPrivateKey(privateKeyUrl,null);
            zipSigner.setKeys("KKcorps",certificate,privateKey,null);
            zipSigner.setKeymode("testkey");
            zipSigner.signZip(Constants.DATA_BASE_DIRECTORY+"InfoTemplateAppTemp.apk", Constants.DATA_BASE_DIRECTORY+"InfoTemplateAppSigned.apk");
            Toast.makeText(this, "Signed apk generated at"+ Constants.DATA_BASE_DIRECTORY+ "InfoTemplateAppSigned.apk",Toast.LENGTH_SHORT).show();
        }catch (Throwable e){
            Toast.makeText(this, "Apk not Signed Properly",Toast.LENGTH_SHORT).show();
            
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String title = data.getStringExtra("title");
            BasicLearningItem basicLearningItem = new BasicLearningItem(title,
                    data.getStringExtra("description"), data.getStringExtra("author"),
                    data.getStringExtra("collectionTitle"), data.getStringExtra("numberOfItems"));

            if (requestCode == Constants.ADD_REQUEST_CODE) {
                itemsArray.add(basicLearningItem);
                GlobalModelCollection.globalCollectionList.add(basicLearningItem);
                Log.i("BasicLearningActivity", "Global collection size:" + String.valueOf(GlobalModelCollection.globalCollectionList.size()));
                refreshList();
            } else if(requestCode == Constants.EDIT_REQUEST_CODE){
                itemsArray.set(clickIndex, basicLearningItem);
                GlobalModelCollection.globalCollectionList.set(clickIndex, basicLearningItem);
                refreshList();
            }
        }
    }

    protected  void refreshList(){

        matrixCursor = new MatrixCursor(new String[]{"_id","placeTitle"});
        for (int j = 0; j < itemsArray.size(); j++) {
            matrixCursor.newRow()
                    .add(j)
                    .add(itemsArray.get(j).getTitle());
        }
        cursorAdapter.swapCursor(matrixCursor);
        cursorAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.basic_learning, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.generateApk:
                //generateXMLData

                try {
                    BasicLearningGenerator.writeXML("info_content.xml");
                    //Process p = Runtime.getRuntime().exec("zip -m -r " + Constants.DATA_BASE_DIRECTORY + "/QuizTemplateApp.apk /assets");
                    ZipInput zipInput = ZipInput.read(Constants.DATA_BASE_DIRECTORY+"InfoTemplateApp.apk");

                    ZioEntry zioEntry = new ZioEntry("/assets/info_content.xml",Constants.DATA_BASE_DIRECTORY+"/assets/info_content.xml");
                    FileOutputStream outputStream = new FileOutputStream(Constants.DATA_BASE_DIRECTORY+"InfoTemplateAppTemp.apk");
                    ZipOutput zipOutput =  new ZipOutput(outputStream);
                    for(ZioEntry entry : zipInput.getEntries().values()){
                        zipOutput.write(entry);
                        Log.i("zipFiles",entry.getName());
                    }
                    zipOutput.write(zioEntry);
                    zipOutput.close();
                    //zipOutput.write(zioEntry);
                    SignApk();
                }catch (Exception e){

                    e.printStackTrace();
                }
                //only signed apk implemented, data files have not been inserted

                break;
            default:
                Log.i("BasicLearningActivity","Unknown Item Clicked");
        }
        return super.onOptionsItemSelected(item);
    }
}
