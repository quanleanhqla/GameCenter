package com.example.quanla.quannet.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.database.models.Photos;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QuanLA on 3/20/2017.
 */

public class PhotoHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;



    public PhotoHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Photos photos){
        ivPhoto.setImageResource(photos.getImage());
    }
}
