package com.example.quanla.quannet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.database.DbContextHot;
import com.example.quanla.quannet.database.models.GameRoom;
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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                GameRoom gameRoom = dataSnapshot.child("360 Game").getValue(GameRoom.class);
//                GameRoom gameRoom1 = dataSnapshot.child("Auto Game").getValue(GameRoom.class);
//                DbContextHot.instance.add(gameRoom);
//                DbContextHot.instance.add(gameRoom1);
                for(int i=0; i<allName.length; i++){
                    GameRoom gameRoom = dataSnapshot.child("hot").child(allName[i]).getValue(GameRoom.class);
                    Log.d(TAG, String.format("DCM: %s", gameRoom));
//                    Location dest = new Location("Dest");
//                    dest.setLatitude(gameRoom.getLatitude());
//                    dest.setLongitude(gameRoom.getLongitude());
//                    float km = mLastLocation.distanceTo(dest);
//                    gameRoom.setKm(km);
                    DbContextHot.instance.add(gameRoom);
                }
//                for (GameRoom gameRoom : DbContextHot.instance.getAllRooms())
//                    databaseReference.child("comment").child(gameRoom.getTitle()).push().setValue(new Comments("0","0"));
                Log.d(TAG, String.format("%s", dataSnapshot.child("hot").child("Playdota Stadium").getValue(GameRoom.class)));
                Log.d(TAG, (String.format("%s", DbContextHot.instance.getAllRooms().get(0).toString())));
                startActivity(new Intent(InitialActivity.this, MainActivity.class));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
