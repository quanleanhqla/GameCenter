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
        ImageView imHinh = (ImageView) row.findViewById(R.id.imageView);
        ImageView imLocation = (ImageView) row.findViewById(R.id.img_location);
        TextView tvName = (TextView) row.findViewById(R.id.tv_name);
        TextView tvDiaChi = (TextView) row.findViewById(R.id.tv_diachi);
        TextView tvKhoangCach = (TextView) row.findViewById(R.id.tv_khoangcach);

        imLocation.setImageResource(R.drawable.ic_cursor);
        imHinh.setImageResource(R.drawable.quannet);
        tvKhoangCach.setText(String.format("%.2f", met)+" km");
        tvName.setText(gameRoom.getTitle());
        tvDiaChi.setText(gameRoom.getAddress());

        return row;
    }

}
