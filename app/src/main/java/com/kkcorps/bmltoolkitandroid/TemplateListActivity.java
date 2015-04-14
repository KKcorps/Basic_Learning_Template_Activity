package com.kkcorps.bmltoolkitandroid;

import android.app.Activity;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.kkcorps.bmltoolkitandroid.BasicLearningTemplate.BasicLearningEditor;
import com.kkcorps.bmltoolkitandroid.BasicLearningTemplate.BasicLearningItem;
import com.kkcorps.bmltoolkitandroid.BasicLearningTemplate.BasicLearningSimulatorCard;
import com.kkcorps.bmltoolkitandroid.BasicLearningTemplate.BasicLearningGenerator;
import com.kkcorps.bmltoolkitandroid.FlashCardTemplate.FlashCardEditor;
import com.kkcorps.bmltoolkitandroid.FlashCardTemplate.FlashCardSimulator;
import com.kkcorps.bmltoolkitandroid.Utils.FileUtils;
import com.kkcorps.bmltoolkitandroid.Utils.XmlGenerator;
import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.SimpleDragSortCursorAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.zipio.ZioEntry;
import kellinwood.zipio.ZipInput;
import kellinwood.zipio.ZipOutput;


public class TemplateListActivity extends ActionBarActivity {

    private DragSortListView dragSortListView;

    private DragSortListView.DragSortListener dragSortListener;
    private ArrayAdapter DSAdapter;
    private String[] placesArray = new String[]{};
    private List<String> places = new ArrayList<String>(Arrays.asList(placesArray));

    private List<Model> itemsArray = new ArrayList<Model>(GlobalModelCollection.globalCollectionList);
    private MatrixCursor matrixCursor;
    private SimpleDragSortCursorAdapter cursorAdapter;
    private ButtonRectangle addItem, runSimulator;
    private int clickIndex;
    private String TempAPkName = null, projectName;
    private android.app.Dialog dialog;
    private Constants.Templates TemplateName = Constants.Templates.INFO;
    String TemplateApkName = "InfoTemplateApp";
    private Class EditorActivity = BasicLearningEditor.class,
            Simulator = BasicLearningSimulatorCard.class;
    private String TAG = "Template List Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_learning);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        handleIntent(getIntent());

        getSupportActionBar().setTitle(TemplateName.toString().toLowerCase()+" Items");

        dragSortListView = (DragSortListView) findViewById(R.id.dragsortlistview);
        TextView textView = (TextView) findViewById(R.id.dsitem);
        addItem = (ButtonRectangle) findViewById(R.id.addItem);
        runSimulator = (ButtonRectangle) findViewById(R.id.runItem);

        DSAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.drag_sort_item_with_handle, places);
        matrixCursor = new MatrixCursor(new String[]{"_id","placeTitle"});
        for (int i = 0; i < itemsArray.size(); i++) {
            matrixCursor.newRow()
                    .add(i)
                    .add(itemsArray.get(i).getTitle());
        }
        cursorAdapter = new SimpleDragSortCursorAdapter(getApplicationContext(),R.layout.drag_sort_item_with_handle,matrixCursor,new String[]{"placeTitle"},new int[]{R.id.text},0);
        dragSortListener = new DragSortListView.DragSortListener() {
            @Override
            public void drag(int i, int i2) {

            }

            @Override
            public void drop(int i, int i2) {
                if(i!=i2) {

                    Model itemDragged = itemsArray.get(i);
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
                //Log.i("BasicLearningForm","Item no. "+String.valueOf(i)+" has been clicked");
                Intent intent = new Intent(TemplateListActivity.this, EditorActivity);
                intent.putExtra("clickIndex",i);
                clickIndex = i;
                intent.putExtra("requestCode", String.valueOf(Constants.EDIT_REQUEST_CODE) );
                startActivityForResult(intent, Constants.EDIT_REQUEST_CODE);
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TemplateListActivity.this,EditorActivity);
                intent.putExtra("requestCode", String.valueOf(Constants.ADD_REQUEST_CODE));
                startActivityForResult(intent, Constants.ADD_REQUEST_CODE);
            }
        });

        runSimulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TemplateListActivity.this,Simulator);
                startActivity(intent);
            }
        });

        //dialog = new Dialog(this,"Save Dialog","Enter the Project Name");
        dialog = new android.app.Dialog(this);
        dialog.setContentView(R.layout.dialog_save);
        dialog.setTitle("Save Dialog");
        final TextView dialogMessage = (TextView) dialog.findViewById(R.id.dialogMessage);
        dialogMessage.setText("Enter the Project Name");
        final EditText projectNameView = (EditText) dialog.findViewById(R.id.projectName);
        ButtonRectangle dialogButton = (ButtonRectangle) dialog.findViewById(R.id.dialogButtonSave);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectName = projectNameView.getText().toString();
                if(projectName!=null && !projectName.equals("")){
                    Constants.PROJECT_NAME_TEMP = projectName+".xml";
                }
                XmlGenerator.writeXML(Constants.PROJECT_NAME_TEMP, TemplateName);
                Toast.makeText(TemplateListActivity.this,"Project saved at "+Constants.DATA_BASE_DIRECTORY+"assets/"+Constants.PROJECT_NAME_TEMP,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    private void handleIntent(Intent intent){
        TemplateName = Constants.Templates.valueOf(intent.getStringExtra("SelectedTemplate").replace(" ","").toUpperCase());
        switch (TemplateName){
            case FLASHCARDS:
                EditorActivity = FlashCardEditor.class;
                Simulator = FlashCardSimulator.class;
                TemplateApkName = "FlashCardsTemplateApp";
                break;
            case INFO:
                EditorActivity = BasicLearningEditor.class;
                Simulator = BasicLearningSimulatorCard.class;
                TemplateApkName = "InfoTemplateApp";
                break;
            default:
                Log.i(TAG, "Unknown Template Selected");
        }
    }
    private void SignApk(){
        try {
            ZipSigner zipSigner = new ZipSigner();
            URL publicKeyUrl = FileUtils.UrlFromFile(FileUtils.copyFileFromAsset(TemplateListActivity.this, "keys/", Constants.CERT_NAME));
            URL privateKeyUrl = FileUtils.UrlFromFile(FileUtils.copyFileFromAsset(TemplateListActivity.this,"keys/",Constants.KEY_NAME));

            X509Certificate certificate = zipSigner.readPublicKey(publicKeyUrl);
            PrivateKey privateKey = zipSigner.readPrivateKey(privateKeyUrl,null);
            zipSigner.setKeys("KKcorps",certificate,privateKey,null);
            zipSigner.setKeymode("testkey");
            zipSigner.signZip(getCacheDir()+"/Temp.apk", Constants.DATA_BASE_DIRECTORY+TemplateApkName+"Signed.apk");
            Toast.makeText(this, "Signed apk generated at"+ Constants.DATA_BASE_DIRECTORY+ TemplateApkName+"Signed.apk",Toast.LENGTH_SHORT).show();
        }catch (Throwable e){
            Toast.makeText(this, "Apk not Signed Properly",Toast.LENGTH_SHORT).show();
            
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            /*String title = data.getStringExtra("title");
            BasicLearningItem basicLearningItem = new BasicLearningItem(title,
                    data.getStringExtra("description"), data.getStringExtra("author"),
                    data.getStringExtra("collectionTitle"), data.getStringExtra("numberOfItems"));*/


            if (requestCode == Constants.ADD_REQUEST_CODE) {
                itemsArray = GlobalModelCollection.globalCollectionList;
                //itemsArray.add(basicLearningItem);
                //GlobalModelCollection.globalCollectionList.add(basicLearningItem);
                //Log.i("BasicLearningActivity", "Global collection size:" + String.valueOf(GlobalModelCollection.globalCollectionList.size()));
                refreshList();
            } else if(requestCode == Constants.EDIT_REQUEST_CODE){
                itemsArray = GlobalModelCollection.globalCollectionList;
                //itemsArray.set(clickIndex, basicLearningItem);
                //GlobalModelCollection.globalCollectionList.set(clickIndex, basicLearningItem);
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
                    File xmlFile = XmlGenerator.writeXML(null,TemplateName);
                    File f = FileUtils.copyFileFromAsset(TemplateListActivity.this, "Apks/",TemplateApkName+".apk");
                    ZipInput zipInput = ZipInput.read(f.getAbsolutePath());

                    ZioEntry zioEntry = new ZioEntry("/assets/"+xmlFile.getName(),xmlFile.getAbsolutePath());
                    //TODO: Delete Temporary Apk File
                    FileOutputStream outputStream = new FileOutputStream(getCacheDir()+"/Temp.apk");
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
                break;

            case R.id.action_save:
                dialog.show();
                break;
            default:
                Log.i("BasicLearningActivity","Unknown Item Clicked");
        }
        return super.onOptionsItemSelected(item);
    }
}
