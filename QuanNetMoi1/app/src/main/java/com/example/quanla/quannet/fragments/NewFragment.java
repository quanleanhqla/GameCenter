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
import com.example.quanla.quannet.adapters.GameRoomAdapter;
import com.example.quanla.quannet.database.DbContext;
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

    private GameRoomAdapter gameRoomAdapter;
    private final String TAG = "DCM";
    String[] allName = {"Ibma net", "360 Game", "X - MEN Club", "Phoenix Cyber","Game Sieu Toc","Game Online","Syndra Gaming","Victory Game","Box-Game","Tung's Net","Auto Game","Imba Esports Stadium"};
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
                DbContext.instance.clear();
//                GameRoom gameRoom = dataSnapshot.child("360 Game").getValue(GameRoom.class);
//                GameRoom gameRoom1 = dataSnapshot.child("Auto Game").getValue(GameRoom.class);
//                DbContext.instance.add(gameRoom);
//                DbContext.instance.add(gameRoom1);
                for(int i=0; i<allName.length; i++){
                    DbContext.instance.add(dataSnapshot.child(allName[i]).getValue(GameRoom.class));
                }
                Log.e(TAG, String.format("%s", dataSnapshot.getValue()));
                gameRoomAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "d cm");
            }
        });

        gameRoomAdapter = new GameRoomAdapter();
        gameRoomAdapter.setContext(this.getContext());
        recyclerView.setAdapter(gameRoomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;

    }

    @OnClick(R.id.fab)
    public void onClick(){
        startActivity(new Intent(this.getActivity(), NewActivity.class));
    }

}
