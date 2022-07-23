package com.example.androidsqlitetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    //Create view variables
    private EditText etNameRecord;
    private EditText etWeightRecord;
    //Create an intent and a String variable for directing to MainActivity
    private Intent intent2;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Connect the view variables with id
        etNameRecord = findViewById(R.id.etNameRecord);
        etWeightRecord = findViewById(R.id.etWeightRecord);

        //Get the field values from MainActivity
        etNameRecord.setText(getIntent().getStringExtra("name"));
        etWeightRecord.setText(getIntent().getStringExtra("weight"));

        //Create an intent object of MainActivity
        intent2 = new Intent(this, MainActivity.class);
    }

    public void updateRecord(View view) {
        //Store the input from user
        String nameInput = etNameRecord.getText().toString();
        String weightInput = etWeightRecord.getText().toString();

        //Check if the input is empty
        if(nameInput.equals("") || weightInput.equals("")){
            Toast.makeText(this, "Please enter the name and the weight.", Toast.LENGTH_LONG).show();
        } else {
            //If not, update the selected record of the database in the MainActivity by calling the update() with parameters
            MainActivity.bwDB.update(nameInput, weightInput, MainActivity.id);
            //Notification to user
            Toast.makeText(this, "1 record is modified successfully", Toast.LENGTH_LONG).show();
        }

        //Store flag into the intent to trigger the code in the onCreate() "if" statement in the MainActivity
        flag = "update";
        intent2.putExtra("flag", flag);
        //Go back to MainActivity
        startActivity(intent2);
    }

    public void delRecord(View view) {
        //Delete the selected record from the database in the MainActivity by calling the delData() with the id parameter
        MainActivity.bwDB.delData(MainActivity.id);
        Toast.makeText(this, "1 record is deleted successfully.", Toast.LENGTH_LONG).show();

        //Store flag into the intent to trigger the code in the onCreate() "if" statement in the MainActivity
        flag = "delete";
        intent2.putExtra("flag", flag);
        //Go back to MainActivity
        startActivity(intent2);
    }

    public void back(View view) {
        //Store flag into the intent to trigger the code in the onCreate() "if" statement in the MainActivity
        flag = "back";
        intent2.putExtra("flag", flag);
        //Go back to MainActivity
        startActivity(intent2);
    }
}