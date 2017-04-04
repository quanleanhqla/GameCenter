package com.example.quanla.quannet.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.database.models.GameRoom;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QuanLA on 3/19/2017.
 */

public class GameRoomHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_km)
    TextView tvKm;
    @BindView(R.id.iv_km)
    ImageView ivKm;
    @BindView(R.id.iv_photo)
    ImageView imgPhoto;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.smoke)
    ImageView ivSmoke;
    @BindView(R.id.parking)
    ImageView ivPark;
    @BindView(R.id.food)
    ImageView ivFood;
    public ImageView getImgPhoto() {
        return imgPhoto;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvAddress() {
        return tvAddress;
    }

    public TextView getTvRate() {
        return tvRate;
    }

    public GameRoomHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(GameRoom gameRoom, Context context){
        //Picasso.with(context).load(gameRoom.getUrlImage()).into(imgPhoto);
        imgPhoto.setImageResource(R.drawable.gaixinh);
        tvTitle.setText(gameRoom.getTitle());
        tvAddress.setText(gameRoom.getAddress());
        tvRate.setText(gameRoom.getRate());
        tvKm.setText(gameRoom.getKm()+" km");
        ivKm.setImageResource(R.drawable.ic_cursor);
        ivSmoke.setImageResource(R.drawable.ic_smoke_free_black_24px);
        ivPark.setImageResource(R.drawable.ic_local_parking_black_24px);
        ivFood.setImageResource(R.drawable.ic_plate_fork_and_knife);
    }
}
