package com.kkcorps.bmltoolkitandroid;

import android.app.Activity;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.mobeta.android.dslv.DragSortItemView;
import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.SimpleDragSortCursorAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BasicLearningActivity extends Activity {

    private DragSortListView dragSortListView;

    private DragSortListView.DragSortListener dragSortListener;
    private ArrayAdapter DSAdapter;
    private String[] placesArray = new String[]{"New Delhi","Lucknow","Agra","Chandigarh","Gurgaon"};
    private List<String> places = new ArrayList<String>(Arrays.asList(placesArray));
    private int REQUEST_CODE = 101;

    private List<BasicLearningItem> itemsArray = new ArrayList<BasicLearningItem>();
    private MatrixCursor matrixCursor;
    private SimpleDragSortCursorAdapter cursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_learning);
        dragSortListView = (DragSortListView) findViewById(R.id.dragsortlistview);

        TextView textView = (TextView) findViewById(R.id.dsitem);
        DSAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.drag_sort_itemwhandle, places);
        matrixCursor = new MatrixCursor(new String[]{"_id","placeTitle"});
        for (int i = 0; i < places.size(); i++) {
            matrixCursor.newRow()
                    .add(i)
                    .add(places.get(i));
        }
        cursorAdapter = new SimpleDragSortCursorAdapter(getApplicationContext(),R.layout.drag_sort_itemwhandle,matrixCursor,new String[]{"placeTitle"},new int[]{R.id.text},0);
        dragSortListener = new DragSortListView.DragSortListener() {
            @Override
            public void drag(int i, int i2) {

            }

            @Override
            public void drop(int i, int i2) {
                if(i!=i2) {

                    String s11 = places.get(i);
                    places.set(i,places.get(i2));
                    places.set(i2, s11);
                    matrixCursor = new MatrixCursor(new String[]{"_id","placeTitle"});
                    for (int j = 0; j < places.size(); j++) {
                        matrixCursor.newRow()
                                .add(j)
                                .add(places.get(j));
                    }
                    cursorAdapter.swapCursor(matrixCursor);
                    cursorAdapter.notifyDataSetChanged();
                    //.notifyDataSetChanged();
                    /*String s1 = (String) DSAdapter.getItem(i);
                    DSAdapter.remove(i);
                    DSAdapter.insert(DSAdapter.getItem(i2), i);
                    DSAdapter.remove(i2);
                    DSAdapter.insert(s1, i2);*/

                }
                /*String s1 = places.get(i);

                places.set(i,places.get(i2));
                places.set(i2, s1);*/

                //Object v1 = DSAdapter.getItem(i);

                //DSAdapter.notify();
            }

            @Override
            public void remove(int i) {
                places.remove(i);
                matrixCursor = new MatrixCursor(new String[]{"_id","placeTitle"});
                for (int j = 0; j < places.size(); j++) {
                    matrixCursor.newRow()
                            .add(j)
                            .add(places.get(j));
                }
                cursorAdapter.swapCursor(matrixCursor);
                cursorAdapter.notifyDataSetChanged();
                //DSAdapter.notify();
            }
        };

        dragSortListView.setAdapter(cursorAdapter);
        dragSortListView.setDragSortListener(dragSortListener);

        dragSortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("BasicLearningForm","Item no. "+String.valueOf(i)+" has been clicked");
                Intent intent = new Intent(BasicLearningActivity.this,BasicLearningForm.class);
                intent.putExtra("title",places.get(i));
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==Activity.RESULT_OK){
            BasicLearningItem basicLearningItem = new BasicLearningItem(data.getStringExtra("title"),
                    data.getStringExtra("description"),data.getStringExtra("author"),
                    data.getStringExtra("collectionTitle"),Integer.parseInt(data.getStringExtra("numberOfItems")));
            itemsArray.add(basicLearningItem);
            places.add(data.getStringExtra("title"));
            //DSAdapter.notifyDataSetChanged();
        }
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
