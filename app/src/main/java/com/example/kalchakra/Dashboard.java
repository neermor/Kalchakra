package com.example.kalchakra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.ImageView;

public class Dashboard extends AppCompatActivity {
    CardView profile, calender;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initView();
        rotate();


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                profile_open();
            }

        });

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender_open();
            }
            public void calender_open()
            {
               Intent intent =new Intent(Dashboard.this,Calender.class);
                startActivity(intent);
            }
        });



    }

    private void rotate() {
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rorate_wheel);
        imageView.startAnimation(rotation);
    }

    private void initView() {
        profile = (CardView) findViewById(R.id.profile_card);
        calender = (CardView) findViewById(R.id.calender);
        imageView =(ImageView) findViewById(R.id.imageView);



    }


    public void profile_open() {
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        }





    }