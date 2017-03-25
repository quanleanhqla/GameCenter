package com.example.quanla.quannet.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.database.DbContext;
import com.example.quanla.quannet.database.models.GameRoom;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AccountActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


    }



}
