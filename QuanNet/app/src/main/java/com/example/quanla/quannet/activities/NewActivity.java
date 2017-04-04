package com.example.quanla.quannet.activities;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.database.models.GameRoom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.nlopez.smartlocation.OnGeocodingListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.geocoding.utils.LocationAddress;

public class NewActivity extends AppCompatActivity {
    private static final String TAG = "NewActivity";
    @BindView(R.id.ib_photo)
    ImageButton imageButton;

    @BindView(R.id.ll1)
    LinearLayout ll1;

    @BindView(R.id.iv1)
    ImageView iv1;

    @BindView(R.id.iv2)
    ImageView iv2;

    @BindView(R.id.iv3)
    ImageView iv3;

    @BindView(R.id.iv4)
    ImageView iv4;

    @BindView(R.id.iv5)
    ImageView iv5;

    @BindView(R.id.iv6)
    ImageView iv6;

    @BindView(R.id.edt_title)
    EditText edtTitle;

    @BindView(R.id.edt_address)
    EditText edtAddress;

    @BindView(R.id.edt_detail)
    EditText edtDetail;

    @BindView(R.id.post)
    Button post;
    private DatabaseReference databaseReference;
    private Uri uriImage;
    private String address;
    private String title;

    private static final int GALLERY_REQUEST = 1;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        ButterKnife.bind(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
                imageButton.setVisibility(View.GONE);
                ll1.setVisibility(View.VISIBLE);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = edtAddress.getText().toString();
                title = edtTitle.getText().toString();
                Log.d(TAG, String.format("onClick: %s", address));
                SmartLocation.with(NewActivity.this).geocoding().direct(address, new OnGeocodingListener() {
                    @Override
                    public void onLocationResolved(String s, List<LocationAddress> list) {
                        if (list.size()>0){
                            location = list.get(0).getLocation();
                            Log.d(TAG, String.format("onLocationResolved: %s", location.toString()));
                            GameRoom gameRoom = new GameRoom(null,title,address,"good",location.getLatitude(),location.getLongitude());
                            Log.d(TAG, String.format("onLocationResolved: %s", gameRoom));
                            upNewGameRoom(gameRoom);
                        }

                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            uriImage = data.getData();
            iv1.setImageURI(uriImage);
        }
    }
    private void upNewGameRoom(GameRoom gameRoom){
        Log.d(TAG, "upNewGameRoom: 123");
        databaseReference.child("update").child(gameRoom.getTitle()).setValue(gameRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onBackPressed();
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private Location geoAddress(String address) {
        final Location[] location = new Location[1];

        Log.d(TAG, String.format("geoAddress: %s", location[0].toString()));
        return location[0];
    }
}
