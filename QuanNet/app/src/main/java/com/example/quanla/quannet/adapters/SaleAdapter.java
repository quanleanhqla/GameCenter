package com.example.quanla.quannet.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.adapters.viewholders.GameRoomHolder;
import com.example.quanla.quannet.database.DbSale;
import com.example.quanla.quannet.database.models.GameRoom;
import com.example.quanla.quannet.events.ActivityReplaceEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by QuanLA on 4/19/2017.
 */

public class SaleAdapter extends RecyclerView.Adapter<GameRoomHolder> {

    private Context context;

    @Override
    public GameRoomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_hot, parent, false);

        GameRoomHolder holder = new GameRoomHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(GameRoomHolder holder, int position) {
        final GameRoom gameRoom = DbSale.instance.getAllRooms().get(position);

        holder.bind(gameRoom, context);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new ActivityReplaceEvent(gameRoom));
            }
        });
    }

    @Override
    public int getItemCount() {
        return DbSale.instance.getAllRooms().size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
