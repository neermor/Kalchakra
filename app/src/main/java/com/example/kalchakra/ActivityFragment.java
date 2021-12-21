package com.example.kalchakra;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.IDNA;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;

import static android.icu.text.MessagePattern.ArgType.SELECT;

public class ActivityFragment extends Fragment {

    private mySQLiteDBHandler dbHandler;

    EditText editEvent, eventName;
    TextView viewTask, eventtime;
    private CalendarView calendarView;
    private String selectedDate;
    private SQLiteDatabase sqLiteDatabase;
    Button saveEvent, addEvent;
    ImageView seteventtime;
    CheckBox alarmme;
    int mHour, mMinute;
    final static int RzQS_1 = 1;
    RecyclerView recyclerView;
    ArrayList<String>  Date, eventData;
    EventAdapter eventAdapter;
    TimePicker timepicker;


    LinearLayout lr, lr2;
    PopupWindow popupWindow, eventShow;

    View view1;

    public static ActivityFragment newInstance() {
        ActivityFragment fragment = new ActivityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view1 = inflater.inflate(R.layout.fragment_activity, container, false);
        initView();

        calendarEvent();


        eventData= new ArrayList<>();
        Date= new ArrayList<>();
        ReadDatabase(view1);
        eventAdapter = new EventAdapter(getActivity(),eventData,Date);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view1;

    }

    private void initView() {
        editEvent = (EditText) view1.findViewById(R.id.editEvent);
        calendarView = (CalendarView) view1.findViewById(R.id.calndarView);
        recyclerView = (RecyclerView) view1.findViewById(R.id.recyclerView);


    }

    public void calendarEvent() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);

                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_event_dialog, null);
                lr = (LinearLayout) customView.findViewById(R.id.eventDilog);

                popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.showAtLocation(lr, Gravity.CENTER_VERTICAL, 0, 0);
                addEvent = (Button) customView.findViewById(R.id.addevent);
                eventName = (EditText) customView.findViewById(R.id.eventname);
                seteventtime =(ImageView) customView.findViewById(R.id.seteventtime);
                eventtime =(TextView) customView.findViewById(R.id.eventtime);
                alarmme = (CheckBox) customView.findViewById(R.id.alarmme);

                setTime();
                ReadDatabase(view);

                addEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            InsertDatabase(view);
                            popupWindow.dismiss();

                    }
                });
                popupWindow.setFocusable(true);
                popupWindow.update();

            }
        });


        try {

            dbHandler = new mySQLiteDBHandler(view1.getContext(), "CalendarDatabase", null, 1);
            sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE EventCalendar(Id INTEGER primary key AUTOINCREMENT, Date Text, Event TEXT)");


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void setTime()
    {
        seteventtime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay,
                                                  int minute) {

                                eventtime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });


    }

    public void InsertDatabase(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", selectedDate);
        contentValues.put("Event", eventName.getText().toString());

        sqLiteDatabase.insert("EventCalendar", null, contentValues);
        ReadDatabase(view);
    }



    public ArrayList ReadDatabase(View view) {
        ArrayList<String> eventNameArray = null;
        String query = "Select * from EventCalendar where Date =" + selectedDate;
        try {

            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst();
            eventName.setText(cursor.getString(0));


        } catch (Exception e) {
            e.printStackTrace();
              //eventName.setText("");


        }
        return eventNameArray;

    }




    public void UpdateDatabase(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date",selectedDate);
        contentValues.put("Event",eventName.getText().toString());
        sqLiteDatabase.update("EventCalendar", contentValues,"_selectedDate",null);
    }
}
