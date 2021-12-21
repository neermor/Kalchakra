package com.example.kalchakra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private Context context;
   private ArrayList Date,event;

    EventAdapter(Context context ,ArrayList date, ArrayList event)
    {
        this.context = context;
        this.Date =date;
        this.event =event;
    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
      View view=  inflater.inflate(R.layout.single_cell_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Date,event;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Date= itemView.findViewById(R.id.Date);
            event=itemView.findViewById(R.id.eventShow);
        }
    }
}
