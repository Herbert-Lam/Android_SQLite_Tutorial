package com.example.androidsqlitetutorial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class BirthWeightDB extends SQLiteOpenHelper {

    //Set the database name and table for repetitive use
    static final private String DB_NAME = "Birth_Weight";
    static final private String DB_TABLE = "Weight_with_names";

    //Create instance variables
    Context context;
    SQLiteDatabase birthWeightDB;
    public static int count = 0;

    //Create the constructor
    public BirthWeightDB (Context ct){
        super(ct, DB_NAME, null, 1);
        context = ct;
    }

    //Create the table when a database object is initiated
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + DB_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Infant_Name TEXT, Infant_Weight TEXT)");
    }

    //Upgrade the database when the version is different
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    //Create a method to insert/add a record with the values from parameters
    public void insertData(String name, String weight){
        //Make the database writable
        birthWeightDB = getWritableDatabase();
        //Add the record into the database by executing the SQL INSERT query
        birthWeightDB.execSQL("INSERT INTO " + DB_TABLE + " (Infant_Name, Infant_Weight) VALUES ('" + name + "','" + weight + "');");
    }

    //Create a method to return an ArrayList<Infant> with the records from the database
    public ArrayList<Infant> loadData(){

        ArrayList<Infant> record = new ArrayList<>();

        //Make the database readable
        birthWeightDB = getReadableDatabase();

        //Create a cursor to read the table created by the SQL SELECT query
        Cursor cr = birthWeightDB.rawQuery("SELECT * FROM " + DB_TABLE, null);
        count = cr.getCount();

        //Read every record and store the corresponding values to the arrayList
        while (cr.moveToNext()){
            int id = cr.getInt(0);
            String nameStr = cr.getString(1);
            String weightStr = cr.getString(2);
            Infant infant = new Infant(id, nameStr, weightStr);
            record.add(infant);
        }

        //Close the cursor
        cr.close();

        //Return the arraylist
        return record;
    }

    //Create a method to delete the table/database
    public void delAll(){
        //Make the database writable
        birthWeightDB = getWritableDatabase();
        //Delete the table by executing the SQL DELETE query
        birthWeightDB.execSQL("DELETE FROM " + DB_TABLE);

        //Here is another non-SQL way to delete the database (not a table)

        //context.deleteDatabase(DB_NAME);
    }

    //Create a method to update a record in a database with the values of parameters
    public void update(String newName, String newWeight, int id){
        //Make the database writable
        birthWeightDB = getWritableDatabase();
        //Update the record to the database by executing the SQL UPDATE query with WHERE clause
        birthWeightDB.execSQL("UPDATE " + DB_TABLE + " SET Infant_Name = '" + newName + "', Infant_Weight = '"
                + newWeight + "' WHERE _id=" + id + ";");

        //Here is another non-SQL way to update an record

        //ContentValues values = new ContentValues();
        //values.put("Infant_Name", newName);
        //values.put("Infant_Weight", newWeight);
        //birthWeightDB.update(DB_TABLE, values, "_id=?", new String[]{String.valueOf(id)});
    }

    //Create a method to delete a record with id
    public void delData(int id){
        //Make the database writable
        birthWeightDB = getWritableDatabase();
        //Delete the record to the database by executing the SQL DELETE query with WHERE clause
        birthWeightDB.execSQL("DELETE FROM " + DB_TABLE + " WHERE _id=" + id + ";");

        //birthWeightDB.delete(DB_TABLE, "_id=?", new String[]{String.valueOf(id)});
    }
}