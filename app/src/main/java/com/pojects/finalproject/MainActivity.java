package com.pojects.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final String myPreference = "myPreference";
    private static final String userId = "UserId";
    private static final String messageBody = "MessageBody";
    private static String userIdText, messageBodyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(myPreference,
                Context.MODE_PRIVATE);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> changeImage());
    }

    public void sqliteBtnOnClick(View v){
        EditText userIdText = findViewById(R.id.userIdText);
        EditText messageBodyText = findViewById(R.id.messageBodyText);
        EditText messageTitleText= findViewById(R.id.messageTitleText);
        RadioButton male = findViewById(R.id.maleRadioButton);
        RadioButton female = findViewById(R.id.femaleRadioButton);

        String gender = "";

        if(male.isChecked()){
            gender = "Male";
        }else {
            if(female.isChecked()){
                gender = "Female";
            }
        }

        try {
            DbContext db = new DbContext(MainActivity.this);
            db.addMessage(userIdText.getText().toString(), gender, messageTitleText.getText().toString(), messageBodyText.getText().toString());
        }catch (Exception ex){
            Log.d("Database exception", ex.toString());
        }

        Intent nextPage = new Intent(this, ItemListActivity.class);
        startActivity(nextPage);
    }
    public void spBtnOnClick(View v){
        save(v);
        get(v);
        new AlertDialog.Builder(this)
                .setTitle("Review Message")
                .setMessage("User ID: " + userIdText + "\nMessage: " + messageBodyText)
                .setNegativeButton(android.R.string.ok, null)
                .show();
    }

    public void changeImage() {
        RadioButton male = findViewById(R.id.maleRadioButton);
        RadioButton female = findViewById(R.id.femaleRadioButton);
        ImageView img= (ImageView) findViewById(R.id.imageView);

        if(male.isChecked()){
            img.setImageResource(R.drawable.male);
        }else {
            if(female.isChecked()){
                img.setImageResource(R.drawable.female);
            }
        }
    }

    public void save(View v){
        EditText userIdText = findViewById(R.id.userIdText);
        EditText messageBodyText = findViewById(R.id.messageBodyText);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userId, userIdText.getText().toString());
        editor.putString(messageBody, messageBodyText.getText().toString());
        editor.apply();
    }

    public void get(View v){
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(userId)) {
            userIdText = sharedPreferences.getString(userId, "");
        }
        if (sharedPreferences.contains(messageBody)) {
            messageBodyText = sharedPreferences.getString(messageBody, "");
        }
    }
}