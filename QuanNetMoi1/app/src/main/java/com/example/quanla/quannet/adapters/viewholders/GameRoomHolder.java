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
    @BindView(R.id.iv_photo)
    ImageView imgPhoto;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_rate)
    TextView tvRate;

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
        Picasso.with(context).load(gameRoom.getUrlImage()).into(imgPhoto);
        tvTitle.setText(gameRoom.getTitle());
        tvAddress.setText(gameRoom.getAddress());
        tvRate.setText(gameRoom.getRate());
    }
}
