package com.scarlett.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scarlett.Model.NavDrawerItem;
import com.scarlett.R;

import java.util.ArrayList;


public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;


    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;

    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.img_plus);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txt_cat_name);
        TextView txt_price = (TextView) convertView.findViewById(R.id.txt_price);

        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        txtTitle.setText(navDrawerItems.get(position).getTitle());

        if(!navDrawerItems.get(position).getPrice().equals("")){
            txt_price.setText(""+" "+navDrawerItems.get(position).getPrice() +" Coins");
            txt_price.setVisibility(View.VISIBLE);
        }else {
            txt_price.setVisibility(View.GONE);

        }

        return convertView;
    }
}