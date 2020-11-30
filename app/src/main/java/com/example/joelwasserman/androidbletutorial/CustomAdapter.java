package com.example.joelwasserman.androidbletutorial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joelwasserman.androidbletutorial.model.DataModel;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private ArrayList<DataModel> dataSet;


    public CustomAdapter() {
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView valueTemperature,nameSensor,hymedityValue,notificationTextView,vebration;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.valueTemperature = (TextView) itemView.findViewById(R.id.valueTemperature);
            this.nameSensor = (TextView) itemView.findViewById(R.id.nameSensor);
            this.hymedityValue = (TextView) itemView.findViewById(R.id.hymedityValue);
            this.notificationTextView = (TextView) itemView.findViewById(R.id.notificationTextView);
            this.vebration = (TextView) itemView.findViewById(R.id.vebration);
        }
    }

    public CustomAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView nameSensor = holder.nameSensor;
        TextView valueTemperature = holder.valueTemperature;
        TextView hymedityValue = holder.hymedityValue;
        TextView notificationTextView = holder.notificationTextView;
        //TextView vebration = holder.vebration;


        nameSensor.setText(dataSet.get(listPosition).getName());
        valueTemperature.setText(dataSet.get(listPosition).getValeur());
        hymedityValue.setText(dataSet.get(listPosition).getTime());
        notificationTextView.setText(dataSet.get(listPosition).getAddress());


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
