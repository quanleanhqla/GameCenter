package com.example.quanla.quannet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.activities.MainActivity;
import com.example.quanla.quannet.adapters.PhotoAdapter;
import com.example.quanla.quannet.database.models.GameRoom;
import com.example.quanla.quannet.events.ActivityReplaceEvent;
import com.example.quanla.quannet.events.ReplaceFragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.call)
    Button btn_call;

    @BindView(R.id.computer)
    Button btn_computer;

    @BindView(R.id.rv_anh)
    RecyclerView rv_anh;

    private GameRoom gameRoom;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        PhotoAdapter photoAdapter = new PhotoAdapter();
        rv_anh.setAdapter(photoAdapter);
        rv_anh.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL, false));



        btn_computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ReplaceFragmentEvent(new ComFragment(), true));
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void replace(ActivityReplaceEvent activityReplaceEvent) {
        gameRoom = activityReplaceEvent.getGameRoom();
        tv_address.setText(activityReplaceEvent.getGameRoom().getAddress());
        tv_title.setText(activityReplaceEvent.getGameRoom().getTitle());
        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(gameRoom.getTitle());
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().removeAllStickyEvents();
        super.onDestroy();
    }
}
