package com.example.quanla.quannet.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.activities.MainActivity;
import com.example.quanla.quannet.adapters.CommentAdapter;
import com.example.quanla.quannet.adapters.PhotoAdapter;
import com.example.quanla.quannet.database.DbContextHot;
import com.example.quanla.quannet.database.models.Comments;
import com.example.quanla.quannet.database.models.GameRoom;
import com.example.quanla.quannet.events.ActivityReplaceEvent;
import com.example.quanla.quannet.events.FromInfoEvent;
import com.example.quanla.quannet.events.ImageProfile;
import com.example.quanla.quannet.events.ReplaceFragmentEvent;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    public static  final String TAG ="DetailFragment";
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.cv4)
    CardView cv;


    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.call)
    Button btn_call;

    @BindView(R.id.computer)
    Button btn_computer;

    @BindView(R.id.rv_anh)
    RecyclerView rv_anh;

    @BindView(R.id.tv_quadem)
    TextView tv_quadem;

    @BindView(R.id.iv_smoke)
    ImageView iv_smoke;

    @BindView(R.id.tv_hutthuoc)
    TextView tv_smoke;

    @BindView(R.id.iv_park)
    ImageView iv_park;

    @BindView(R.id.tv_park)
    TextView tv_park;

    @BindView(R.id.iv_food)
    ImageView iv_food;

    @BindView(R.id.tv_food)
    TextView tv_food;

    @BindView(R.id.tv_momey)
    TextView tv_money;

    @BindView(R.id.tv_khuyenmai)
    TextView tv_khuyenmai;

    @BindView(R.id.tv_rate)
    TextView tv_rate;

    @BindView(R.id.rv_cmt)
    RecyclerView recyclerView;
    @BindView(R.id.ll6)
    LinearLayout ll6;
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.im_profile)
    ImageView imageView;
    @BindView(R.id.et_cmt)
    EditText etcmt;
    @BindView(R.id.bt_login)
    Button btLogin;
    private DatabaseReference databaseReference;
    private String title;
    private CommentAdapter commentAdapter;
    private Comments comments;
    private ArrayList<Comments> arrayList;
    private boolean aBoolean= true;
    FragmentManager fm ;
    BlankFragment editNameDialogFragment ;

    private int check = 0;

    private GameRoom gameRoom;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
            btLogin.setVisibility(View.INVISIBLE);
            Picasso.with(this.getContext()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(imageView);
        }else {
            ll6.setVisibility(View.INVISIBLE);
        }
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        //MainActivity.logo.setVisibility(View.INVISIBLE);
        arrayList = new ArrayList<>();
        fm = getActivity().getSupportFragmentManager();
        editNameDialogFragment = BlankFragment.newInstance("Đăng nhập");
        editNameDialogFragment.setLoginSuccesListener(new BlankFragment.LoginSuccesListener() {
            @Override
            public void loginSucces(ImageProfile imageProfile) {
                Log.d(TAG, "setImageProfile: ");
                ll6.setVisibility(View.VISIBLE);
                btLogin.setVisibility(View.INVISIBLE);
                Picasso.with(getContext()).load(imageProfile.getUri()).into(imageView);
            }
        });
        PhotoAdapter photoAdapter = new PhotoAdapter();
        rv_anh.setAdapter(photoAdapter);
        rv_anh.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL, false));
        if (DbContextHot.instance.allComment()!= null)
            DbContextHot.instance.comments.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, String.format("onCreateView: %s", title));

        etcmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser()==null) showEditDialog();

            }
        });
        etcmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                          }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
                try {
                    comments = new Comments(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),etcmt.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
                    if (comments != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
                        databaseReference.child("comment").child(tv_title.getText().toString()).push().setValue(comments).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "onComplete: ");
                                    Toast.makeText(getContext(), "Đăng bình luận thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d(TAG, String.format("onComplete: %s", task.getException().toString()));
                                    Toast.makeText(getContext(), "Đăng bình luận thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                    etcmt.setText(null);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e.toString());
                }
            }
        });




        Log.d(TAG, String.format("onCreate: %s", title));
        commentAdapter = new CommentAdapter();
        commentAdapter.setContext(this.getContext());

        recyclerView.setAdapter(commentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));



        btn_computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ReplaceFragmentEvent(new ComFragment(), true));
            }
        });




        return view;
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void replace(ActivityReplaceEvent activityReplaceEvent) {
        gameRoom = activityReplaceEvent.getGameRoom();
        this.title = gameRoom.getTitle();
        tv_address.setText(activityReplaceEvent.getGameRoom().getAddress());
        tv_title.setText(activityReplaceEvent.getGameRoom().getTitle());
        tv_money.setText(gameRoom.getMoney());
        tv_rate.setText(gameRoom.getRate()+"");
        if(gameRoom.getKhuyenmai()!=null){
            cv.setVisibility(View.VISIBLE);
            tv_khuyenmai.setText(gameRoom.getKhuyenmai());
        }
        if(!gameRoom.isCanFood()){
            iv_food.setImageResource(R.drawable.ic_noplate_fork_and_knife);
            tv_food.setText("Không đồ ăn");
        }
        if(!gameRoom.isCanNight()){
            tv_quadem.setText("Không qua đêm");
        }
        if(!gameRoom.isCanPark()){
            iv_park.setImageResource(R.drawable.ic_noparking_sign_);
            tv_park.setText("Không chỗ để xe");
        }
        if(!gameRoom.isCanSmoke()){
            iv_smoke.setImageResource(R.drawable.ic_no_cigarette);
            tv_smoke.setText("Không hút thuốc");
        }
        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(gameRoom.getTitle());
        }
        databaseReference.child("comment").child(title).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Comments comments = dataSnapshot.getValue(Comments.class);
                if (comments.getUri() != null&& comments.getComment()!=null) DbContextHot.instance.addComment(comments);
                Log.d(TAG, String.format("onChildAdded: %s", dataSnapshot.getValue(Comments.class)));
                commentAdapter.notifyDataSetChanged();
                Log.d(TAG, String.format("replace: %s", DbContextHot.instance.comments.size()));
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
                Log.d(TAG, String.format("onCancelled: %s", databaseError.toString()));
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().removeAllStickyEvents();
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        if(check != 1) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.location_menu, menu);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mn_location){
            EventBus.getDefault().post(new ReplaceFragmentEvent(new MapFragment(), true));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEditDialog() {

        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void replace(FromInfoEvent activityReplaceEvent) {
        check = 1;
        gameRoom = activityReplaceEvent.getGameRoom();
        this.title = gameRoom.getTitle();
        tv_address.setText(activityReplaceEvent.getGameRoom().getAddress());
        tv_title.setText(activityReplaceEvent.getGameRoom().getTitle());
        tv_money.setText(gameRoom.getMoney());
        tv_rate.setText(gameRoom.getRate()+"");
        if(gameRoom.getKhuyenmai()!=null){
            cv.setVisibility(View.VISIBLE);
            tv_khuyenmai.setText(gameRoom.getKhuyenmai());
        }
        if(!gameRoom.isCanFood()){
            iv_food.setImageResource(R.drawable.ic_noplate_fork_and_knife);
            tv_food.setText("Không đồ ăn");
        }
        if(!gameRoom.isCanNight()){
            tv_quadem.setText("Không qua đêm");
        }
        if(!gameRoom.isCanPark()){
            iv_park.setImageResource(R.drawable.ic_noparking_sign_);
            tv_park.setText("Không chỗ để xe");
        }
        if(!gameRoom.isCanSmoke()){
            iv_smoke.setImageResource(R.drawable.ic_no_cigarette);
            tv_smoke.setText("Không hút thuốc");
        }
        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(gameRoom.getTitle());
        }
        databaseReference.child("comment").child(title).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Comments comments = dataSnapshot.getValue(Comments.class);
                if (comments.getUri() != null) DbContextHot.instance.addComment(comments);
                Log.d(TAG, String.format("onChildAdded: %s", dataSnapshot.getValue(Comments.class)));
                commentAdapter.notifyDataSetChanged();
                Log.d(TAG, String.format("replace: %s", DbContextHot.instance.comments.size()));
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
                Log.d(TAG, String.format("onCancelled: %s", databaseError.toString()));
            }
        });
    }

}
