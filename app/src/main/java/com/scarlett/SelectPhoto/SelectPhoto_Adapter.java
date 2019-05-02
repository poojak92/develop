package com.scarlett.SelectPhoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scarlett.R;

import java.util.ArrayList;

/**
 * Created by SONU on 31/10/15.
 */
public class SelectPhoto_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> imageUrls;
    private SparseBooleanArray mSparseBooleanArray;//Variable to store selected Images
    private boolean isCustomGalleryActivity;//Variable to check if gridview is to setup for Custom Gallery or not

    public SelectPhoto_Adapter(Context context, ArrayList<String> imageUrls, boolean isCustomGalleryActivity) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.isCustomGalleryActivity = isCustomGalleryActivity;
        mSparseBooleanArray = new SparseBooleanArray();



    }

    //Method to return selected Images
    public ArrayList<String> getCheckedItems() {
        ArrayList<String> mTempArry = new ArrayList<String>();

        for (int i = 0; i < imageUrls.size(); i++) {
            if (mSparseBooleanArray.get(i)) {
                mTempArry.add(imageUrls.get(i));
            }
        }

        return mTempArry;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int i) {
        return imageUrls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.adapter_selectphotos, viewGroup, false);//Inflate layout

        final CheckBox mCheckBox = (CheckBox) view.findViewById(R.id.checkBox1);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
        final ConstraintLayout cl_image = (ConstraintLayout) view.findViewById(R.id.cl_image);
        //If Context is MainActivity then hide checkbox
        if (!isCustomGalleryActivity)
            mCheckBox.setVisibility(View.GONE);


        cl_image.setTag(position);
        mCheckBox.setTag(position);//Set Tag for CheckBox
        final ArrayList<String> temparry = getCheckedItems();
        Log.d("sizetemparray: ", "" + temparry.size());
        if (temparry.size() > 0) {
            if (temparry.contains(imageUrls.get(position))) {
                cl_image.setBackground(context.getResources().getDrawable(R.drawable.drawable_selectphotofolder));
            } else {
                cl_image.setBackground(context.getResources().getDrawable(R.drawable.drawable_photofolder));
            }
        }

        mCheckBox.setChecked(mSparseBooleanArray.get(position));
        Glide.with(context).load("file://" + imageUrls.get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> temparry = getCheckedItems();
                if (temparry.contains(imageUrls.get(position))) {
                    mCheckBox.setChecked(false);
                    mSparseBooleanArray.delete((Integer) mCheckBox.getTag());
                    ((SelectPhotoActivity) context).showSelectButton();//call custom gallery activity method
                    cl_image.setBackground(context.getResources().getDrawable(R.drawable.drawable_photofolder));
                } else {
                    mCheckBox.setChecked(true);
                    mSparseBooleanArray.put((Integer) mCheckBox.getTag(), true);//Insert selected checkbox value inside boolean array
                    ((SelectPhotoActivity) context).showSelectButton();//call custom gallery activity method
                    cl_image.setBackground(context.getResources().getDrawable(R.drawable.drawable_selectphotofolder));
                }

            }
        });

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                   mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);//Insert selected checkbox value inside boolean array
                   ((SelectPhotoActivity) context).showSelectButton();//call custom gallery activity method*//*
                   if(isChecked){
                       cl_image.setBackground(context.getResources().getDrawable(R.drawable.drawable_selectphotofolder));

                   }else {
                       cl_image.setBackground(context.getResources().getDrawable(R.drawable.drawable_photofolder));

                   }

               }
           }
        );

        return view;

    }
}
