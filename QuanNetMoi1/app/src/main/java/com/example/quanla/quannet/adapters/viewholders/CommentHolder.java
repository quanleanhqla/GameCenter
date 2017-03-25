package com.example.quanla.quannet.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.database.DbContext;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Quoc Viet Dang on 3/21/2017.
 */

public class CommentHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    public CommentHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    public void bind(String string){
        tv_comment.setText(string);
    }
}
