package com.example.quanla.quannet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.activities.NewActivity;
import com.example.quanla.quannet.adapters.NewAdapter;
import com.example.quanla.quannet.database.DbContextHot;
import com.example.quanla.quannet.database.DbContextNew;
import com.example.quanla.quannet.database.models.Comments;
import com.example.quanla.quannet.database.models.GameRoom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by QuanLA on 3/19/2017.
 */

public class NewFragment extends Fragment {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private NewAdapter newAdapter;
    private final String TAG = "DCM";
    String[] allName = {"Imba eSports Stadium", "Monaco Game", "Game Vip","Arena Gaming Center","360 game","Quan 360 do game","Team Tame"};
    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference =firebaseDatabase.getReference();
    @BindView(R.id.rv1)
    RecyclerView recyclerView;

    public NewFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        ButterKnife.bind(this, view);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DbContextNew.instance.clear();
//                GameRoom gameRoom = dataSnapshot.child("360 Game").getValue(GameRoom.class);
//                GameRoom gameRoom1 = dataSnapshot.child("Auto Game").getValue(GameRoom.class);
//                DbContextHot.instance.add(gameRoom);
//                DbContextHot.instance.add(gameRoom1);
                for(int i=0; i<allName.length; i++){
                    DbContextNew.instance.add(dataSnapshot.child("new").child(allName[i]).getValue(GameRoom.class));
                }

//                for (GameRoom gameRoom : DbContextNew.instance.getAllRooms())
//                    databaseReference.child("comment").child(gameRoom.getTitle()).push().setValue(new Comments("0","0"));
                Log.e(TAG, String.format("abc: %s", dataSnapshot.child("new")));
                Log.e(TAG, String.format("def: %s", dataSnapshot.child("new").getChildrenCount()));
                Log.e(TAG, String.format("%s", DbContextNew.instance.getAllRooms().toString()));
                newAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "d cm");
            }
        });

        newAdapter = new NewAdapter();
        newAdapter.setContext(this.getContext());
        recyclerView.setAdapter(newAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;

    }

    @OnClick(R.id.fab)
    public void onClick(){
        startActivity(new Intent(this.getActivity(), NewActivity.class));
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        EventBus.getDefault().unregister(this);
//    }
//
//    @Subscribe(sticky = true)
//    public void replace(ActivityReplaceEvent activityReplaceEvent){
//        startActivity(new Intent(this.getActivity(), DetailActivity.class));
//    }
}
