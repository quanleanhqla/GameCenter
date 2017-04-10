package com.example.quanla.quannet.activities;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.adapters.CommentAdapter;
import com.example.quanla.quannet.database.DbContextHot;
import com.example.quanla.quannet.database.models.Comments;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentActivity extends AppCompatActivity {
    private static final String TAG = "CommenActivity";
    @BindView(R.id.rv_cmt)
    RecyclerView recyclerView;
    @BindView(R.id.tv_cmt)
    TextView textView;
    private DatabaseReference databaseReference;
    private String title;
    private CommentAdapter commentAdapter;
    private Comments comments;
    private ArrayList<Comments> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        arrayList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        EventBus.getDefault().register(this);

        if (DbContextHot.instance.allComment()!= null)
        DbContextHot.instance.comments.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("comment").child(title).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Comments comments = dataSnapshot.getValue(Comments.class);
               if (comments.getUri()!=null) DbContextHot.instance.addComment(comments);
//                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        commentAdapter = new CommentAdapter();
        commentAdapter.setContext(this);
        Log.d(TAG, String.format("onCreate: %s", title));

        recyclerView.setAdapter(commentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (DbContextHot.instance.comments.size()==0)
            textView.setVisibility(View.VISIBLE);
        else textView.setVisibility(View.INVISIBLE);
    }


    @Subscribe(sticky = true)
    public void moveFromDetail(String tittle){
        this.title = tittle;
        EventBus.getDefault().removeAllStickyEvents();
    }
    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }
}
