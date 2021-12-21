package com.example.kalchakra;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {
    RecyclerView recyclerView;
    View view;
    ImageView imageView;
    ActivityFragment af;
    ArrayList<String> Id , Date, event;

        public static DashboardFragment newInstance() {
            DashboardFragment fragment = new DashboardFragment();
            return fragment;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            view= inflater.inflate(R.layout.fragment_dashboard, container, false);

            initView();
            rotate();
            return view;


            
        }

        private void rotate() {
            Animation rotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rorate_wheel);
            imageView.startAnimation(rotation);
        }


    private void initView() {

        imageView =(ImageView) view.findViewById(R.id.imageView);
    }
}

