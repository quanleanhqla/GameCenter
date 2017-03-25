package com.example.quanla.quannet.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.quanla.quannet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewActivity extends AppCompatActivity {

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

    private Uri uriImage;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            uriImage = data.getData();
            iv1.setImageURI(uriImage);
        }
    }
}
