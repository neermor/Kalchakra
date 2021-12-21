package com.example.kalchakra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.usage.UsageEvents;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import static java.nio.file.StandardOpenOption.CREATE;

public class Calender extends AppCompatActivity {

    private mySQLiteDBHandler dbHandler;
    private EditText editEvent;
    private CalendarView calendarView;
    private String selectedDate;
    private SQLiteDatabase sqLiteDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);

        editEvent =(EditText) findViewById(R.id.editEvent);
        calendarView =(CalendarView) findViewById(R.id.calndarView1);



     calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);
                Log.e("test","hello");

                ReadDatabase(view);
            }
        });



        try{

            dbHandler = new mySQLiteDBHandler(this,"CalendarDatabase",null,1);
            sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE EventCalender (Date Text,Event Text)");

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void InsertDatabase(View view){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date",selectedDate);
        contentValues.put("Event",editEvent.getText().toString());
        sqLiteDatabase.insert("EventCalender",null,contentValues);


    }

    public void ReadDatabase(View view){
        String query = "Select Event from EventCalender where Date =" +selectedDate;
        try{
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst();
            editEvent.setText(cursor.getString(0));
        }
        catch (Exception e){
            e.printStackTrace();
            editEvent.setText("");
        }
    }

}