package com.example.kalchakra;



import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity  {

    BottomNavigationView bottomNavigation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.dashboard:
                                selectedFragment = DashboardFragment.newInstance();
                                break;
                            case R.id.profile:
                                selectedFragment = ProfileFragment.newInstance();
                                break;
                            case R.id.activity:
                                selectedFragment = ActivityFragment.newInstance();
                                break;

                            case R.id.setting:
                            selectedFragment = SettingFragment.newInstance();
                            break;
                    }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_container, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, DashboardFragment.newInstance());
        transaction.commit();

    }}



//    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment selectedFragment = null;
//                    switch (item.getItemId()) {
//                        case R.id.dashboard:
//                            selectedFragment = DashboardFragment.newInstance();
//                            return true;
//                        case R.id.setting:
//                            loadFragment(SettingFragment.newInstance("", ""));
//                            return true;
//
//                        case R.id.profile:
//                            loadFragment(ProfileFragment.newInstance("", ""));
//                            return true;
//
//                        case R.id.activity:
//                            loadFragment(ActivityFragment.newInstance("", ""));
//                            return true;
//
//
//                    }
//                     return false;
//                }
//            };
//


















