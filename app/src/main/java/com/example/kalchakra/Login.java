package com.example.kalchakra;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    TextView forget_password , editTextEmail, editTextPassword, forget_Password_Email;
    Button SingUp,login,ok;
    ImageView imageView;
    LinearLayout lr;
    PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        rotate_wheel();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }


        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                forgetPassword();
            }

            public void forgetPassword() {
                LayoutInflater layoutInflater = (LayoutInflater) Login.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              //  Intent intent = new Intent(Login.this, Forgot_Password.class);
                View customView = layoutInflater.inflate(R.layout.activity_forgot__password,null);
                popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.showAtLocation(lr, Gravity.CENTER_VERTICAL, 0, 0);
               // popupWindow.update(50, 50, 300, 80);
               ImageView close_icon = (ImageView) customView.findViewById(R.id.close_icon);
                close_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });




        SingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sing_Up();
            }

            public void Sing_Up(){

                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);

            }
        });
    }

    private void userLogin()
    {
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
           editTextEmail.setError("Please enter valid email");
           editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getString("email"),
                                userJson.getString("username"),
                                userJson.getString("gender")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                //returing the response
                return requestHandler.sendPostRequest(Urls.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();

    }

    private void rotate_wheel() {
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rorate_wheel);
        imageView.startAnimation(rotation);
    }

    private void initView() {
        forget_password =(TextView) findViewById(R.id.forget_password);
        SingUp =(Button) findViewById(R.id.sign_up);
        imageView =(ImageView) findViewById(R.id.imageView);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        login =(Button) findViewById(R.id.login);
        lr = (LinearLayout) findViewById(R.id.lr);
        ok =(Button) findViewById(R.id.ok);
        //close_icon =(ImageView) findViewById(R.id.close_icon);
        forget_Password_Email =(TextView) findViewById(R.id.forget_Password_Email);
    }
}