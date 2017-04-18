package com.example.quanla.quannet.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
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
    @BindView(R.id.smoke)
    ImageView ivSmoke;
    @BindView(R.id.parking)
    ImageView ivPark;
    @BindView(R.id.food)
    ImageView ivFood;
    @BindView(R.id.rtb_map)
    RatingBar rtb;
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
        tvRate.setText(gameRoom.getRate()+"");
        rtb.setRating((float) gameRoom.getRate());

        if(!gameRoom.isCanSmoke()) ivSmoke.setImageResource(R.drawable.ic_no_cigarette);
        else ivSmoke.setImageResource(R.drawable.ic_cigarette);
        if(gameRoom.isCanPark()) ivPark.setImageResource(R.drawable.ic_parking_sign);
        else ivPark.setImageResource(R.drawable.ic_noparking_sign_);
        if(gameRoom.isCanFood()) ivFood.setImageResource(R.drawable.ic_food);
        else ivFood.setImageResource(R.drawable.ic_noplate_fork_and_knife);
    }
}
