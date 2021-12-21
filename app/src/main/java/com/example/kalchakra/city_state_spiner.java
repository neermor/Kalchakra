package com.example.kalchakra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class city_state_spiner extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener
{

    private Spinner country_Spinner;
    private Spinner state_Spinner;
    private Spinner city_Spinner;
    private ArrayList<String> country_list, state_list, city_list;
    ArrayAdapter setAdapter;
    public AdapterView.OnItemSelectedListener setOnItemSelectedListener;


    ArrayList<String> listSpinner=new ArrayList<String>();
    // to store the city and state in the format : City , State. Eg: New Delhi , India
    ArrayList<String> listAll=new ArrayList<String>();
    // for listing all states
    ArrayList<String> listState=new ArrayList<String>();
    // for listing all cities
    ArrayList<String> listCity=new ArrayList<String>();
    // access all auto complete text views
    AutoCompleteTextView act;

    //An ArrayList for Spinner Items


//    ArrayAdapter<String> arrayAdapter_country;
//    ArrayAdapter<String> arrayAdapter_state;
//    ArrayAdapter<String> arrayAdapter_city;

    //AutoCompleteTextView act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_state_spiner);

        country_list = new ArrayList<String>();
        state_list = new ArrayList<String>();
        city_list = new ArrayList<String>();


        initializeUI();

    }

    private void initializeUI() {

        country_Spinner = (Spinner) findViewById(R.id.country);
        state_Spinner = (Spinner) findViewById(R.id.city);
        city_Spinner = (Spinner) findViewById(R.id.state);


    }


    public String getJson()
    {
        String json=null;
        try
        {
            // Opening cities.json file
            InputStream is = getAssets().open("demo.json");
            // is there any content in the file
            int size = is.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            is.read(buffer);
            // close the stream --- very important
            is.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return json;
        }
        return json;
          }


    // This add all JSON object's data to the respective lists
    void obj_list()
    {
        // Exceptions are returned by JSONObject when the object cannot be created
        try
        {
            // Convert the string returned to a JSON object
            JSONObject jsonObject=new JSONObject(getJson());
            // Get Json array
            JSONArray array=jsonObject.getJSONArray("array");
            // Navigate through an array item one by one
            for(int i=0;i<array.length();i++)
            {
                // select the particular JSON data
                JSONObject object=array.getJSONObject(i);
                String country =object.getString("country");
                String city=object.getString("name");
                String state=object.getString("state");
                // add to the lists in the specified format
                listSpinner.add(String.valueOf(i+1)+" : "+city+" , "+state+" , "+country);
                listAll.add(city+" , "+state);
                listCity.add(city);
                listState.add(state);

            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



