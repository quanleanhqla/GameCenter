package com.example.quanla.quannet.adapters.viewholders;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.database.models.Computer;
import com.example.quanla.quannet.database.models.GameRoom;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QuanLA on 3/31/2017.
 */

public class ComputerHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_computer)
    public ImageView ivComputer;

    @BindView(R.id.txt_computer)
    public TextView txtComputer;

    public ComputerHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Computer computer){
        ivComputer.setImageResource(R.drawable.ic_computer_white_24px);
        txtComputer.setText("Máy "+computer.getNumber()+"");
        txtComputer.setTextColor(Color.parseColor("#F44336"));
        if(computer.isAvailable()){
            ivComputer.setImageResource(R.drawable.ic_computer_black_24px);
            txtComputer.setTextColor(Color.parseColor("#9E9E9E"));
        }
    }
}
