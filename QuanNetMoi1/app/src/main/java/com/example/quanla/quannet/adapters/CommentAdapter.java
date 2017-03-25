package com.example.quanla.quannet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.adapters.viewholders.CommentHolder;
import com.example.quanla.quannet.adapters.viewholders.GameRoomHolder;
import com.example.quanla.quannet.database.DbContext;

/**
 * Created by Quoc Viet Dang on 3/21/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {
    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_comment, parent, false);

        CommentHolder commentHolder = new CommentHolder(itemView);
        return commentHolder;

    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        String string = DbContext.instance.allComment().get(position);
        holder.bind(string);
    }

    @Override
    public int getItemCount() {
        return DbContext.instance.allComment().size();
    }
}
