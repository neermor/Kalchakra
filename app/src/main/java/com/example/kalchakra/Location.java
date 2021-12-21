package com.example.kalchakra;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.NumberUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.datepicker.MaterialCalendar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;
import static java.lang.Double.parseDouble;
import android.content.Intent;


public class Location extends AppCompatActivity implements LocationListener {

    Button button_location;
    TextInputEditText country, state, city;
    LocationManager locationManager;
    android.location.Location location;
    private double lat, longnitude;
    LinearLayout spinner_layout, gps_location;
    TextView textmanual;

    Button calculate_age;
    EditText dob, expected_age;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    MaterialCheckBox materialCheckBox;
    long time;
    int age;
    Date mDate;
    public static int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        initViews();


        //runTime permission
        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Location.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Location.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                } else {
                    detectCurrentLocation();


                }
            }

        });


        materialCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gps_location.setVisibility(LinearLayout.GONE);
                    spinner_layout.setVisibility(LinearLayout.VISIBLE);
                    textmanual.setVisibility(LinearLayout.VISIBLE);
                    button_location.setVisibility(LinearLayout.GONE);


                } else {
                    gps_location.setVisibility(LinearLayout.VISIBLE);
                    spinner_layout.setVisibility(LinearLayout.GONE);
                    textmanual.setVisibility(LinearLayout.GONE);
                    button_location.setVisibility(LinearLayout.VISIBLE);

                }
            }
        });

        calculate_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateAge();

            }





            private void validateAge() {

                String birthday = dob.getText().toString().trim();
                String exp_age = (expected_age.getText().toString());


                //int age;
                //  long i;


                if (exp_age.isEmpty()) {
                    Toast.makeText(Location.this, "Please Enter Your Expected Age ", Toast.LENGTH_SHORT).show();

                    if (birthday.isEmpty()) {

                        Toast.makeText(Location.this, "Please Enter Your birthday   ", Toast.LENGTH_SHORT).show();


                    }


                }

                else {
                    i = calculateAge(time);
                    age = Integer.parseInt(exp_age);

                    if (age <= i) {
                        Toast.makeText(Location.this, "Your expected age can not be less then your age   ", Toast.LENGTH_SHORT).show();

                    } else if (i <= age) {
                      //  i = calculateAge(time);
                        Intent intent = new Intent(Location.this, LifeDaysCal.class);
                        intent.putExtra("user_age",i);
                        intent.putExtra("uexp_age",age);
                        startActivity(intent);
                    }

                }

            }


        });


        dob.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.DATE);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Location.this,R.style.MyCalendarTheme, onDateSetListener,
                        year, month, day);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.updateDate(2020,01,01);
               // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);
                // @SuppressLint("SimpleDateFormat") String format = new SimpleDateFormat("dd MMM YYYY").format(c.getTime());

                month = month + 1;
                // Log.d(TAG,"onDateSet: DD/MM/YYYY: " + day + "/" + month + "/" +year);
                String birth_date = day + "/" + month + "/" + year;
                dob.setText(birth_date);

                SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");

                try {
                    {
                        mDate = (Date) sdf.parse(dob.getText().toString());

                        time = mDate.getTime();

                        System.out.println("Date in milli :: " + time);
                    }
                }
                catch (Exception e)
                {

                }
            }


        };


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int calculateAge(Long date) {
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--;


        }
        return age;

    }


    private void detectCurrentLocation() {

        Toast.makeText(Location.this, "Now Your Location is set", Toast.LENGTH_SHORT).show();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 1, 10, this);

        }


        if (locationManager != null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                lat = location.getLatitude();
                longnitude = location.getAltitude();
                findAddress();
            }
        }
    }


    private void initViews() {

        button_location = findViewById(R.id.location);
        country = findViewById(R.id.country);
        state = findViewById(R.id.State);
        city = findViewById(R.id.City);
        calculate_age = (Button) findViewById(R.id.look_next);
        dob = (EditText) findViewById(R.id.dob);
        expected_age = (EditText) findViewById(R.id.expected_age);
        materialCheckBox = (MaterialCheckBox) findViewById(R.id.show_spinner);
        spinner_layout = (LinearLayout) this.findViewById(R.id.city_state_country);
        gps_location = (LinearLayout) this.findViewById(R.id.gps_location);
        textmanual = (TextView) findViewById(R.id.textmanul);


    }

    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {

        lat = location.getLatitude();
        longnitude = location.getAltitude();
        findAddress();

    }

    private void findAddress() {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {

            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            String countryName = addresses.get(0).getCountryName();
            String stateName = addresses.get(0).getAdminArea();
            String cityName = addresses.get(0).getLocality();

            country.setText(countryName);
            state.setText(stateName);
            city.setText(cityName);


        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {


    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

        Toast.makeText(this, "Please Turn on your GPS", Toast.LENGTH_SHORT).show();

    }

    }
