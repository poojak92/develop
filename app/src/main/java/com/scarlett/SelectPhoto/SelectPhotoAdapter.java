package com.scarlett.SelectPhoto;

/**
 * Created by deepshikha on 3/3/17.
 */

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scarlett.R;

import java.util.ArrayList;


public class SelectPhotoAdapter extends ArrayAdapter<Model_images> {

    Context context;
    ViewHolder viewHolder;
    ArrayList<Model_images> al_menu = new ArrayList<>();
    int int_position;
    private SparseBooleanArray mSparseBooleanArray;//Variable to store selected Images


    public SelectPhotoAdapter(Context context, ArrayList<Model_images> al_menu, int int_position) {
        super(context, R.layout.adapter_photosfolder, al_menu);
        this.al_menu = al_menu;
        this.context = context;
        this.int_position = int_position;
        mSparseBooleanArray = new SparseBooleanArray();

    }

    @Override
    public int getCount() {

        Log.e("ADAPTER LIST SIZE", al_menu.get(int_position).getAl_imagepath().size() + "");
        return al_menu.get(int_position).getAl_imagepath().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_menu.get(int_position).getAl_imagepath().size() > 0) {
            return al_menu.get(int_position).getAl_imagepath().size();
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_selectphotos, parent, false);
            viewHolder.tv_foldern = (TextView) convertView.findViewById(R.id.tv_folder);
            viewHolder.tv_foldersize = (TextView) convertView.findViewById(R.id.tv_folder2);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            viewHolder.ll_camera = (LinearLayout) convertView.findViewById(R.id.ll_camera);
            viewHolder.ll_image = (ConstraintLayout) convertView.findViewById(R.id.ll_image);
            viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox1);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_foldern.setVisibility(View.GONE);
        viewHolder.tv_foldersize.setVisibility(View.GONE);

        viewHolder.ll_image.setVisibility(View.VISIBLE);
        viewHolder.ll_camera.setVisibility(View.GONE);

        viewHolder.mCheckBox.setTag(position);//Set Tag for CheckBox
        viewHolder.mCheckBox.setChecked(mSparseBooleanArray.get(position));
        viewHolder.mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);
        viewHolder.ll_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.mCheckBox.setChecked(true);
                mSparseBooleanArray.put((Integer) viewHolder.mCheckBox.getTag(), true);//Insert selected checkbox value inside boolean array
                ((PhotofolderActivity) context).showSelectButton();//call custom gallery activity method
            }
        });


        Glide.with(context).load("file://" + al_menu.get(int_position).getAl_imagepath().get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(viewHolder.iv_image);


        return convertView;

    }
    private static class ViewHolder {
        TextView tv_foldern, tv_foldersize;
        ImageView iv_image;
        LinearLayout ll_camera;
        ConstraintLayout ll_image;
        CheckBox mCheckBox;
    }

    CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);//Insert selected checkbox value inside boolean array
            ((PhotofolderActivity) context).showSelectButton();//call custom gallery activity method
        }
    };

    //Method to return selected Images
    public ArrayList<String> getCheckedItems() {
        ArrayList<String> mTempArry = new ArrayList<String>();

        for (int i = 0; i < al_menu.size(); i++) {
            if (mSparseBooleanArray.get(i)) {
                mTempArry.add(al_menu.get(int_position).getAl_imagepath().get(i));
            }
        }

        return mTempArry;
    }

}
