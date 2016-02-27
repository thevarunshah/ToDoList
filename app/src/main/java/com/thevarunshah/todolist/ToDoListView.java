package com.thevarunshah.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ToDoListView extends Activity implements View.OnClickListener {

    private final static String TAG = "ToDoListView"; //for debugging purposes

    private ArrayList<String> list = new ArrayList<String>(); //the actual list
    private ListView listView = null; //main view of to-do list
    ArrayAdapter<String> listAdapter = null; //how to manage the list

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list_view); //set the activity view

        //obtain list view and create new to-do list custom adapter
        listView = (ListView) findViewById(R.id.listview);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        //attach adapter to list view
        listView.setAdapter(listAdapter);

        //on long click, delete item
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int positionFinal = position;

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ToDoListView.this);

                alertDialogBuilder.setTitle("Delete Item");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                list.remove(positionFinal);
                                listAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                return false;
            }
        });

        //obtain add button and attach press listener to it
        FloatingActionButton addItem = (FloatingActionButton) findViewById(R.id.add_item);
        addItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //start add item activity and wait for result (in onActivityResult(...))
        Intent i = new Intent(ToDoListView.this, AddItemView.class);
        startActivityForResult(i, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK && requestCode == 0){

            String item = data.getStringExtra("text");

            //add item to main list and update view
            list.add(item);
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause(){

        super.onPause();

        try {
            //backup data
            Backup.writeData(ToDoListView.this, list);
        } catch (Exception e) {
            Log.i(TAG, "could not write to file");
            Log.i(TAG, "Exception: " + e);
        }
    }

    @Override
    protected void onResume(){

        super.onResume();

        try {
            if(list.isEmpty()){
                //read data from backup
                ArrayList<String> backup = Backup.readData(ToDoListView.this);
                this.list.clear();
                this.list.addAll(backup);
            }
        } catch (Exception e) {
            Log.i(TAG, "could not read from file");
            Log.i(TAG, "Exception: " + e);
        }
    }
}
