package com.example.quanla.quannet.adapters.viewholders;

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

    @BindView(R.id.iv_check)
    public ImageView ivCheck;

    @BindView(R.id.txt_computer)
    public TextView txtComputer;

    public ComputerHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Computer computer){
        ivComputer.setImageResource(R.drawable.ic_computer_white_24px);
        ivCheck.setImageResource(R.drawable.ic_check_white_24px);
        txtComputer.setText(computer.getNumber()+"");
        if(computer.isAvailable()) ivCheck.setVisibility(View.INVISIBLE);
    }
}
