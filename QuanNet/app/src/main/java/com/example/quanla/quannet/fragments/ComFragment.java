package com.example.quanla.quannet.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.activities.MainActivity;
import com.example.quanla.quannet.adapters.ComputerAdapter;
import com.example.quanla.quannet.database.models.GameRoom;
import com.example.quanla.quannet.events.ActivityReplaceEvent;
import com.example.quanla.quannet.events.FromInfoEvent;
import com.example.quanla.quannet.events.ReplaceFragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComFragment extends Fragment {


    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.bt_call)
    Button btn_call;

    private int check =0;

    private GameRoom gameRoom;

    ComputerAdapter computerAdapter;

    public ComFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_com, container, false);
        ButterKnife.bind(this, view);

        //MainActivity.logo.setVisibility(View.INVISIBLE);
        computerAdapter = new ComputerAdapter();
        rv.setAdapter(computerAdapter);
        rv.setLayoutManager(new GridLayoutManager(this.getContext(), 5));
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void replace(ActivityReplaceEvent activityReplaceEvent) {
        gameRoom = activityReplaceEvent.getGameRoom();
        tv_title.setText(activityReplaceEvent.getGameRoom().getTitle());

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void replace(FromInfoEvent activityReplaceEvent) {
        gameRoom = activityReplaceEvent.getGameRoom();
        tv_title.setText(activityReplaceEvent.getGameRoom().getTitle());
        check = 1;

    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void call() {
        Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
        callIntent.setData(Uri.parse("tel:900"));    //this is the phone number calling
        //check permission
        //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
        //the system asks the user to grant approval.
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //request permission from user if the app hasn't got the required permission
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                    10);
            return;
        } else {     //have got permission
            try {
                startActivity(callIntent);  //call activity and make phone call
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        if(check != 1) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.location_menu, menu);
        }

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
