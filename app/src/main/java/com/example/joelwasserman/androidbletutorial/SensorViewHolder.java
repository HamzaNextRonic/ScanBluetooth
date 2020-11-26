package com.example.joelwasserman.androidbletutorial;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joelwasserman.androidbletutorial.interfece.ItemClickListner;

public class SensorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView valueTemperature,nameSensor,hymedityValue,notificationTextView,vebration;
    public CardView cardView;
    public ItemClickListner listner;

    public SensorViewHolder(@NonNull View itemView) {
        super(itemView);

        valueTemperature = itemView.findViewById(R.id.valueTemperature);
        nameSensor = itemView.findViewById(R.id.nameSensor);
        hymedityValue = itemView.findViewById(R.id.hymedityValue);
        notificationTextView = itemView.findViewById(R.id.notificationTextView);
        vebration = itemView.findViewById(R.id.vebration);


        cardView = itemView.findViewById(R.id.cardSensor);

    }



    public void setItemClickListner (ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }
}
