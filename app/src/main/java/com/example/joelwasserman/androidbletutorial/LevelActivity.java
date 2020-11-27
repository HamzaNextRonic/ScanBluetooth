package com.example.joelwasserman.androidbletutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LevelActivity extends AppCompatActivity {



   Button backword,exit, temperatureBtn;
   String valueTemperature;
   RelativeLayout relave1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);



        temperatureBtn = findViewById(R.id.temperatureBtn);
        relave1 = findViewById(R.id.relative1);

        exit = findViewById(R.id.exit);
        backword = findViewById(R.id.backword);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        backword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
       valueTemperature = getIntent().getStringExtra("valueTemperature");

        if(Double.valueOf(valueTemperature) > 8){
            relave1.setBackground("#fffff");
        }



    }
}