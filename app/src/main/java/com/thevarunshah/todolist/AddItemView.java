package com.thevarunshah.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemView extends Activity implements View.OnClickListener{

    /*
    This method is where the activity layout is set up
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_view);

        //obtain button and attach press listener to it
        Button addItem = (Button) findViewById(R.id.add_item);
        addItem.setOnClickListener(this);
    }

    /*
    This on-click method listens for taps to what it is attached to
     */
    @Override
    public void onClick(View v) {

        //edittext object of the item entered
        EditText text = (EditText) findViewById(R.id.item_text);

        //create new intent to put the item in
        Intent data = new Intent();
        data.putExtra("text", text.getText().toString());

        //attach data to return call and go back to main view
        setResult(RESULT_OK, data);
        finish();
    }
}
