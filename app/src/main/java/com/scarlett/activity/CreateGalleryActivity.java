package com.scarlett.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.scarlett.R;
import com.scarlett.SelectPhoto.GalleryActivity;
import com.scarlett.SelectPhoto.SelectPhotoAdapter;
import com.scarlett.activity.base.BaseAcitivity;
import com.scarlett.activity.base.BaseToolbarActivity;

public class CreateGalleryActivity extends BaseToolbarActivity {

   /* int int_position;
    private GridView gridView;
    SelectPhotoAdapter adapter;
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_creategallery);
        super.onCreate(savedInstanceState);

        showLeftButton(R.drawable.ic_toolbar_back, new ILeftClickListener() {
            @Override
            public void onLeftButtonClicked() {
                onBackPressed();
            }
        });
        showTitle(getResources().getString(R.string.text_create_gallery));


/*
        gridView = (GridView)findViewById(R.id.gv_folder);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new SelectPhotoAdapter(this, GalleryActivity.al_images,int_position);
        gridView.setAdapter(adapter);*/

    }


    public void addPhotos(View view) {
        mRouter.startActivity(GalleryActivity.class);
    }
}
