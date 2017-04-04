package com.example.quanla.quannet.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.drawable.GradientDrawable;
import android.provider.BaseColumns;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.adapters.PagerAdapter;
import com.example.quanla.quannet.database.DbContextHot;
import com.example.quanla.quannet.database.models.GameRoom;
import com.example.quanla.quannet.events.ActivityReplaceEvent;
import com.example.quanla.quannet.events.MoveToMap;
import com.example.quanla.quannet.events.MoveToMapEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.yalantis.guillotine.animation.GuillotineAnimation;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoreActivity extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 250;
    private static final String TAG = "CoreActivity";

    private LinearLayout llProfile;
    private LinearLayout llNear;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;
    @BindView(R.id.sv_search)
    SearchView searchView;
    @BindView(R.id.tv_nameuser)
    TextView textView;
    String suggestionSelecect ;
    String[] allName = {"Playdota Stadium", "Cybox Game Center", "Vikings Gaming", "Pegasus Club Center","GameHome","Gaming House","Imba eSports Stadium","Monaco Game","Colosseum Gaming Center","Only One Airport Gaming","Epic Gaming Center","Game Vip","G5 E-Sport Center","Moon Game", "Nhiệt Game", "Royal Gaming","Arena Gaming Center","Cyzone","H3 Cyber Gaming","Clan 105"};
    private SimpleCursorAdapter cursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);
        ButterKnife.bind(this);

        textView.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        final String[] from = new String[] {"quannet"};
        final int[] to = new int[] {android.R.id.text1};


        cursorAdapter = new SimpleCursorAdapter((Activity)this,
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.function, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();


        llProfile =(LinearLayout) guillotineMenu.findViewById(R.id.profile_group);

        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CoreActivity.this, AccountActivity.class));
            }
        });

        llNear = (LinearLayout) guillotineMenu.findViewById(R.id.near_group);

        llNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new MoveToMapEvent(MoveToMap.FROMNEARME));
                startActivity(new Intent(CoreActivity.this, MapsActivity.class));
            }
        });

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(fragmentManager);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);

        //divider
        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.seperated_color));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }
        searchView.setSuggestionsAdapter(cursorAdapter);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {

                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                CursorAdapter c = searchView.getSuggestionsAdapter();

                Cursor cursor = (Cursor)     searchView.getSuggestionsAdapter().getItem(position);
                suggestionSelecect = cursor.getString(1);
                for (GameRoom gameRoom : DbContextHot.instance.getAllRooms()){
                    if (gameRoom.getTitle().toLowerCase().equals(suggestionSelecect.toLowerCase())) {
                        EventBus.getDefault().postSticky(new ActivityReplaceEvent(gameRoom));
                    }
                }
                Log.d(TAG, String.format("onSuggestionClick: %s", suggestionSelecect));
                searchView.setQuery( suggestionSelecect,false);

                return false;

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                for (GameRoom gameRoom : DbContextHot.instance.getAllRooms()){
                    if (gameRoom.getTitle().toLowerCase().equals(query.toLowerCase())) {
                        EventBus.getDefault().postSticky(new ActivityReplaceEvent(gameRoom));
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                populateAdapter(newText);
                return false;
            }
        });
    }
    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "quannet" });
        for (int i = 0; i< allName.length; i++) {
            if (allName[i].toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[] {i, allName[i]});
            for (String s : allName)
                Log.d(TAG, String.format("populateAdapter:%s ", s));

        }
        Log.d(TAG, "populateAdapter: 1");
        cursorAdapter.changeCursor(c);
    }
    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();

    }



}
