package com.example.quanla.quannet.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

    @BindView(R.id.tv_quadem)
    TextView tv_quadem;

    @BindView(R.id.iv_smoke)
    ImageView iv_smoke;

    @BindView(R.id.tv_hutthuoc)
    TextView tv_smoke;

    @BindView(R.id.iv_park)
    ImageView iv_park;

    @BindView(R.id.tv_park)
    TextView tv_park;

    @BindView(R.id.iv_food)
    ImageView iv_food;

    @BindView(R.id.tv_food)
    TextView tv_food;

    @BindView(R.id.tv_momey)
    TextView tv_money;

    @BindView(R.id.vv)
    View vv;

    @BindView(R.id.tv_khuyenmai)
    TextView tv_khuyenmai;

    @BindView(R.id.tv_rate)
    TextView tv_rate;


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
        tv_money.setText(gameRoom.getMoney());
        tv_rate.setText(gameRoom.getRate()+"");
        if(gameRoom.getKhuyenmai()!=null){
            vv.setVisibility(View.VISIBLE);
            tv_khuyenmai.setVisibility(View.VISIBLE);
        }
        if(!gameRoom.isCanFood()){
            iv_food.setImageResource(R.drawable.ic_noplate_fork_and_knife);
            tv_food.setText("Không đồ ăn");
        }
        if(!gameRoom.isCanNight()){
            tv_quadem.setText("Không qua đêm");
        }
        if(!gameRoom.isCanPark()){
            iv_park.setImageResource(R.drawable.ic_noparking_sign_);
            tv_park.setText("Không chỗ để xe");
        }
        if(!gameRoom.isCanSmoke()){
            iv_smoke.setImageResource(R.drawable.ic_no_cigarette);
            tv_smoke.setText("Không hút thuốc");
        }
        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(gameRoom.getTitle());
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().removeAllStickyEvents();
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.location_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mn_location){
            EventBus.getDefault().post(new ReplaceFragmentEvent(new MapFragment(), true));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
