package com.kkcorps.bmltoolkitandroid;

import android.app.Activity;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.mobeta.android.dslv.DragSortItemView;
import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.SimpleDragSortCursorAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BasicLearningActivity extends ActionBarActivity {

    private DragSortListView dragSortListView;

    private DragSortListView.DragSortListener dragSortListener;
    private ArrayAdapter DSAdapter;
    private String[] placesArray = new String[]{};
    private List<String> places = new ArrayList<String>(Arrays.asList(placesArray));

    private List<BasicLearningItem> itemsArray = new ArrayList<BasicLearningItem>(GlobalModelCollection.globalCollectionList);
    private MatrixCursor matrixCursor;
    private SimpleDragSortCursorAdapter cursorAdapter;
    private Button addItem;
    private int clickIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_learning);
        getSupportActionBar().setTitle("Basic Learning Items");
        dragSortListView = (DragSortListView) findViewById(R.id.dragsortlistview);
        TextView textView = (TextView) findViewById(R.id.dsitem);
        addItem = (Button) findViewById(R.id.addItem);

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
                itemsArray.set(clickIndex,basicLearningItem);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
