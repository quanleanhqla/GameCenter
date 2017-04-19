package com.example.quanla.quannet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.database.DbContextHot;
import com.example.quanla.quannet.database.DbSale;
import com.example.quanla.quannet.database.models.GameRoom;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InitialActivity extends AppCompatActivity {

    private final String TAG = "Test";
    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    String[] allName = {"Playdota Stadium", "Cybox Game Center", "Vikings Gaming", "Pegasus Club Center","GameHome","Gaming House","Imba eSports Stadium","Monaco Game","Colosseum Gaming Center","Only One Airport Gaming","Epic Gaming Center","Game Vip","G5 E-Sport Center","Moon Game", "Nhiệt Game", "Royal Gaming","Arena Gaming Center","Cyzone","H3 Cyber Gaming","Clan 105"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        Log.d(TAG, "DCM CHUNG MAY");
        databaseReference.child("hot").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GameRoom gameRoom = dataSnapshot.getValue(GameRoom.class);
                DbContextHot.instance.add(gameRoom);
                startActivity(new Intent(InitialActivity.this, MainActivity.class));
                if(gameRoom.getKhuyenmai()!=null) DbSale.instance.add(gameRoom);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        startActivity(new Intent(InitialActivity.this, MainActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
