package com.example.quanla.quannet.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.database.models.GameRoom;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("new").child("Imba eSports Stadium").setValue(new GameRoom(null, "Imba eSports Stadium", "152 Phó Đức Chính, Trúc Bạch, Ba Đình, Hà Nội", "Excellent", 21.045606,105.8410282));
//        mDatabase.child("new").child("Monaco Game").setValue(new GameRoom(null, "Monaco Game", "tầng 6, 22 Thành Công, Khu tập thể Bắc Thành Công, Ba Đình, Hà Nội", "Excellent", 21.0221663,105.8136659));
//        mDatabase.child("new").child("Game Vip").setValue(new GameRoom(null, "Game Vip", "69 Phó Đức Chính, Quận Ba Đình, Hà Nội", "Excellent", 21.0483626,105.839595));
//        mDatabase.child("new").child("Arena Gaming Center").setValue(new GameRoom(null, "Arena Gaming Center", "Tầng 5, 52 Chùa Hà, Hà Nội", "Excellent", 21.035984,105.7928853));
//        mDatabase.child("new").child("360 game").setValue(new GameRoom(null, "360 game", "29, Tô Hiệu, Phường Nghĩa Đô,Quận Cầu Giấy, Hà Nội", "Excellent", 21.044935,105.7952233));
//        mDatabase.child("new").child("Quan 360 do game").setValue(new GameRoom(null, "Quán 360 độ game", "77, Đặng Văn Ngữ, Phường Trung Tự, Quận Đống Đa, Hà Nội", "Excellent", 21.01102,105.8312663));
//        mDatabase.child("new").child("Team Tame").setValue(new GameRoom(null, "Team Tame", "115, Đặng Tiến Đông, Phường Trung Liệt, Quận Đống Đa, Hà Nội", "Excellent", 21.012604,105.8211183));

    }




}
