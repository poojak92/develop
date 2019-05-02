package com.scarlett.SelectPhoto;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.scarlett.R;
import com.scarlett.activity.base.BaseToolbarActivity;

import java.util.ArrayList;

import butterknife.BindView;


public class PhotofolderActivity extends BaseToolbarActivity {
    int int_position;

    SelectPhotoAdapter adapter;

    @BindView(R.id.gv_folder)
    GridView mGridView;

    @BindView(R.id.btn_next)
    Button mBtnNext;


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

        mBtnNext.setVisibility(View.VISIBLE);
        int_position = getIntent().getIntExtra("value", 0);

        adapter = new SelectPhotoAdapter(this,GalleryActivity.al_images,int_position);
        mGridView.setAdapter(adapter);
    }
    public void showSelectButton() {
        ArrayList<String> selectedItems = adapter.getCheckedItems();
        if (selectedItems.size() > 0) {
            mBtnNext.setVisibility(View.VISIBLE);
        } else
            mBtnNext.setVisibility(View.GONE);

    }
}
