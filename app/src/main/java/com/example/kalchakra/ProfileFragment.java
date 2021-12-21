package com.example.kalchakra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;



public class ProfileFragment extends Fragment {
    ImageView  back;
    CircleImageView profile_image;

    Button logoutButton;
    View view;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

    TextView  textViewUsername, textViewEmail, textViewGender;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view=  inflater.inflate(R.layout.fragment_profile, container, false);

        if (!SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            getActivity().finish();
            startActivity(new Intent(getActivity(), Login.class));
        }

        initView();


        User user = SharedPrefManager.getInstance(getContext()).getUser();

        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());
        textViewGender.setText(user.getGender());

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
            }
        });


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(imageTakeIntent,0);


            }
        });

        return view;
    }

    private void initView() {

        textViewUsername = (TextView) view.findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
        textViewGender = (TextView) view.findViewById(R.id.textViewGender);
        logoutButton =(Button)view.findViewById(R.id.logout);
        back = (ImageView) view.findViewById(R.id.back);
        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap bitmap =(Bitmap) data.getExtras().get("data");
        profile_image.setImageBitmap(bitmap);

    }
}

