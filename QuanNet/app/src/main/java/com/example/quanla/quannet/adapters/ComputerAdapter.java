package com.example.quanla.quannet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.adapters.viewholders.ComputerHolder;
import com.example.quanla.quannet.database.DbComputer;
import com.example.quanla.quannet.database.models.Computer;
import com.example.quanla.quannet.database.models.GameRoom;

/**
 * Created by QuanLA on 3/31/2017.
 */

public class ComputerAdapter extends RecyclerView.Adapter<ComputerHolder> {
    @Override
    public ComputerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_computer, parent, false);

        ComputerHolder computerHolder = new ComputerHolder(itemView);
        return computerHolder;
    }

    @Override
    public void onBindViewHolder(ComputerHolder holder, int position) {
        Computer computer = DbComputer.instance.getComputerArrayList().get(position);

        holder.bind(computer);
    }

    @Override
    public int getItemCount() {
        return DbComputer.instance.getComputerArrayList().size();
    }
}
