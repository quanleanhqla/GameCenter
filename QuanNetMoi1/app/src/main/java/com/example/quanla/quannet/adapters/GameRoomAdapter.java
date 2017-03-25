package com.example.quanla.quannet.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.adapters.viewholders.GameRoomHolder;
import com.example.quanla.quannet.database.DbContext;
import com.example.quanla.quannet.database.models.GameRoom;

/**
 * Created by QuanLA on 3/19/2017.
 */

public class GameRoomAdapter extends RecyclerView.Adapter<GameRoomHolder> {
    private Context context;

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public GameRoomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_main, parent, false);

        GameRoomHolder gameRoomHolder = new GameRoomHolder(itemView);
        return gameRoomHolder;
    }

    @Override
    public void onBindViewHolder(GameRoomHolder holder, int position) {
        GameRoom gameRoom = DbContext.instance.getAllRooms().get(position);

        holder.bind(gameRoom, context);
    }

    @Override
    public int getItemCount() {
        return DbContext.instance.getAllRooms().size();
    }
}
