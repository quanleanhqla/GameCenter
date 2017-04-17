package com.example.quanla.quannet.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.database.models.GameRoom;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by minhh on 30/03/2017.
 */

public class CustomInfoAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity context;
    private GameRoom gameRoom;
    private double met;

    public CustomInfoAdapter(Activity context, GameRoom gameRoom,double met) {
        this.context = context;
        this.gameRoom = gameRoom;
        this.met = met;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(R.layout.item_map,null);
        row.setPadding(0,7,0,7);
        RatingBar ratingBar = (RatingBar) row.findViewById(R.id.rtb_map);
        ImageView imHinh = (ImageView) row.findViewById(R.id.imageView);
        ImageView imSmoke = (ImageView) row.findViewById(R.id.smoke);
        ImageView imParking = (ImageView) row.findViewById(R.id.parking);
        ImageView imFood = (ImageView) row.findViewById(R.id.food);
        TextView tvName = (TextView) row.findViewById(R.id.tv_name);

        TextView tvDiaChi = (TextView) row.findViewById(R.id.tv_diachi);

        ratingBar.setRating((float) gameRoom.getRate());

        imHinh.setImageResource(R.drawable.quannet);
        imSmoke.setImageResource(R.drawable.ic_smoke_free_black_24px);
        imParking.setImageResource(R.drawable.ic_parking_sign);
        imFood.setImageResource(R.drawable.ic_food);

        tvName.setText(gameRoom.getTitle());
        tvDiaChi.setText(gameRoom.getAddress());

        return row;
    }

}
