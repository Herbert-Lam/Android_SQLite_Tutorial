package com.example.androidsqlitetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Create view variables
    private EditText etName;
    private EditText etWeight;
    private TableLayout tl;

    //Create an ArrayList<String[]> to store the data from the database and display the data in the views
    private ArrayList<String[]> outputAL;

    //Create the database variable and int id which will be used across two activities
    public static BirthWeightDB bwDB;
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect the view variables with ids
        etName = findViewById(R.id.etName);
        etWeight = findViewById(R.id.etWeight);
        tl = findViewById(R.id.tl);

        //Create a concrete database object
        bwDB = new BirthWeightDB(this);

        //To load and display the database when launching the MainActivity from SecondActivity
        if(getIntent().getExtras() != null){
            loadDB(tl);
            bwDB.close();
        }
    }

    //Create a button event method to add a record to the database from user input
    public void addData(View view) {
        //Store the input from user
        String nameInput = etName.getText().toString();
        String weightInput = etWeight.getText().toString();

        //Check if the input is empty
        if(nameInput.equals("") || weightInput.equals("")){
            //If empty then ask the user to enter the values
            Toast.makeText(this, "Please enter the name and the weight.", Toast.LENGTH_LONG).show();
        } else {
            //If not empty, insert the input into the database, show a notification to user, clear the input fields and close the database
            bwDB.insertData(nameInput, weightInput);
            Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_LONG).show();
            etName.setText("");
            etWeight.setText("");
            bwDB.close();
        }
    }

    //Create a button event method to clear the input fields
    public void clear(View view) {
        etName.setText("");
        etWeight.setText("");
    }

    //Create a button event method to read the database and display the records to user
    public void loadDB(View view) {
        //Read the database and store the values to the ArrayList
        outputAL = bwDB.loadData();

        //Check if the database is empty
        if(bwDB.count == 0) {
            //Clear the tablelayout
            tl.removeAllViews();

            //Create a textview to display a message about no data
            TextView txvNoData = new TextView(this);
            txvNoData.setText("There is no data yet.");
            txvNoData.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txvNoData.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            //Insert the textview into the tablelayout
            tl.addView(txvNoData);
        } else {
            //If the database is not empty
            //Clear the tablelayout
            tl.removeAllViews();

            //Create a table row to show the headers of records
            TableRow tr_head = new TableRow(this);

            //Create two textviews for the two headers
            TextView txvNameHeader = new TextView(this);
            txvNameHeader.setText("Infant Name");
            txvNameHeader.setTextSize(16);
            txvNameHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 4f));

            TextView txvWeightHeader = new TextView(this);
            txvWeightHeader.setText("Weight (lbs)");
            txvWeightHeader.setTextSize(16);
            txvWeightHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

            //Insert the two textviews into the table row
            tr_head.addView(txvNameHeader);
            tr_head.addView(txvWeightHeader);

            //Insert the table row to the tablelayout
            tl.addView(tr_head);

            //Using for-loop to display every records from the database
            for(int i = 0; i < bwDB.count; i++){
                //Create a table tow to show the data of each record
                TableRow tr = new TableRow(this);

                //Create two textviews for the fields of each record
                TextView txv1 = new TextView(this);
                //Read the first field from the ArrayList. ([0] is the id column)
                String s1 = outputAL.get(i)[1];
                txv1.setText(s1);
                txv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 4f));

                TextView txv2 = new TextView(this);
                //Read the second field from the ArrayList
                String s2 = outputAL.get(i)[2];
                txv2.setText(s2);
                txv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

                //Insert the two textviews into each table row
                tr.addView(txv1);
                tr.addView(txv2);

                //Set Id and make each row to be clickable with events
                tr.setId(i);
                tr.setClickable(true);
                tr.setOnClickListener(trOnClickListener);

                //Insert each table row to the tablelayout
                tl.addView(tr);
            }

            //Clear the input fields
            etName.setText("");
            etWeight.setText("");
        }
    }

    //Create an onClickListener to execute the followings when the user clicks the table row
    public View.OnClickListener trOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            //Create an intent of SecondActivity
            Intent intent1 = new Intent(v.getContext(), SecondActivity.class);

            //Refer which table row is clicked
            int idx = v.getId();
            //Get the id from the ArrayList for modifying/deleting a record in the database
            id = Integer.parseInt(outputAL.get(idx)[0]);

            //Get the field values of the selected table row for user to recognize which row is selected
            String s1 = outputAL.get(idx)[1];
            String s2 = outputAL.get(idx)[2];

            //Store the values into the intent
            intent1.putExtra("id", id);
            intent1.putExtra("name", s1);
            intent1.putExtra("weight", s2);

            //Jump to SecondActivity
            startActivity(intent1);
        }
    };

    //Create a button event method to delete the table/database
    public void delDB(View view) {
        //Delete the table/database
        bwDB.delAll();
        //Clear the tablelayout
        tl.removeAllViews();
        //Notify the user that the table/database is deleted
        Toast.makeText(this, "All data is erased.", Toast.LENGTH_LONG).show();
        //Close the database
        bwDB.close();
    }
}