package com.example.quanla.quannet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.adapters.viewholders.PhotoHolder;
import com.example.quanla.quannet.database.DbPhoto;
import com.example.quanla.quannet.database.models.Photos;

/**
 * Created by QuanLA on 4/16/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_image, parent, false);

        PhotoHolder photoHolder = new PhotoHolder(view);
        return photoHolder;
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, int position) {
        Photos photo = DbPhoto.instance.getArrayList().get(position);
        holder.bind(photo);
    }

    @Override
    public int getItemCount() {
        return DbPhoto.instance.getArrayList().size();
    }
}
