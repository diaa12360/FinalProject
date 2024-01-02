package com.pojects.finalproject;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ItemListActivity extends AppCompatActivity {

    DbContext db;
    ArrayList<String> user_id, title, gender, body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        db = new DbContext(this);
        user_id = new ArrayList<>();
        gender = new ArrayList<>();
        title = new ArrayList<>();
        body = new ArrayList<>();


        storeData();

        ListView lst = findViewById(R.id.listOfItems);


        HashMap<String, String> res = new HashMap<>();

        for(int i = 0 ; i < user_id.size() ; i += 1){
            res.put(user_id.get(i) , title.get(i));
        }

        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.single_item, new String[]{
                "First Line", "Second Line"
        }, new int[]{R.id.userIdItem, R.id.messageTitleItem})
                ;

        for (Map.Entry<String, String> stringStringEntry : res.entrySet()) {
            HashMap<String, String> ans = new HashMap<>();
            Map.Entry pair = (Map.Entry) stringStringEntry;
            ans.put("First Line", pair.getKey().toString());
            ans.put("Second Line", pair.getValue().toString());
            listItems.add(ans);
        }

        lst.setAdapter(adapter);

        Toast.makeText(this, db.getStatus(), Toast.LENGTH_SHORT).show();

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent nextPage = new Intent(getApplicationContext(), InfoActivity.class);
                nextPage.putExtra("message body", body.get(position));
                startActivity(nextPage);
            }
        });
    }

    void storeData(){
        Cursor cursor = db.readAllData();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "The Database is empty.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                user_id.add(cursor.getString(0));
                title.add(cursor.getString(1));
                body.add(cursor.getString(2));
                gender.add(cursor.getString(3));
            }
        }
    }
}