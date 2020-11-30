package com.example.joelwasserman.androidbletutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
       /****/
       //valueTemperature="20.5";

        temperatureBtn.setText(valueTemperature+" °C");
        if(Double.valueOf(valueTemperature) > 20){

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Bitmap btm = BitmapFactory.decodeResource(this.getResources(),R.drawable.warning);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setContentTitle("عنوان الرسالة")
                    .setContentText("la Temperature  depasse 8")
                    .setSmallIcon(R.drawable.warning)
                    .setLargeIcon(btm)
                    .setAutoCancel(true)
                    .setNumber(1);

            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
            builder.setVibrate(new long[] {500, 1000, 500, 1000, 500});
            builder.setSound(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sound_notification));

            notificationManager.notify(1,builder.build());



            relave1.setBackgroundResource(R.color.redBackground);
            temperatureBtn.setBackgroundResource(R.drawable.redcercle);
            temperatureBtn.setText(valueTemperature+" °C");

        }



    }
}