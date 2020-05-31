package com.example.user.bmicalculator;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FHistoryActivity extends AppCompatActivity {

    ListView lvShow;
    ArrayList<String> f = new ArrayList<>();
    ArrayAdapter ad;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fhistory);

        lvShow = (ListView)findViewById(R.id.lvShow);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("info");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                f.clear();;
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    //Info information = d.getValue(Info.class);
                   // f.add(information);
                    String result = d.getValue().toString();
                    int size = result.length();
                    String temp = result.substring(1,size-1);
                    f.add(temp);
                }

                ad = new ArrayAdapter(FHistoryActivity.this,android.R.layout.simple_list_item_1,f);
                lvShow.setAdapter(ad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
