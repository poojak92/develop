package com.scarlett.SelectPhoto;

/**
 * Created by deepshikha on 3/3/17.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scarlett.R;

import java.util.ArrayList;


public class Gallery_adapter extends ArrayAdapter<Model_images> {

    Context context;
    ViewHolder viewHolder;
    ArrayList<Model_images> al_menu = new ArrayList<>();


    public Gallery_adapter(Context context, ArrayList<Model_images> al_menu) {
        super(context, R.layout.adapter_gallery, al_menu);
        this.al_menu = al_menu;
        this.context = context;


    }

    @Override
    public int getCount() {

        Log.e("ADAPTER LIST SIZE", al_menu.size() + "");
        return al_menu.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_menu.size() > 0) {
            return al_menu.size();
        } else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_gallery, parent, false);

            viewHolder.ll_camera = (LinearLayout) convertView.findViewById(R.id.ll_camera);
            viewHolder.ll_image = (LinearLayout) convertView.findViewById(R.id.ll_image);

            viewHolder.tv_foldern = (TextView) convertView.findViewById(R.id.tv_folder);
            viewHolder.tv_foldersize = (TextView) convertView.findViewById(R.id.tv_folder2);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(!al_menu.get(position).getStr_folder().equals("0")) {

            viewHolder.tv_foldern.setText(al_menu.get(position).getStr_folder());
            viewHolder.tv_foldersize.setText(al_menu.get(position).getAl_imagepath().size() + "");
            viewHolder.ll_camera.setVisibility(View.GONE);
            viewHolder.ll_image.setVisibility(View.VISIBLE);

            Glide.with(context).load("file://" + al_menu.get(position).getAl_imagepath().get(0))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(viewHolder.iv_image);
        }else {
            viewHolder.ll_camera.setVisibility(View.VISIBLE);
            viewHolder.ll_image.setVisibility(View.GONE);
        }


        return convertView;

    }

    private static class ViewHolder {
        TextView tv_foldern, tv_foldersize;
        ImageView iv_image;
        LinearLayout ll_camera;
        LinearLayout ll_image;

    }


}
