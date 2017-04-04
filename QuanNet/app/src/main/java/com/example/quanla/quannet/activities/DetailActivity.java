package com.example.quanla.quannet.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.adapters.CommentAdapter;
import com.example.quanla.quannet.adapters.PhotoAdapter;
import com.example.quanla.quannet.database.models.GameRoom;
import com.example.quanla.quannet.events.ActivityReplaceEvent;
import com.example.quanla.quannet.events.MoveToMap;
import com.example.quanla.quannet.events.MoveToMapEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_image)
    ImageView ivImage;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_address)
    TextView tvAddress;

    @BindView(R.id.tv_momey)
    TextView tvMoney;

    @BindView(R.id.txt_computer)
    TextView tvComputer;



    PhotoAdapter photoAdapter;
    CommentAdapter commentAdapter;
    String comment;
    private String TAG = "DetailActivity";

    private GameRoom gameRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        tvComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailActivity.this, RoomActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void replace(ActivityReplaceEvent activityReplaceEvent) {
        gameRoom = activityReplaceEvent.getGameRoom();
        tvAddress.setText(activityReplaceEvent.getGameRoom().getAddress());
        tvTitle.setText(activityReplaceEvent.getGameRoom().getTitle());
        Log.d(TAG, "setDetail: 1");
        EventBus.getDefault().removeAllStickyEvents();

    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mn_location) {
            Log.d(TAG, "onOptionsItemSelected");
            EventBus.getDefault().postSticky(new MoveToMapEvent(gameRoom, MoveToMap.FROMDETAIL));
            startActivity(new Intent(DetailActivity.this, MapsActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void call() {
//        try {
//            String phoneNumber = "0979301596";
//            Uri uri = Uri.parse("tel:" + phoneNumber);
//            Intent it = new Intent(Intent.ACTION_CALL);
//            it.setData(uri);
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//
//            startActivity(it);
//
//        }
//        catch (ActivityNotFoundException e) {
//            // Exceptions
//        }
//    }
}
