package com.scarlett.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.scarlett.Model.NavDrawerItem;
import com.scarlett.R;
import com.scarlett.activity.base.BaseAcitivity;
import com.scarlett.activity.base.BaseToolbarActivity;
import com.scarlett.adapter.NavDrawerListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashBoardActivity extends AppCompatActivity {

    String[] Menu = {
            "My Post",
            "My Favourite",
            "Wallet Balance",
            "Find Creator",
            "Share",
            "Logout"
    };

    int[] Menu_imageId = {
            R.drawable.ic_post,
            R.drawable.ic_favourite,
            R.drawable.ic_wallet,
            R.drawable.ic_findcreator,
            R.drawable.ic_share,
            R.drawable.ic_logout
    };
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    NavDrawerItem navDrawerItem;


    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.left_drawer)
    ListView mLeftDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setNavigationDrawer();


    }

    public void setNavigationDrawer() {
        navDrawerItems = new ArrayList<NavDrawerItem>();
        for (int i = 0; i < Menu.length; i++) {
            if (Menu[i].equals("Wallet Balance")) {
                navDrawerItem = new NavDrawerItem(Menu[i], Menu_imageId[i], "200");
            } else {
                navDrawerItem = new NavDrawerItem(Menu[i], Menu_imageId[i], "");
            }
            navDrawerItems.add(navDrawerItem);
        }

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.nav_header_main, mLeftDrawer, false);
        mLeftDrawer.addHeaderView(header, null, false);

        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mLeftDrawer.setOnItemClickListener(new DrawerItemClickListener());
        mLeftDrawer.setAdapter(adapter);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //selectItem(position);
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    //  drawer.closeDrawer(GravityCompat.START);

}
