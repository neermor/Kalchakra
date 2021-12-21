package com.example.kalchakra;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

//import static com.example.kalchakra.Location.i;

@RequiresApi(api = Build.VERSION_CODES.N)
public class LifeDaysCal extends AppCompatActivity {
    Button Registration, login;
    TextView get_age;
    int i ,exp_age;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_days_cal);



        initView();
        getValue();





        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void getValue() {
        int days =365;

    Intent intent = getIntent();
    Integer user_age = intent.getIntExtra("user_age",i);
    Integer uexp_age = intent.getIntExtra("uexp_age", exp_age);
    int age_in_days = days * (uexp_age - user_age);

   // int age_in_days = days * age_in_year;
    get_age.setText("You have "  +age_in_days+ " days Remain in your life,do something great that world will remember you");





    }

    public void registration() {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    public void login()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void initView()
    {
        Registration = (Button) findViewById(R.id.registration);
        login =(Button) findViewById(R.id.login);
        get_age =(TextView) findViewById(R.id.get_age);

    }

}