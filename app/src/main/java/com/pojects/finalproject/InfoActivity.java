package com.pojects.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        MediaPlayer mp = MediaPlayer.create(this, R.raw.tone);
        mp.start();
        TextView messageBody = findViewById(R.id.result);

        messageBody.setText(getIntent().getStringExtra("message body"));
    }
}