package com.example.quanla.quannet.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.quanla.quannet.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewActivity extends AppCompatActivity {

    private Uri imageUri;

    @BindView(R.id.ib_photo)
    ImageButton ibPhoto;

    @BindView(R.id.edt_title)
    EditText edtTitle;

    @BindView(R.id.edt_address)
    EditText edtAddress;

    @BindView(R.id.edt_gia)
    EditText edtGia;

    @BindView(R.id.cb_food)
    CheckBox cbFood;

    @BindView(R.id.cb_night)
    CheckBox cbNight;

    @BindView(R.id.cb_park)
    CheckBox cbPark;

    @BindView(R.id.cb_smoke)
    CheckBox cbSmoke;

    @BindView(R.id.post)
    Button btnPost;

    private static final int GALLERY_REQUEST = 1;
    private ProgressDialog progress;
    private StorageReference storage;
    private DatabaseReference data;

    private boolean checkFood;
    private boolean checkNight;
    private boolean checkPark;
    private boolean checkSmoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);

        progress = new ProgressDialog(this);
        storage = FirebaseStorage.getInstance().getReference();
        data = FirebaseDatabase.getInstance().getReference().child("Update");

        cbFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkFood = cbFood.isChecked();
            }
        });

        cbNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkNight = cbNight.isChecked();
            }
        });

        cbPark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkPark = cbPark.isChecked();
            }
        });

        cbSmoke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkSmoke = cbSmoke.isChecked();
            }
        });


        ibPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Request mobile to get content
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setMessage("Posting to Blog...");
                progress.show();

                final String title = edtTitle.getText().toString().trim();
                final String address = edtAddress.getText().toString().trim();
                final String price = edtGia.getText().toString().trim();

                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(address) && imageUri != null && !TextUtils.isEmpty(price)){
                    StorageReference filePath = storage.child(title).child(imageUri.getLastPathSegment());
                    filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            DatabaseReference newPost = data.child(title);
                            newPost.child("title").setValue(title);
                            newPost.child("address").setValue(address);
                            newPost.child("money").setValue(price);
                            newPost.child("urlImage").setValue(downloadUrl.toString());
                            newPost.child("canFood").setValue(checkFood);
                            newPost.child("canSmoke").setValue(checkSmoke);
                            newPost.child("canNight").setValue(checkNight);
                            newPost.child("canPark").setValue(checkPark);
                            progress.dismiss();
                            onBackPressed();
                        }
                    });
                }
                else Toast.makeText(NewActivity.this, "Nhập ngu vcl", Toast.LENGTH_SHORT).show();
            }
        });




    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Get content
        if(requestCode==GALLERY_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            ibPhoto.setImageURI(imageUri);
        }
    }


}
