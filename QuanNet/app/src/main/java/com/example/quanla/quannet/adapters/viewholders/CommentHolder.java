package com.example.quanla.quannet.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.database.models.Comments;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Quoc Viet Dang on 3/21/2017.
 */

public class CommentHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.tv_user)
    TextView tv_user;
    public CommentHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    public void bind(Comments comments){
        tv_comment.setText(comments.getComment());
        tv_user.setText(comments.getUsername());
    }
}
