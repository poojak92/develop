package com.scarlett.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.scarlett.R;
import com.scarlett.SelectPhoto.GalleryActivity;
import com.scarlett.SelectPhoto.ShowSelectedPhoto_Adapter;
import com.scarlett.Utils.EqualSpacingItemDecoration;
import com.scarlett.activity.base.BaseToolbarActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class CreateGalleryActivity extends BaseToolbarActivity {

    @BindView(R.id.rv_folder)
    RecyclerView mRvFolder;
    private static final int CustomGallerySelectId = 1;//Set Intent Id
    public static final String CustomGalleryIntentKey = "ImageArray";//Set Intent Key Value

    ShowSelectedPhoto_Adapter showSelectedPhoto_Adapter;

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


    }


    public void addPhotos(View view) {
        startActivityForResult(new Intent(CreateGalleryActivity.this, GalleryActivity.class), CustomGallerySelectId);

       // mRouter.startActivity(GalleryActivity.class);
    }
    protected void onActivityResult(int requestcode, int resultcode,
                                    Intent imagereturnintent) {
        super.onActivityResult(requestcode, resultcode, imagereturnintent);
        switch (requestcode) {
            case CustomGallerySelectId:
                if (resultcode == RESULT_OK) {
                    String imagesArray = imagereturnintent.getStringExtra(CustomGalleryIntentKey);//get Intent data
                    Log.d("imagesArray4: ",imagesArray);
                    //Convert string array into List by splitting by ',' and substring after '[' and before ']'
                    List<String> selectedImages = Arrays.asList(imagesArray.substring(1, imagesArray.length() - 1).split(", "));
                    loadGridView(new ArrayList<String>(selectedImages));//call load gridview method by passing converted list into arrayList
                }
                break;

        }
    }
    //Load GridView
    private void loadGridView(ArrayList<String> imagesArray) {
        mRvFolder.setVisibility(View.VISIBLE);
        showSelectedPhoto_Adapter = new ShowSelectedPhoto_Adapter(CreateGalleryActivity.this, imagesArray);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateGalleryActivity.this,3);
        mRvFolder.addItemDecoration(new EqualSpacingItemDecoration(20));
        mRvFolder.addItemDecoration(new EqualSpacingItemDecoration(16,EqualSpacingItemDecoration.HORIZONTAL));
        mRvFolder.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        mRvFolder.setAdapter(showSelectedPhoto_Adapter);
    }
}
