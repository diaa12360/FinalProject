package com.pojects.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String myPreference = "app_prefs";
    private static final String userId = "UserId";
    private static final String messageBody = "MessageBody";
    private static String userIdString, messageBodyString;
    private EditText userIdText;
    private EditText messageBodyText;
    private EditText messageTitleText;
    private RadioButton male;
    private RadioButton female;
    private ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(myPreference,
                Context.MODE_PRIVATE);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        userIdText = findViewById(R.id.userIdText);
        messageBodyText = findViewById(R.id.messageBodyText);
        messageTitleText = findViewById(R.id.messageTitleText);
        male = findViewById(R.id.maleRadioButton);
        female = findViewById(R.id.femaleRadioButton);
        img = findViewById(R.id.imageView);
        radioGroup.setOnCheckedChangeListener((group, checkedId) ->
                img.setImageResource(
                        male.isChecked() ? R.drawable.baseline_male_24 : female.isChecked() ?
                                R.drawable.baseline_female_24 : null
                )
        );
    }

    public void sqliteBtnOnClick(View v) {
        String gender = "";
        if (male.isChecked()) {
            gender = "Male";
        } else if (female.isChecked()) {
            gender = "Female";
        }
        try {
            DBHelper db = new DBHelper(MainActivity.this);
            db.addMessage(
                    userIdText.getText().toString(),
                    gender,
                    messageTitleText.getText().toString(),
                    messageBodyText.getText().toString()
            );
        } catch (Exception ex) {
            Log.d("Database exception", ex.toString());
        }
        Intent nextPage = new Intent(this, ItemListActivity.class);
        startActivity(nextPage);
    }

    public void spBtnOnClick(View v) {
        //Save in app_prefs
        sharedPreferences.edit()
                .putString(userId, userIdText.getText().toString())
                .putString(messageBody, messageBodyText.getText().toString())
                .apply();
        //Get from app_prefs
        if (sharedPreferences.contains(userId)) {
            userIdString = sharedPreferences.getString(userId, "");
        }
        if (sharedPreferences.contains(messageBody)) {
            messageBodyString = sharedPreferences.getString(messageBody, "");
        }
        //show in AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Review Message")
                .setMessage("User ID: " + userIdString + "\nMessage: " + messageBodyString)
                .setNegativeButton(android.R.string.ok, null)
                .show();
    }
}