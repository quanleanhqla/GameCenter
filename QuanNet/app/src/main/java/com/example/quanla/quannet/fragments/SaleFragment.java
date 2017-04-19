package com.example.quanla.quannet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.activities.MainActivity;
import com.example.quanla.quannet.adapters.HotAdapter;
import com.example.quanla.quannet.adapters.SaleAdapter;
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
public class SaleFragment extends Fragment {

    @BindView(R.id.rv_hot)
    RecyclerView rvHot;

    private SaleAdapter hotAdapter;


    public SaleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        ButterKnife.bind(this, view);
        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Phòng máy khuyến mại");
        }

        //MainActivity.logo.setVisibility(View.INVISIBLE);

        hotAdapter = new SaleAdapter();
        hotAdapter.setContext(this.getContext());
        rvHot.setAdapter(hotAdapter);
        rvHot.setLayoutManager(new LinearLayoutManager(this.getContext()));


        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void replace(ActivityReplaceEvent activityReplaceEvent){
        EventBus.getDefault().post(new ReplaceFragmentEvent(new DetailFragment(), true));
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

}
