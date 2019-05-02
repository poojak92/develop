package com.scarlett.SelectPhoto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.scarlett.R;
import com.scarlett.activity.CreateGalleryActivity;
import com.scarlett.activity.base.BaseToolbarActivity;

import java.util.ArrayList;

import butterknife.BindView;


public class SelectPhotoActivity extends BaseToolbarActivity {
    int int_position;

    SelectPhoto_Adapter adapter;

    @BindView(R.id.gv_folder)
    GridView mGridView;

    @BindView(R.id.btn_next)
    Button mBtnNext;

    private static ArrayList<String> galleryImageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_selectphoto);
        super.onCreate(savedInstanceState);

        showLeftButton(R.drawable.ic_toolbar_back, new ILeftClickListener() {
            @Override
            public void onLeftButtonClicked() {
                onBackPressed();
            }
        });
        showTitle(getResources().getString(R.string.select_photo));
       // int_position = getIntent().getIntExtra("value", 0);

        int_position = GalleryActivity.selectpos;

        galleryImageUrls = new ArrayList<String>();//Init array
        Log.d("select",""+GalleryActivity.al_images.get(int_position).getAl_imagepath());

        for (int i=0;i<GalleryActivity.al_images.get(int_position).getAl_imagepath().size();i++) {
            galleryImageUrls.add(GalleryActivity.al_images.get(int_position).getAl_imagepath().get(i));//get Image from column index
            System.out.println("Array path" + galleryImageUrls.get(i));
        }

        adapter = new SelectPhoto_Adapter(SelectPhotoActivity.this, galleryImageUrls, true);
       // adapter = new SelectPhotoAdapter(this,GalleryActivity.al_images,int_position);
        mGridView.setAdapter(adapter);
    }
    public void showSelectButton() {
        ArrayList<String> selectedItems = adapter.getCheckedItems();
        if (selectedItems.size() > 0) {
            mBtnNext.setText("Image selected: "+selectedItems.size());
            mBtnNext.setVisibility(View.VISIBLE);
        } else
            mBtnNext.setVisibility(View.GONE);

    }

    public void selectedPhoto(View view) {
        ArrayList<String> selectedItems = adapter.getCheckedItems();

        //Send back result to CreateGalleryActivity with selected images
        Intent intent = new Intent();
        intent.putExtra(GalleryActivity.CustomGalleryIntentKey, selectedItems.toString());//Convert Array into string to pass data
        Log.d("imagesArray1: ",selectedItems.toString());

        setResult(RESULT_OK, intent);//Set result OK
        finish();//finish activity

    }
}
