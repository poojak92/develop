package com.scarlett.SelectPhoto;

import android.os.Bundle;
import android.widget.GridView;

import com.scarlett.R;
import com.scarlett.activity.base.BaseToolbarActivity;


public class PhotofolderActivity extends BaseToolbarActivity {
    int int_position;
    private GridView gridView;
    SelectPhotoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_galleryfolder);
        super.onCreate(savedInstanceState);

        showLeftButton(R.drawable.ic_toolbar_back, new ILeftClickListener() {
            @Override
            public void onLeftButtonClicked() {
                onBackPressed();
            }
        });
        showTitle(getResources().getString(R.string.select_photo));
        gridView = (GridView)findViewById(R.id.gv_folder);

        int_position = getIntent().getIntExtra("value", 0);
        adapter = new SelectPhotoAdapter(this,GalleryActivity.al_images,int_position);
        gridView.setAdapter(adapter);
    }
}
