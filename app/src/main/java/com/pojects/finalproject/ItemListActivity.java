package com.pojects.finalproject;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    private DBHelper db;
    private ArrayList<String> userID, title, gender, body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        db = new DBHelper(this);
        userID = new ArrayList<>();
        gender = new ArrayList<>();
        title = new ArrayList<>();
        body = new ArrayList<>();
        storeData();
        ListView lst = findViewById(R.id.listOfItems);
        HashMap<String, String> res = new HashMap<>();
        for (int i = 0; i < userID.size(); i++) {
            res.put(userID.get(i), title.get(i));
        }
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listItems, R.layout.single_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.userIdItem, R.id.messageTitleItem}
        );

        for (String key : res.keySet()) {
            HashMap<String, String> pair = new HashMap<>();
            pair.put("First Line", key);
            pair.put("Second Line", res.get(key));
            listItems.add(pair);
        }

        lst.setAdapter(adapter);
        Toast.makeText(this, db.getStatus(), Toast.LENGTH_SHORT).show();
        lst.setOnItemClickListener((parent, view, position, id) -> {
            Intent nextPage = new Intent(getApplicationContext(), InfoActivity.class);
            nextPage.putExtra("message body", body.get(position));
            startActivity(nextPage);
        });
    }

    private void storeData() {
        Cursor cursor = db.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "The Database is empty.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                userID.add(cursor.getString(0));
                title.add(cursor.getString(1));
                body.add(cursor.getString(2));
                gender.add(cursor.getString(3));
            }
        }
    }
}